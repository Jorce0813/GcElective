package com.gc.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gc.system.domain.GcSc;
import com.gc.system.domain.GcSchedule;
import com.gc.system.domain.TimeTableModel;
import com.gc.system.mapper.SysTimeTableMapper;
import com.gc.system.mapper.SysUserMapper;
import com.gc.system.service.ISysTimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ISysTimeTableService 的实现类
 *
 * @author gc
 **/
@Service
public class SysTimeTableServiceImpl implements ISysTimeTableService {

    @Autowired
    private SysTimeTableMapper sysTimeTableMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询指定用户的已选课表信息 - 1
     *
     * @Param [userId]
     * @return java.util.List<com.gc.system.domain.TimeTableModel>
     **/
    @Override
    public List<TimeTableModel> selectCourseListByUserId(Long userId) {
        Map<Integer, TimeTableModel> map = new HashMap<>();
        List<TimeTableModel> resList = new ArrayList<>();

        String uuserId = String.valueOf(userId);
        Long instId = sysUserMapper.selectUserById(userId).getInstId();
        String tableName = sysTimeTableMapper.getTableName(instId);
        // 获取列表信息
        List<GcSchedule> list = sysTimeTableMapper.selectCourseListByUserId(tableName, uuserId);
        Iterator<GcSchedule> ite = list.iterator();
        while (ite.hasNext()) {
            GcSchedule schedule = ite.next();
            String teachTimeAndLoc = schedule.getTeachTimeAndLoc();
            String courseName = schedule.getCourseName();
            // Get 已选课表的志愿
            List<GcSc> gcScs = schedule.getGcScs(); //只有 1 个元素
            String voluntary = gcScs.get(0).getVoluntary();
            //
            JSONObject jo = JSONObject.parseObject(teachTimeAndLoc);
            JSONArray array = jo.getJSONArray("info");
            int size = array.size();
            for (int i = 0; i < size; i++) {
                JSONObject obj = array.getJSONObject(i);
                // 获取周-节信息
                String[] point = obj.getString("point").split("-");
                if (map.containsKey(Integer.parseInt(point[1]))) {
                    TimeTableModel ttm = map.get(Integer.parseInt(point[1]));
                    ttm.setWv(point[0], voluntary, courseName);
                    map.put(Integer.parseInt(point[1]), ttm);
                } else {
                    TimeTableModel tt = new TimeTableModel();
                    tt.setPoint(Integer.parseInt(point[1]));
                    tt.setWv(point[0], voluntary, courseName);
                    map.put(Integer.parseInt(point[1]), tt);
                }
            }
        }
        for (int i = 1; i <= 6; i++) {
            if (map.containsKey(i)) {
                resList.add(map.get(i));
            } else {
                TimeTableModel model = new TimeTableModel();
                model.setPoint(i);
                resList.add(model);
            }
        }
        return resList;
    }

    /**
     * 查询指定用户的已选课表信息 - 2
     *
     * @param userId
     * @return List<GcSchedule>
     **/
    @Override
    public List<GcSchedule> selectScheduleList(Long userId) {
        List<GcSchedule> res = new ArrayList<>();
        Long instId = sysUserMapper.selectUserById(userId).getInstId();
        String tableName = sysTimeTableMapper.getTableName(instId);
        //处理json数据
        List<GcSchedule> list = sysTimeTableMapper.selectCourseListByUserId(tableName, String.valueOf(userId));
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

    /**
     * 退选课程
     *
     * @Param [userId, scheId]
     * @return int
     **/
    @Override
    public int withdrawal(Long userId, Long scheId) {
        Long instId = sysUserMapper.selectUserById(userId).getInstId();
        String tableName = sysTimeTableMapper.getTableName(instId);
        int deleteFlag = sysTimeTableMapper.withdrawal(tableName, userId, scheId);
        return deleteFlag;
    }

    /**
     * 更新选课人数 [退选课程后]
     *
     * @Param [scheId]
     * @return int
     **/
    @Override
    public int reduceSelectedSize(Long scheId) {
        int updateFlag = sysTimeTableMapper.reduceSelectedSize(scheId);
        return updateFlag;
    }

    /**
     * 获取选课总学分
     *
     * @Param [userId]
     * @return int
     **/
    @Override
    public int getTotalCredit(Long userId) {
        Long instId = sysUserMapper.selectUserById(userId).getInstId();
        String tableName = sysTimeTableMapper.getTableName(instId);
        Integer creditSum = sysTimeTableMapper.getTotalCredit(userId, tableName);
        return creditSum == null ? 0 :creditSum;
    }
}
