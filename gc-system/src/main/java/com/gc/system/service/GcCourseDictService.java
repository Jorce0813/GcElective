package com.gc.system.service;

import com.gc.system.domain.GcCourseType;

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

}
