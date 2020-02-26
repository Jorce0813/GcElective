package com.gc.web.controller.student;

import com.gc.common.core.controller.BaseController;
import com.gc.common.core.domain.AjaxResult;
import com.gc.common.core.page.TableDataInfo;
import com.gc.framework.util.ShiroUtils;
import com.gc.system.domain.GcSc;
import com.gc.system.domain.GcSchedule;
import com.gc.system.service.GcSelectService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 判断 [用户:课程 = 1:1] 是否已选择课程
     *
     * @Param [scheId]
     * @return com.gc.common.core.domain.AjaxResult
     **/
    @GetMapping("/isSelected/{scheId}")
    @ResponseBody
    public AjaxResult isSelected(@PathVariable("scheId") Long scheId) {
        Long userId = ShiroUtils.getSysUser().getUserId();
        GcSc gcSc = new GcSc();
        gcSc.setUserId(userId);
        gcSc.setScheId(scheId);
        Integer isSlected = gcSelectService.isSelected(gcSc);
        System.out.println("isSelected: " + isSlected);
        if (isSlected > 0) {
            return error("该课程已选");
        } else {
            return success();
        }
    }

    /**
     * 判断是否选课冲突
     *
     * @Param []
     * @return com.gc.common.core.domain.AjaxResult
     **/
    @GetMapping("/isConflict/{scheId}")
    @ResponseBody
    public AjaxResult isConflict(@PathVariable("scheId") Long scheId) {
        // get用户ID-> 参数GcSc-> 联合查询get该用户已选的课程的[teachTimeAndLoc]->
        // 冲突判断后返回boolean值-> Controller层返回AjaxResult 给前端
        Long userId = ShiroUtils.getSysUser().getUserId();
        GcSc gcSc = new GcSc();
        gcSc.setUserId(userId);
        gcSc.setScheId(scheId);
        String conflictCourseName = gcSelectService.isConflict(gcSc);
        if (conflictCourseName == null) {
            return success("可以选择该课程");
        } else { // 冲突了就返回第一个冲突的课程名
            return error(conflictCourseName);
        }
    }


    /**
     * 选择课程 - 弹出框事件
     *
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping("/choose/{scheId}")
    public String choose(@PathVariable("scheId") Long scheId, ModelMap mmap) {
        GcSchedule gcSchedule = gcSelectService.selectScheduleByScheId(scheId);
        mmap.put("gcSchedule", gcSchedule);
        return prefix + "/choose";
    }


    /**
     * 选择课程 - 提交事件
     * 先读后写的情况要考虑多线程控制, 在这里不存在该情况
     *
     * @Param gcSc
     * @return com.gc.common.core.domain.AjaxResult
     **/
    @PostMapping("/choose")
    @ResponseBody
    @Transactional
    public AjaxResult choose(GcSc gcSc) {
        // 1. 先判断 [相同志愿下是否有课程冲突]
        Long userId = ShiroUtils.getSysUser().getUserId();
        gcSc.setUserId(userId);
        String conflictCourseName = gcSelectService.isConflict(gcSc);

        if (conflictCourseName == null) { // [课程 + 志愿没有冲突]
            // 2. [更新已选人数 + 插入已选课表]-> Spring声明式事务
            int updateFlag = gcSelectService.addSelectedSize(gcSc);
            if (updateFlag > 0) { //更新成功
                // Long userId = ShiroUtils.getSysUser().getUserId();
                gcSc.setUserId(userId);
                int insertFlag = gcSelectService.insertSelectedCourse(gcSc);
                if (insertFlag > 0) {
                    return success("选课成功");
                } else {
                    return error("选课失败, 请刷新页面或重试");
                }
            } else {
                return error("选课失败, 请刷新页面或重试");
            }
        } else { // [课程 + 志愿] 冲突: 返回第一个冲突的 [课程名冲突] 信息
            return error("与已选课程 【" + conflictCourseName + "】 志愿冲突");
        }
    }


}
