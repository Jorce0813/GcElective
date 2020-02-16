package com.gc.system.domain;

import com.gc.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 教师表 gc_teacher
 *
 * @author gc
 **/
public class GcTeacher extends BaseEntity {

    private static final Long serialVersionUID = 1L;

    /** 教师ID **/
    private Long tId;

    /** 教师姓名 **/
    private String tName;

    /** 教师所属学院ID **/
    private Long instId;

    /** 性别 **/
    private String tSex;

    /** 邮箱 **/
    private String tEmail;

    /** 电话 **/
    private String tPhone;

    /** 学历 **/
    private String tEducation;

    /** 职位 **/
    private String tPosition;

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public Long getInstId() {
        return instId;
    }

    public void setInstId(Long instId) {
        this.instId = instId;
    }

    public String gettSex() {
        return tSex;
    }

    public void settSex(String tSex) {
        this.tSex = tSex;
    }

    public String gettEmail() {
        return tEmail;
    }

    public void settEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public String gettPhone() {
        return tPhone;
    }

    public void settPhone(String tPhone) {
        this.tPhone = tPhone;
    }

    public String gettEducation() {
        return tEducation;
    }

    public void settEducation(String tEducation) {
        this.tEducation = tEducation;
    }

    public String gettPosition() {
        return tPosition;
    }

    public void settPosition(String tPosition) {
        this.tPosition = tPosition;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tId", gettId())
                .append("tName", gettName())
                .append("instId", getInstId())
                .append("tSex", gettSex())
                .append("tPhone", gettPhone())
                .append("tEmail", gettEmail())
                .append("tEducation", gettEducation())
                .append("tPosition", gettPosition())
                .toString();
    }
}
