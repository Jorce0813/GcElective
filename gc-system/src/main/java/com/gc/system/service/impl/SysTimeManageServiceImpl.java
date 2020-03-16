package com.gc.system.service.impl;

import com.gc.system.domain.GcSc;
import com.gc.system.domain.GcSchedule;
import com.gc.system.domain.GcTimeManage;
import com.gc.system.mapper.SysTimeManageMapper;
import com.gc.system.mapper.SysUserMapper;
import com.gc.system.service.SysTimeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 选课管理 - 业务层
 *
 * @author gc
 **/
@Service
public class SysTimeManageServiceImpl implements SysTimeManageService {

    @Autowired
    private SysTimeManageMapper manageMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public GcTimeManage getManage() {
        return manageMapper.getManage();
    }

    @Override
    public int update(GcTimeManage manage) {
        return manageMapper.update(manage);
    }

    @Override
    public List<Long> getScheduleIdList() {
        return manageMapper.getScheduleIdList();
    }

    @Override
    public List<GcSc> getGcScByVoluntaryAndScheId(Long scheId, Long voluntary, String startTime, String endTime) {
        return manageMapper.getGcScByVoluntaryAndScheId(scheId, voluntary, startTime, endTime);
    }

    @Override
    public Integer getSelectedSizeById(Long scheId) {
        return manageMapper.getSelectedSizeById(scheId);
    }

    @Override
    public Integer getClassSizeById(Long scheId) {
        return manageMapper.getClassSizeById(scheId);
    }

    @Override
    public String getTableName(Long instId) {
        return manageMapper.getTableName(instId);
    }

    @Override
    public int deleteRecodeById(Map<String, Object> params) {
        return manageMapper.deleteRecodeById(params);
    }

    /**
     *  if(当前志愿人数 > 剩余名额){
     *      志愿内随机选出[剩余]个名额的人;
     *      然后将没选上的记录delete;
     *   }else if(当前志愿人数 <= 剩余名额){
     *      不用随机, 全部选上;
     *  }
     *  再->同个userId, 将选了该门课程不同志愿进行delete [1] ;
     *
	 *	！！！更新选课人数
     *	@ Transactional(propagation = Propagation.REQUIRES_NEW)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void draw(int voluntary) {
        // 先设置draw_flag, 再进行抽签, 避免[抽签后更新draw_flag字段失败造成的rollback既耗时又给数据库带来压力]
        GcTimeManage gcTimeManage = new GcTimeManage();
        gcTimeManage.setDrawFlag(1);
        if (update(gcTimeManage) < 1) {
            throw new RuntimeException("更新drawFlag失败");
        }

        // 抽签顺序: 第一志愿 -> 第二志愿 -> 第三志愿
        List<Long> scheList = getScheduleIdList();
        List<GcSchedule> updateList = new ArrayList<>();
        // 获取该轮选课的时间
        GcTimeManage manage = getManage();
        Date startTime = manage.getStartTime();
        Date endTime = manage.getEndTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Iterator<Long> ite = scheList.iterator();
        while (ite.hasNext()) {
            Long scheId = ite.next(); //获取排课ID
            List<GcSc> aList = new ArrayList<>(); //被选上的学生的list
            List<GcSc> bList = new ArrayList<>(); //没被选上的学生的list
            // list = 选了排课ID为scheId, 第voluntary志愿的选课记录
            List<GcSc> list = getGcScByVoluntaryAndScheId(scheId, (long) voluntary, df.format(startTime), df.format(endTime));
            if (list == null || list.size() == 0) { //本门课程当前志愿没人选择
                continue;
            }
            Integer classSize = getClassSizeById(scheId); //课堂人数
            Integer hasDrawSize = selectCountOfVoluntary(scheId, voluntary, df.format(startTime), df.format(endTime)); //小于voluntary志愿的已经选上的人数
            Integer surplus = classSize - hasDrawSize; //剩余可选名额
            if (list.size() > surplus) {
                /* shuffle算法<将list中的元素进行随机交换后重置>, 无序随机算法, 相当于进行了Random */
                Collections.shuffle(list);
                bList = list.subList(0, (list.size() - surplus));
                aList = list.subList((list.size() - surplus), list.size());
                // 处理没选上的记录 - 分表处理
                Iterator<GcSc> iterator = bList.iterator();
                Map<String, List<Long>> map = new HashMap<>(); // Map<数据库表名, 该表对应要delete的落选的sc_id>
                while (iterator.hasNext()) {
                    GcSc sc = iterator.next();
                    String tableName = getTableName(userMapper.selectUserById(sc.getUserId()).getInstId());
                    if (map.containsKey(tableName)) {
                        map.get(tableName).add(sc.getScId());
                    } else {
                        map.put(tableName, new ArrayList<>());
                        map.get(tableName).add(sc.getScId());
                    }
                }
                // 根据不同的数据库表delete未被选上的选课记录
                for (Map.Entry<String, List<Long>> m : map.entrySet()) {
                    Map<String, Object> params = new HashMap<>();
                    params.put("tableName", m.getKey());
                    params.put("ids", m.getValue());
                    deleteRecodeById(params);
                }
            } else {
                aList = list;
            }
            // 将被当前选上的同个userId, 将选了该门课程不同志愿进行delete
            // 根据scheId先查询被选上的courseId
            // 再连表查询[条件:tableName, courseId, voluntary, userId, scheId=scheId]出选了同个courseId不同志愿的scheId, 然后进行delete
            Long courseId = manageMapper.getCourseIdByScheId(scheId);
            for (GcSc tmp : aList) {
                String tableName = getTableName(userMapper.selectUserById(tmp.getUserId()).getInstId());
                Long userId = tmp.getUserId();
                deleteRecode(tableName, userId, voluntary, courseId);
            }
        }
    }



    @Override
    public int deleteRecode(String tableName, Long userId, int voluntaty, Long courseId) {
        return manageMapper.deleteRecode(tableName, userId, voluntaty, courseId);
    }

    @Override
    public Integer selectCountOfVoluntary(Long scheId, int voluntary, String startTime, String endTime) {
        return manageMapper.selectCountOfVoluntary(scheId, voluntary, startTime, endTime);
    }

    @Override
    public int updateSelectedSize(int voluntary) {
        List<Long> scheList = getScheduleIdList();
        List<GcSchedule> updateList = new ArrayList<>();
        // 获取该轮选课的时间
        GcTimeManage manage = getManage();
        Date startTime = manage.getStartTime();
        Date endTime = manage.getEndTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 更新选课人数
        Iterator<Long> ite = scheList.iterator();
        while (ite.hasNext()) {
            Long scheId = ite.next();
            // 根据scheId查询该门排课的已选人数
            int selectedSize = selectCountOfVoluntary(scheId, voluntary, df.format(startTime), df.format(endTime)); // (1,2,3) < 4
            GcSchedule schedule = new GcSchedule();
            schedule.setScheId(scheId);
            schedule.setSelectedSize(selectedSize);
            updateList.add(schedule);
        }
        int updateFlag = batchUpdateSelectedSize(updateList);
        if (updateFlag > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int batchUpdateSelectedSize(List<GcSchedule> list) {
        return manageMapper.batchUpdateSelectedSize(list);
    }
}
