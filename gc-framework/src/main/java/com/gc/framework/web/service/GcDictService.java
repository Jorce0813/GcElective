package com.gc.framework.web.service;

import com.gc.system.domain.GcCourseType;
import com.gc.system.service.GcCourseDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * html调用 thymeleaf 实现字典读取
 *
 * @author gc
 **/
@Service("gdict")
public class GcDictService {

    @Autowired
    private GcCourseDictService gcCourseDictService;

    /**
     * 获取课程的类型
     *
     * @Param []
     * @return java.util.List<com.gc.system.domain.GcCourseType>
     **/
    public List<GcCourseType> getCourseType(){
        return gcCourseDictService.getCourseType();
    }

}
