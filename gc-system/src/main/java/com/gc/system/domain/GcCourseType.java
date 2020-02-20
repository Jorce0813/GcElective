package com.gc.system.domain;

import com.gc.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 课程类型表 gc_course_type
 *
 * @author gc
 **/
public class GcCourseType extends BaseEntity {

    private static final Long serialVersionUID = 1L;

    /** 类型ID **/
    private Long typeId;

    /** 类型名 **/
    private String typeName;

    /** 创建时间 **/
    private Date createTime;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
                .append("typeId", getTypeId())
                .append("typeNae", getTypeName())
                .append("createTime", getCreateTime())
                .toString();
    }
}
