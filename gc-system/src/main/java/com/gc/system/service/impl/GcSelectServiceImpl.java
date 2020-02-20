package com.gc.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gc.system.domain.GcSchedule;
import com.gc.system.mapper.GcSelectMapper;
import com.gc.system.service.GcSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * GcSelectService 接口实现类
 *
 * @author gc
 **/
@Service
public class GcSelectServiceImpl implements GcSelectService {

    @Autowired
    private GcSelectMapper gcSelectMapper;

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
}
