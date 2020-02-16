package com.gc.system.domain;

import com.gc.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 排课表 gc_schedule
 *
 * @author gc
 **/
public class GcSchedule extends BaseEntity {

    private static final Long serialVersionUID = 1L;

    /** 排课ID **/
    private Long scheId;

    /** 教师ID **/
    private Long teacherId;

    /** 课程ID **/
    private Long courseId;

    /** 上课时间 **/
    private String techTime;

    /** 上课地点 **/
    private String techLoc;

    /** 课堂人数 **/
    private int classSize;

    /** 已选人数 **/
    private int selectedSize;

    /** 创建时间 **/
    private Date createTime;

    /** 备注 **/
    private String note;

    public Long getScheId() {
        return scheId;
    }

    public void setScheId(Long scheId) {
        this.scheId = scheId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTechTime() {
        return techTime;
    }

    public void setTechTime(String techTime) {
        this.techTime = techTime;
    }

    public String getTechLoc() {
        return techLoc;
    }

    public void setTechLoc(String techLoc) {
        this.techLoc = techLoc;
    }

    public int getClassSize() {
        return classSize;
    }

    public void setClassSize(int classSize) {
        this.classSize = classSize;
    }

    public int getSelectedSize() {
        return selectedSize;
    }

    public void setSelectedSize(int selectedSize) {
        this.selectedSize = selectedSize;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("scheId", getScheId())
                .append("teacherId", getTeacherId())
                .append("courseId", getCourseId())
                .append("techTime", getTechTime())
                .append("techLoc", getTechLoc())
                .append("classSize", getClassSize())
                .append("selectedSize", getSelectedSize())
                .append("createTime", getCreateTime())
                .append("note", getNote())
                .toString();
    }
}
