package com.gc.web.controller.manage;

import com.gc.common.core.controller.BaseController;
import com.gc.common.core.domain.AjaxResult;
import com.gc.common.core.page.TableDataInfo;
import com.gc.common.utils.poi.ExcelUtil;
import com.gc.system.domain.GcSchedule;
import com.gc.system.service.GcSelectService;
import com.gc.system.service.ISysCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 管理中心 - 排课管理
 *
 * @author gc
 **/
@Controller
@RequestMapping("/manage/course")
public class SysCourseController extends BaseController {

    private String prefix = "manage/course";

    @Autowired
    private GcSelectService gcSelectService;

    @Autowired
    private ISysCourseService courseService;

    /**
     * 获取课程管理入口
     *
     * @return java.lang.String
     * @Param []
     **/
    @GetMapping()
    public String course() {
        return prefix + "/course";
    }

    /**
     * 获取课程列表
     **/
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(GcSchedule gcSchedule) {
        startPage();
        List<GcSchedule> list = gcSelectService.selectScheduleList(gcSchedule);
        return getDataTable(list);
    }

    /**
     * 根据ID删除课程信息
     *
     * @return com.gc.common.core.domain.AjaxResult
     * @Param [ids]
     **/
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        try {
            return toAjax(courseService.deleteCourseByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 编辑课程 - 弹出框事件
     *
     * @return java.lang.String
     * @Param [scheId, mmp]
     **/
    @GetMapping("/update/{scheId}")
    public String update(@PathVariable("scheId") Long scheId, ModelMap mmp) {
        GcSchedule schedule = courseService.selectScheduleByScheId(scheId);
        mmp.put("schedule", schedule);
        return prefix + "/edit";
    }

    /**
     * 编辑课程 - 保存事件
     *
     * @return com.gc.common.core.domain.AjaxResult
     * @Param [schedule]
     **/
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult updateSave(GcSchedule schedule) {
        try {
            return toAjax(courseService.updateSave(schedule));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 新增 - 弹出框
     *
     * @return java.lang.String
     * @Param []
     **/
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增排课 - 保存
     *
     * @return com.gc.common.core.domain.AjaxResult
     * @Param [schedule]
     **/
    @PostMapping("/addSave")
    @ResponseBody
    public AjaxResult addSave(GcSchedule schedule) {
        String courseName = courseService.getCourseNameById(schedule.getCourseId());
        String teacherName = courseService.getTeacherNameById(schedule.getTeacherId());
        schedule.setCourseName(courseName);
        schedule.setTeacherName(teacherName);
        try {
            return toAjax(courseService.insertSchedule(schedule));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 下载 导入模板
     *
     * @return com.gc.common.core.domain.AjaxResult
     * @Param []
     **/
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<GcSchedule> util = new ExcelUtil<>(GcSchedule.class);
        return util.importTemplateExcel("排课数据");
    }

    /**
     * 导入排课数据
     *
     * @return com.gc.common.core.domain.AjaxResult
     * @Param [file]
     **/
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<GcSchedule> util = new ExcelUtil<>(GcSchedule.class);
        List<GcSchedule> scheduleList = util.importExcel(file.getInputStream());
        String message = courseService.importSchedule(scheduleList);
        return AjaxResult.success(message);
    }

    /**
     * 根据条件导出表格数据
     *
     * @return com.gc.common.core.domain.AjaxResult
     * @Param [schedule]
     **/
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult exportData(GcSchedule schedule) {
        List<GcSchedule> list = courseService.getScheduleList(schedule);
        ExcelUtil<GcSchedule> util = new ExcelUtil<>(GcSchedule.class);
        return util.exportExcel(list, "排课数据");
    }

}
