package com.gc.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gc.system.domain.GcSc;
import com.gc.system.domain.GcSchedule;
import com.gc.system.mapper.GcSelectMapper;
import com.gc.system.mapper.SysTimeManageMapper;
import com.gc.system.mapper.SysUserMapper;
import com.gc.system.service.GcSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * GcSelectService 接口实现类
 *
 * @author gc
 **/
@Service
public class GcSelectServiceImpl implements GcSelectService {

    @Autowired
    private GcSelectMapper gcSelectMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysTimeManageMapper manageMapper;

    /**
     * 根据条件查询选课课程数据
     *
     * @param gcSchedule
     * @return List<GcSchedule>
     **/
    @Override
    public List<GcSchedule> selectScheduleList(GcSchedule gcSchedule) {
        List<GcSchedule> res = new ArrayList<>();
        //处理json数据
        List<GcSchedule> list = gcSelectMapper.selectScheduleList(gcSchedule);
        Iterator<GcSchedule> ite = list.iterator();
        while (ite.hasNext()) {
            GcSchedule gs = ite.next();
            String teachTimeAndLoc = gs.getTeachTimeAndLoc();
            // 格式化json数据, 取出上课时间和地点
            JSONObject jo = JSONObject.parseObject(teachTimeAndLoc);
            JSONArray array = jo.getJSONArray("info");
            int size = array.size();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                JSONObject obj = array.getJSONObject(i);
                // 上课周次
                String week = obj.getString("week");
                // 上课节次[1-2: 表示第一周3~4节]
                String[] point = obj.getString("point").split("-");
                int jieci = Integer.parseInt(point[1]);
                // 上课地点
                String loc = obj.getString("loc");
                sb.append("[\n")
                        .append(week + " 周\n")
                        .append(" 周 " + point[0] + ": " + (jieci * 2 - 1) + "-" + (jieci * 2) + " 节\n")
                        .append(" " + loc + "\n")
                        .append("]\n");
            }
            // 设置处理后的 [teachTimeAndLoc] 格式数据
            gs.setTeachTimeAndLoc(sb.toString());
            // 将对象添加到res中
            res.add(gs);
        }
        return res;
    }

    @Override
    public GcSchedule selectScheduleByScheId(Long scheId) {
        return gcSelectMapper.selectScheduleByScheId(scheId);
    }

    /**
     * 更新选课人数 [+]
     *
     * @Param gcSc
     * @return int
     **/
    @Override
    public int addSelectedSize(GcSc gcSc){
        return gcSelectMapper.addSelectedSize(gcSc);
    }

    /**
     * 更新选课人数 [-]
     *
     * @Param gcSc
     * @return int
     **/
    @Override
    public int reduceSelectedSize(GcSc gcSc){
        return gcSelectMapper.reduceSelectedSize(gcSc);
    }

    /**
     * 插入选课记录
     *
     * @Param gcSc
     * @return int
     **/
    @Override
    public int insertSelectedCourse(GcSc gcSc) {
        // 根据 userId 找到该学生的学院ID
        Long instId = sysUserMapper.selectUserById(gcSc.getUserId()).getInstId();
        // 根据学院ID找到学院的 [已选课表]
        String tableName = gcSelectMapper.getTableName(instId);
        gcSc.setTableName(tableName);
        return gcSelectMapper.insertSelectedCourse(gcSc);
    }

    /**
     * 查询用户是否选择了该门课程
     *
     * @Param [gcSc]
     * @return java.lang.Integer
     **/
    @Override
    public Integer isSelected(GcSc gcSc) {
        Long instId = sysUserMapper.selectUserById(gcSc.getUserId()).getInstId();
        String tableName = gcSelectMapper.getTableName(instId);
        gcSc.setTableName(tableName);
        return gcSelectMapper.isSelected(gcSc);
    }

    /**
     * 判断 [准备选] 的课程跟该学生 [已经选了的] 课程是否冲突
     *
     * @Param [gcSc]
     * @return java.lang.String - 返回第一个冲突的 [课程名称]
     **/
    @Override
    public String isConflict(GcSc gcSc) {
        /** map1, map2用于存储 Map<point,weekList> */
        Map<String, List<String>> map1 = new HashMap<>();
        Map<String, List<String>> map2 = new HashMap<>();
        // 1. 获取即将要选择的课程信息
        GcSchedule schedule = gcSelectMapper.selectScheduleByScheId(gcSc.getScheId());
        String teachTimeAndLoc = schedule.getTeachTimeAndLoc();
        // 格式化json数据, 取出上课时间和地点
        JSONObject jo = JSONObject.parseObject(teachTimeAndLoc);
        JSONArray array = jo.getJSONArray("info");
        int size = array.size();
        for (int i = 0; i < size; i++) {
            JSONObject obj = array.getJSONObject(i);
            // 上课周次
            String[] week = obj.getString("week").split(",");
            List<String> list1 = new ArrayList<>(Arrays.asList(week));
            // 上课节次[1-2: 表示第一周3~4节]
            String point = obj.getString("point");
            if (map1.containsKey(point)) { //并集
                List<String> list2 = map1.get(point);
                // 求两个list的并集 [跟数学上的并集不太一样]
                list2.addAll(list1);
                map1.put(point, list2);
            } else { //添加
                map1.put(point, list1);
            }
        }
        // 2. 获取该用户已选择的课程信息
        Long instId = sysUserMapper.selectUserById(gcSc.getUserId()).getInstId();
        String tableName = gcSelectMapper.getTableName(instId);
        gcSc.setTableName(tableName);
        List<GcSchedule> gList = gcSelectMapper.selectCourseListByUserId(gcSc);
        Iterator<GcSchedule> ite = gList.iterator();
        while (ite.hasNext()) {
            GcSchedule gs = ite.next();
            /* start :add in 2020/03/10 : 添加判断: 选课时[同名课程]是否选过前端传过来的选课志愿 */
            if (gs.getCourseName().equals(schedule.getCourseName())) {
                return gs.getCourseName();
            }
            /* end : */
            String teachTimeAndLoc2 = gs.getTeachTimeAndLoc();
            // 格式化json数据, 取出上课时间和地点
            JSONObject jo2 = JSONObject.parseObject(teachTimeAndLoc2);
            JSONArray array2 = jo2.getJSONArray("info");
            int size2 = array2.size();
            for (int i = 0; i < size2; i++) {
                JSONObject obj = array2.getJSONObject(i);
                // 上课周次
                String[] week = obj.getString("week").split(",");
                List<String> list1 = new ArrayList<>(Arrays.asList(week));
                // 上课节次[1-2: 表示第一周3~4节]
                String point = obj.getString("point");
                if (map2.containsKey(point)) { //并集
                    List<String> list2 = map2.get(point);
                    // 求两个list的并集 [跟数学上的并集不太一样]
                    list2.addAll(list1);
                    map2.put(point, list2);
                } else { //添加
                    map2.put(point, list1);
                }
            }
            // 3. 判断 [待选课程] 跟 [已选课程] 是否冲突
            for (Map.Entry<String, List<String>> m : map1.entrySet()) {
                if (map2.containsKey(m.getKey())) { //同个 [周节] , 判断week是否有交集
                    List<String> list1 = m.getValue();
                    List<String> list2 = map2.get(m.getKey());
                    if (!Collections.disjoint(list1, list2)) { //判断是否有交集 - [周week有冲突]
                        return gs.getCourseName(); //直接返回第一个冲突的课程
                    }
                }
            }
        }
        // 4. 没有冲突返回 null
        return null;
    }

    /**
     * 根据scheId查询课程学分
     *
     * @Param [scheId]
     * @return java.lang.Integer
     **/
    @Override
    public Integer getCourseCreditByScheId(Long scheId) {
        return gcSelectMapper.getCourseCreditByScheId(scheId);
    }

    @Override
    public Integer hasSelected(GcSc gcSc) {
        Long instId = sysUserMapper.selectUserById(gcSc.getUserId()).getInstId();
        String tableName = gcSelectMapper.getTableName(instId);
        Date startTime = manageMapper.getManage().getStartTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return gcSelectMapper.hasSelected(tableName, gcSc.getUserId(), gcSc.getScheId(), df.format(startTime));
    }

    @Override
    public Integer hasSelected(String tableName, Long userId, Long scheId, String startTime) {
        return gcSelectMapper.hasSelected(tableName, userId, scheId, startTime);
    }
}
