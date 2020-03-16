package com.gc.system.service;

import com.gc.system.domain.GcSc;
import com.gc.system.domain.GcSchedule;
import com.gc.system.domain.GcTimeManage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SysTimeManageService {

    /**
     * 获取选课安排信息
     *
     * @return com.gc.system.domain.GcTimeManage
     * @Param []
     **/
    public GcTimeManage getManage();

    /**
     * 更新选课安排信息
     *
     * @return int
     * @Param []
     **/
    public int update(GcTimeManage manage);

    /**
     * 获取所有排课的ID
     *
     * @return java.util.List<java.lang.Integer>
     * @Param []
     **/
    public List<Long> getScheduleIdList();

    /**
     * 根据 voluntary && scheId 获取[某门课某个志愿]的选课记录[sc_id、user_id]
     * 分库分表的查询总数据 - 目前只有两张表
     *
     * @return java.util.List<com.gc.system.domain.GcSc>
     * @Param [scheId, voluntary]
     **/
    public List<GcSc> getGcScByVoluntaryAndScheId(Long scheId, Long voluntary, String startTime, String endTime);

    public Integer getSelectedSizeById(Long scheId);

    /**
     * 抽签处理
     *
     * @Param []
     * @return int
     **/
    public void draw(int voluntary);

    /**
     * 获取某门排课的课堂人数
     *
     * @return java.lang.Integer
     * @Param [scheId]
     **/
    public Integer getClassSizeById(Long scheId);

    public String getTableName(Long instId);

    public int deleteRecodeById(Map<String, Object> params);

    /**
     * 将被当前志愿选上的同个userId, 将选了该门课程不同志愿进行delete
     *
     * @return int
     * @Param []
     **/
    public int deleteRecode(String tableName, Long userId, int voluntaty, Long courseId);

    /**
     * 计算某门排课的已选中, 比当前志愿小的志愿的已选人数 + 已经选上的人数
     *
     * @return java.lang.Integer
     * @Param [scheId, voluntary]
     **/
    public Integer selectCountOfVoluntary(Long scheId, int voluntary, String startTime, String endTime);

    public int updateSelectedSize(int voluntary);

    /**
     * 批量更新[已选人数]
     *
     * @return int
     * @Param [list]
     **/
    public int batchUpdateSelectedSize(List<GcSchedule> list);



}
