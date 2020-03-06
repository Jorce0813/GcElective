package com.gc.system.mapper;

import com.gc.system.domain.GcSchedule;

import java.util.List;

/**
 * 课程管理 - 数据层
 *
 * @author gc
 **/
public interface SysCourseMapper {

    /**
     * 根据ID删除课程信息
     *
     * @Param [ids]
     * @return int
     **/
    public int deleteCourseByIds(Long[] ids);

    /**
     * 根据scheId查询课程信息
     *
     * @Param [scheId]
     * @return com.gc.system.domain.GcSchedule
     **/
    public GcSchedule selectScheduleByScheId(Long scheId);

    /**
     * 更新排课的课程信息
     *
     * @Param [schedule]
     * @return int
     **/
    public int updateSchedule(GcSchedule schedule);

    /**
     * 更新课程信息
     *
     * @Param [schedule]
     * @return int
     **/
    public int updateCourse(GcSchedule schedule);

    public String getCourseNameById(Long courseId);

    public String getTeacherNameById(Long tId);

    /**
     * 新增排课信息
     *
     * @Param [schedule]
     * @return int
     **/
    public int insertSchedule(GcSchedule schedule);

    public List<GcSchedule> getScheduleById(GcSchedule schedule);

    public List<GcSchedule> getScheduleList(GcSchedule schedule);

}
