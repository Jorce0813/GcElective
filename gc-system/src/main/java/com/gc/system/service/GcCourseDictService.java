package com.gc.system.service;

import com.gc.system.domain.GcCourseType;
import com.gc.system.domain.GcInstitue;

import java.util.List;

/**
 * 字典 业务层
 *
 * @author gc
 **/
public interface GcCourseDictService {

    /**
     * 获取课程类型
     *
     * @Param []
     * @return java.util.List<com.gc.system.domain.GcCourseType>
     **/
    public List<GcCourseType> getCourseType();

    /**
     * 查询所有学院信息
     *
     * @Param []
     * @return java.util.List<com.gc.system.domain.GcInstitue>
     **/
    public List<GcInstitue> getAllInstitue();

}
