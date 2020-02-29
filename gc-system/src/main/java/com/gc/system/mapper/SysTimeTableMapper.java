package com.gc.system.mapper;

import com.gc.system.domain.GcSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 我的课表 - 数据层
 *
 * @author gc
 **/
public interface SysTimeTableMapper {

    /**
     * 查询指定用户的已选课表信息
     **/
    public List<GcSchedule> selectCourseListByUserId(@Param("tableName") String tableName, @Param("userId") String userId);

    /**
     * 根据学院ID获取学院对应的 【已选课表】 的数据表名
     **/
    public String getTableName(Long instId);

    /**
     * 退选课程
     *
     * @Param [tableName, userId, scheId]
     * @return int
     **/
    public int withdrawal(@Param("tableName") String tableName, @Param("userId") Long userId, @Param("scheId") Long scheId);

    /**
     * 更新选课人数 [退选课程后]
     *
     * @Param [scheId]
     * @return int
     **/
    public int reduceSelectedSize(Long scheId);

    /**
     * 获取已选总学分
     *
     * @Param [userId, tableName]
     * @return int
     **/
    public int getTotalCredit(@Param("userId") Long userId, @Param("tableName") String tableName);

}
