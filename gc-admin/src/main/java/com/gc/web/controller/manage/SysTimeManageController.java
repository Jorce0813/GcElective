package com.gc.web.controller.manage;

import com.gc.common.core.controller.BaseController;
import com.gc.common.core.domain.AjaxResult;
import com.gc.system.domain.GcTimeManage;
import com.gc.system.service.SysTimeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 管理中心 - 选课管理
 *
 * @author gc
 **/
@Controller
@RequestMapping("/manage/time")
public class SysTimeManageController extends BaseController {

    private String prefix = "manage/timemanage";

    @Autowired
    private SysTimeManageService manageService;

    /**
     * 获取 选课管理 页面入口
     *
     * @return java.lang.String
     * @Param []
     **/
    @GetMapping()
    public String manage() {
        return prefix + "/manage";
    }

    /**
     * 设置选课 - 提交事件
     * 建议: 将业务代码迁移到Impl层比较好
     * @Param [manage]
     * @return com.gc.common.core.domain.AjaxResult
     **/
    @PostMapping("/setUpCourseSelect")
    @ResponseBody
    public AjaxResult setUpCourseSelect(GcTimeManage manage) {
        /* (1)选课还没开始: 判断时间是否符合[startTime < endTime && endTime > new Date()]
         * (2)选课已经开始: 只能操作结束时间且: 结束时间 > (当前时间 + 30min)
         * (3)选课已经结束: * 先判断是否已经抽签[是->允许设置选课 ; 否->WARMING先进行抽签 or 联系超级管理员处理]
         *                 * 再判断 [startTime < endTime && startTime > new Date()]
         * 以上判断后再进行update
         */
        Date nowTime = new Date();
        Date paramStartTime = manage.getStartTime();
        Date paramEndTime = manage.getEndTime();
        GcTimeManage timeManage = manageService.getManage();
        Date startTime = timeManage.getStartTime();
        Date endTime = timeManage.getEndTime();
        int drawFlag = timeManage.getDrawFlag();

        if (paramStartTime == null || paramStartTime.toString().trim().equals("") ||
                paramEndTime == null || paramEndTime.toString().trim().equals("")) {
            return error("时间不能为空, 请选择时间");
        }

        if (paramStartTime.getTime() > paramEndTime.getTime()) {
            return error("开始时间不能大于结束时间");
        }

        manage.setDrawFlag(0); // 当已经抽过签要开启新一轮选课时需要设置drawFlag
        if (nowTime.getTime() < startTime.getTime()) { //选课未开始
            if (paramEndTime.getTime() > nowTime.getTime()) {
                manageService.update(manage);
            } else {
                return error("选课结束时间设置无效");
            }
        } else if ((nowTime.getTime() > startTime.getTime()) && nowTime.getTime() < endTime.getTime()) { //选课进行中
            if (paramEndTime.getTime() > (nowTime.getTime() + 30 * 60 * 1000)) {
                manage.setStartTime(null); //将前端传过来的开始时间设置为null
                manageService.update(manage);
            } else {
                return error("选课即将结束, 避免故障, 无法重新设置");
            }
        } else { //选课已经结束
            if (drawFlag == 0) {
                return error("尚未抽签,请先进行选课抽签");
            } else if (paramStartTime.getTime() > nowTime.getTime()) {
                manageService.update(manage);
            } else {
                return error("设置失败,请重新选择选课开始时间");
            }
        }
        return success("选课设置成功");
    }

    /**
     * get 选课安排信息
     *
     * @return com.gc.common.core.domain.AjaxResult
     * @Param []
     **/
    @GetMapping("/get")
    @ResponseBody
    public AjaxResult getManage() {
        GcTimeManage manage = manageService.getManage();
        return AjaxResult.success(manage);
    }

    /**
     * 开始抽签事件
     *
     * @return com.gc.common.core.domain.AjaxResult
     * @Param []
     **/
    @PostMapping("/draw")
    @ResponseBody
    //加了@Transactional之后: 删除没问题, 就是update的时候读取到的是原先的数据, 不是删除后的数据
    public AjaxResult draw() {
        // 判断是否抽过签并做相应的设置
        if (manageService.getManage().getDrawFlag() == 1) {
            return error("已经抽过签, 禁止再次抽签");
        }
        // @Transactional注解的方法放在同一个类中调用会失效, 所以将for()循环迁移至这里
        for (int voluntary = 1; voluntary <= 3; voluntary++) {
            manageService.draw(voluntary);
        }
        // manageService.updateSelectedSize(voluntary + 1) 发生异常, 将会出现voluntary志愿抽签成功但选课人数没更新的情况
        if (manageService.updateSelectedSize(4) < 1) {
            return error("抽签失败, 请重新抽签或联系管理员");
        }
        return success("抽签成功");
    }

}
