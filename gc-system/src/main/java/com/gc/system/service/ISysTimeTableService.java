package com.gc.system.service;

import com.gc.system.domain.GcSchedule;
import com.gc.system.domain.TimeTableModel;

import java.util.List;

/**
 * 我的课表 - 接口层
 *
 * @author gc
 **/
public interface ISysTimeTableService {

    public List<TimeTableModel> selectCourseListByUserId(Long userId);

    public List<GcSchedule> selectScheduleList(Long userId);

    public int withdrawal(Long userId, Long scheId);

    public int reduceSelectedSize(Long scheId);

    public int getTotalCredit(Long userId);

}
