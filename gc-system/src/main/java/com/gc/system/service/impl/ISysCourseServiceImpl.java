package com.gc.system.service.impl;

import com.gc.common.core.text.Convert;
import com.gc.common.exception.BusinessException;
import com.gc.common.utils.StringUtils;
import com.gc.common.utils.security.Md5Utils;
import com.gc.system.domain.GcSchedule;
import com.gc.system.mapper.SysCourseMapper;
import com.gc.system.service.ISysCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程管理 - 逻辑层
 *
 * @author gc
 **/
@Service
public class ISysCourseServiceImpl implements ISysCourseService {

    @Autowired
    private SysCourseMapper sysCourseMapper;

    /**
     * 根据ID删除课程信息
     *
     * @return int
     * @Param [ids]
     **/
    @Override
    public int deleteCourseByIds(String ids) {
        Long[] scheIds = Convert.toLongArray(",", ids);
        return sysCourseMapper.deleteCourseByIds(scheIds);
    }

    @Override
    public GcSchedule selectScheduleByScheId(Long userId) {
        return sysCourseMapper.selectScheduleByScheId(userId);
    }

    /**
     * 更新排课信息 + 课程信息
     *
     * @Param [schedule]
     * @return int
     **/
    @Override
    @Transactional
    public int updateSave(GcSchedule schedule) {
        int updateSchedule = sysCourseMapper.updateSchedule(schedule);
        if (updateSchedule > 0) {
            int updateCourse = sysCourseMapper.updateCourse(schedule);
            if (updateCourse > 0) {
                return 1;
            }
        }
        return -1;
    }

    @Override
    public String getCourseNameById(Long courseId) {
        return sysCourseMapper.getCourseNameById(courseId);
    }

    @Override
    public String getTeacherNameById(Long tId) {
        return sysCourseMapper.getTeacherNameById(tId);
    }

    @Override
    public int insertSchedule(GcSchedule schedule) {
        return sysCourseMapper.insertSchedule(schedule);
    }

    /**
     * 导入排课数据
     *
     * @Param [schedule]
     * @return java.lang.String
     **/
    @Override
    @Transactional
    public String importSchedule(List<GcSchedule> scheduleList) {
        if (StringUtils.isNull(scheduleList) || scheduleList.size() == 0) {
            throw new BusinessException("不允许导入空数据");
        }
        // 1. 根据 [teacher_id + course_id + teach_time_loc]的Hash验证排课是否存在
        for (GcSchedule schedule : scheduleList) {
            List<GcSchedule> list = sysCourseMapper.getScheduleById(schedule);
            String hash = Md5Utils.hash(schedule.getTeachTimeAndLoc());
            for (GcSchedule sche : list) {
                String hash1 = Md5Utils.hash(sche.getTeachTimeAndLoc());
                if (hash.equals(hash1)) {//两个MD5Hash串一样, 说明该排课已经存在了
                    throw new BusinessException("teacherId = " + schedule.getTeacherId() + ", courseId = " + schedule.getCourseId() + ", " +
                            "的排课已经存在, 请校验后重试");
                }
            }
            // 验证通过, 插入到数据库
            int insertFlag = sysCourseMapper.insertSchedule(schedule);
            if (insertFlag <= 0) {
                throw new BusinessException("teacherId = " + schedule.getTeacherId() + ", courseId = " + schedule.getCourseId() + ", " +
                        "的排课数据格式不正确, 请校验后重试");
            }
        }
        StringBuilder successMsg = new StringBuilder();
        successMsg.insert(0, "Done: " + scheduleList.size() + "条数据导入成功");
        return successMsg.toString();
    }

    @Override
    public List<GcSchedule> getScheduleList(GcSchedule schedule) {
        return sysCourseMapper.getScheduleList(schedule);
    }
}
