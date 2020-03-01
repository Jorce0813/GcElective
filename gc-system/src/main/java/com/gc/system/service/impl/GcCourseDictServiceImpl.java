package com.gc.system.service.impl;

import com.gc.system.domain.GcCourseType;
import com.gc.system.domain.GcInstitue;
import com.gc.system.mapper.GcCourseDictMapper;
import com.gc.system.service.GcCourseDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author gc
 **/
@Service
public class GcCourseDictServiceImpl implements GcCourseDictService {

    @Autowired
    private GcCourseDictMapper gcCourseDictMapper;

    @Override
    public List<GcCourseType> getCourseType() {
        return gcCourseDictMapper.getCourseType();
    }

    @Override
    public List<GcInstitue> getAllInstitue() {
        return gcCourseDictMapper.getAllInstitue();
    }

}
