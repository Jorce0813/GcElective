package com.gc.system.service;

import com.gc.system.domain.GcSchedule;

import java.util.List;

/**
 * 课程管理 - service层
 *
 * @author gc
 **/
public interface ISysCourseService {

    public int deleteCourseByIds(String ids);

    public GcSchedule selectScheduleByScheId(Long userId);

    public int updateSave(GcSchedule schedule);

    public String getCourseNameById(Long courseId);

    public String getTeacherNameById(Long tId);

    public int insertSchedule(GcSchedule schedule);

    public String importSchedule(List<GcSchedule> scheduleList);

    public List<GcSchedule> getScheduleList(GcSchedule schedule);

}
