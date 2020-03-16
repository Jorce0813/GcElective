package com.gc.system.service;

import com.gc.system.domain.GcSc;
import com.gc.system.domain.GcSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    public GcSchedule selectScheduleByScheId(Long scheId);

    public int addSelectedSize(GcSc gcSc);

    public int reduceSelectedSize(GcSc gcSc);

    public int insertSelectedCourse(GcSc gcSc);

    public Integer isSelected(GcSc gcSc);

    /**
     * 判断 [准备选] 的课程跟该学生 [已经选了的] 课程是否冲突
     *
     * @Param [gcSc]
     * @return java.lang.String - 返回第一个冲突的 [课程名称]
     **/
    public String isConflict(GcSc gcSc);

    public Integer getCourseCreditByScheId(Long scheId);

    public Integer hasSelected(GcSc gcSc);

    /**
     * 根据scheId得到courseId, 然后判断本轮选课之前的选课轮次是否已经抽中了该courseId
     *
     * @return java.lang.Integer
     * @Param [tableName, userId, scheId, startTime]
     **/
    public Integer hasSelected(String tableName, Long userId, Long scheId, String startTime);


}
