package com.gc.system.mapper;

import com.gc.system.domain.GcCourse;
import com.gc.system.domain.GcCourseType;
import com.gc.system.domain.GcInstitue;
import com.gc.system.domain.GcTeacher;

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

    /**
     * 查询所有学院信息
     *
     * @Param []
     * @return java.util.List<com.gc.system.domain.GcInstitue>
     **/
    public List<GcInstitue> getAllInstitue();

    /**
     * 获取所有课程
     *
     * @Param []
     * @return java.util.List<com.gc.system.domain.GcCourse>
     **/
    public List<GcCourse> getAllCourse();

    /**
     * 获取所有老师信息
     *
     * @Param []
     * @return java.util.List<com.gc.system.domain.GcTeacher>
     **/
    public List<GcTeacher> getAllTeacher();

}
