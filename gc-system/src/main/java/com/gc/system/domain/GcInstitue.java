package com.gc.system.domain;

import com.gc.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 学院表 gc_institue
 *
 * @author gc
 **/
public class GcInstitue extends BaseEntity {

    private static final Long serialVersionUID = 1L;

    /** 学院ID **/
    private Long instId;

    /** 学院名称 **/
    private String instName;

    /** 创建时间 **/
    private Date createTime;

    public Long getInstId() {
        return instId;
    }

    public void setInstId(Long instId) {
        this.instId = instId;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
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
                .append("instId", getInstId())
                .append("instName", getInstName())
                .append("createTime", getCreateTime())
                .toString();
    }
}
