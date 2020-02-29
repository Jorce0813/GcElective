package com.gc.system.domain;

import com.gc.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 已选课表 gc_sc [sc == selected course]
 *
 * @author gc
 **/
public class GcSc extends BaseEntity {

    private static final Long serialVersionUID = 1L;

    /** 已选课程ID **/
    private Long scId;

    /** 学生ID **/
    private Long userId;

    /** 排课ID **/
    private Long scheId;

    /** 选课志愿 **/
    private String voluntary;

    /** 对应哪个学院的数据库表-插入选课记录时用 **/
    private String tableName;

    /** 选课时间 **/
    private Date createTime;

    public Long getScId() {
        return scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScheId() {
        return scheId;
    }

    public void setScheId(Long scheId) {
        this.scheId = scheId;
    }

    public String getVoluntary() {
        return voluntary;
    }

    public void setVoluntary(String voluntary) {
        this.voluntary = voluntary;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("scId", getScId())
                .append("userId", getUserId())
                .append("scheId", getScheId())
                .append("voluntary", getVoluntary())
                .append("tableName", getTableName())
                .append("createTime", getCreateTime())
                .toString();
    }
}
