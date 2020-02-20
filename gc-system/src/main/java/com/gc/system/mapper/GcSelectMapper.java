package com.gc.system.mapper;

import com.gc.system.domain.GcSchedule;

import java.util.List;

/**
 * 选课中心 - Dao层
 *
 * @author gc
 **/
public interface GcSelectMapper {

    /**
     * 根据条件分页查询排课的课程数据
     *
     * @Param [gcSchedule]
     * @return java.util.List<com.gc.system.domain.GcSchedule>
     **/
    public List<GcSchedule> selectScheduleList(GcSchedule gcSchedule);

}
