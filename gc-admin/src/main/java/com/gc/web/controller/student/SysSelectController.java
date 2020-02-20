package com.gc.web.controller.student;

import com.gc.common.core.controller.BaseController;
import com.gc.common.core.page.TableDataInfo;
import com.gc.system.domain.GcSchedule;
import com.gc.system.service.GcSelectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生中心 - 选课入口
 *
 * @author gc
 **/
@Controller
@RequestMapping("/student/select")
public class SysSelectController extends BaseController {

    private String prefix = "student/select";

    @Autowired
    private GcSelectService gcSelectService;

    /**
     * 获取选课入口页面
     **/
    @RequiresPermissions("student:select:view")
    @GetMapping()
    public String select() {
        return prefix + "/select";
    }

    /**
     * 获取课程列表
     **/
    // @RequiresPermissions("")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GcSchedule gcSchedule) {
        startPage();
        List<GcSchedule> list = gcSelectService.selectScheduleList(gcSchedule);
        return getDataTable(list);
    }


}
