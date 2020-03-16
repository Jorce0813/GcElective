package com.gc.system.domain;

import com.gc.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 课程安排表 - gc_time_manage
 *
 * @author gc
 **/
public class GcTimeManage extends BaseEntity {

    /** ID */
    private Long id;

    /** 开始时间 */
    private Date startTime;

    /** 结束时间 */
    private Date endTime;

    /** 抽签标记: 0-未抽签  1-已抽签 */
    private int drawFlag;

    /** 备注 */
    private String note;

    /** 更新时间 */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getDrawFlag() {
        return drawFlag;
    }

    public void setDrawFlag(int drawFlag) {
        this.drawFlag = drawFlag;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("rotation", getDrawFlag())
                .append("note", getNote())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
