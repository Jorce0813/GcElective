package com.gc.web.controller.student;

import com.gc.common.core.controller.BaseController;
import com.gc.common.core.domain.AjaxResult;
import com.gc.common.core.page.TableDataInfo;
import com.gc.framework.util.ShiroUtils;
import com.gc.system.domain.GcSchedule;
import com.gc.system.domain.TimeTableModel;
import com.gc.system.service.ISysTimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生中心 - 我的课表
 *
 * @author gc
 **/
@Controller
@RequestMapping("/student/timetable")
public class SysTimeTableController extends BaseController {

    private String prefix = "student/timetable";

    @Autowired
    private ISysTimeTableService iSysTimeTableService;

    /**
     * 获取 [我的课表] 入口
     *
     * @Param []
     * @return java.lang.String
     **/
    @GetMapping()
    public String timeTable(){
        return prefix + "/timetable";
    }

    /**
     * 我的课表列表 表格list
     * 不分页-> 即不调用startPage()方法
     *
     * @Param []
     * @return com.gc.common.core.page.TableDataInfo
     **/
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        Long userId = ShiroUtils.getSysUser().getUserId();
        List<TimeTableModel> list = iSysTimeTableService.selectCourseListByUserId(userId);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(6);
        return rspData;
    }

    /**
     * 我的课表 - 第2个表格显示已选课程的详细信息
     *
     * @Param []
     * @return com.gc.common.core.page.TableDataInfo
     **/
    @PostMapping("/detailList")
    @ResponseBody
    public TableDataInfo detailList() {
        // startPage();
        Long userId = ShiroUtils.getSysUser().getUserId();
        List<GcSchedule> list = iSysTimeTableService.selectScheduleList(userId);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(list.size());
        return rspData;
        // return getDataTable(list);
    }

    /**
     * 退选课程
     * >> 目前采用直接从数据库中删除选课数据的方法, 为了不破坏之前的功能
     * >> 合理的方法是在已选课表中新增 [delFlag] 字段
     *
     * @Param [scheId]
     * @return com.gc.common.core.domain.AjaxResult
     **/
    @PostMapping("/withdrawal")
    @ResponseBody
    @Transactional
    public AjaxResult withdrawal(@RequestParam("scheId") Long scheId) {
        Long userId = ShiroUtils.getSysUser().getUserId();
        int deleteFlag = iSysTimeTableService.withdrawal(userId, scheId);
        if (deleteFlag > 0) {
            int updateFlag = iSysTimeTableService.reduceSelectedSize(scheId);
            if (updateFlag > 0) {
                return success("退选成功");
            } else {
                return error("退选失败, 请稍候重试");
            }
        } else {
            return error("退选失败, 请稍候重试");
        }
    }

}

