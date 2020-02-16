package com.gc.web.controller.student;

import com.gc.common.core.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 学生中心 - 选课入口
 *
 * @author gc
 **/
@Controller
@RequestMapping("/student/select")
public class SysSelectController extends BaseController {

    private String prefix = "student/select";

    @RequiresPermissions("student:select:view")
    @GetMapping()
    public String select() {
        return prefix + "/select";
    }

}
