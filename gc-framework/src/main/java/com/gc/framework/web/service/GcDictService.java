package com.gc.framework.web.service;

import com.gc.framework.util.ShiroUtils;
import com.gc.system.domain.GcCourseType;
import com.gc.system.domain.GcInstitue;
import com.gc.system.service.GcCourseDictService;
import com.gc.system.service.ISysTimeTableService;
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

    @Autowired
    private ISysTimeTableService iSysTimeTableService;

    /**
     * 获取课程的类型
     *
     * @Param []
     * @return java.util.List<com.gc.system.domain.GcCourseType>
     **/
    public List<GcCourseType> getCourseType(){
        return gcCourseDictService.getCourseType();
    }

    /**
     * 获取已选课程的总学分
     *
     * @Param []
     * @return int
     **/
    public int getTotalCredit(){
        Long userId = ShiroUtils.getSysUser().getUserId();
        int creditSum = iSysTimeTableService.getTotalCredit(userId);
        return creditSum;
    }

    /**
     * 获取所有学院
     *
     * @Param []
     * @return java.util.List<com.gc.system.domain.GcInstitue>
     **/
    public List<GcInstitue> getInstitue(){
        return gcCourseDictService.getAllInstitue();
    }

}
