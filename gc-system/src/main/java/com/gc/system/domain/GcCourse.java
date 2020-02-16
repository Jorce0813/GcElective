package com.gc.system.domain;

import com.gc.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 课程表 gc_course
 *
 * @author gc
 **/
public class GcCourse extends BaseEntity {

    private static final Long serialVersionUID = 1L;

    /** 课程ID **/
    private Long courseId;

    /** 课程名 **/
    private String courseName;

    /** 课程类型 **/
    private String courseType;

    /** 课程学分 **/
    private Integer courseCredit;

    /** 课程学时 **/
    private Integer courseHours;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public Integer getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(Integer courseCredit) {
        this.courseCredit = courseCredit;
    }

    public Integer getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(Integer courseHours) {
        this.courseHours = courseHours;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("courseId", getCourseId())
                .append("courseName", getCourseName())
                .append("courseType", getCourseType())
                .append("courseCredit", getCourseCredit())
                .append("courseHours", getCourseHours())
                .toString();
    }
}
