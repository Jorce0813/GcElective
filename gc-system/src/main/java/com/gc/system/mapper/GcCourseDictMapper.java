package com.gc.system.mapper;

import com.gc.system.domain.GcCourseType;

import java.util.List;

/**
 * 字典表 - 课程类型表
 *
 * @author gc
 **/
public interface GcCourseDictMapper {

    /**
     * 查看课程的类型
     *
     * @Param []
     * @return java.util.List<com.gc.system.domain.GcCourseType>
     **/
    public List<GcCourseType> getCourseType();

}
