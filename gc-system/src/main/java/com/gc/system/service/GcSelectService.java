package com.gc.system.service;

import com.gc.system.domain.GcSchedule;

import java.util.List;

/**
 * 选课入口 - Service层
 *
 * @author gc
 **/
public interface GcSelectService {

    /**
     * 根据条件查询选课课程数据
     *
     * @param gcSchedule
     * @return List<GcSchedule>
     **/
    public List<GcSchedule> selectScheduleList(GcSchedule gcSchedule);

}
