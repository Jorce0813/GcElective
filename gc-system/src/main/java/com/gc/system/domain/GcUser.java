package com.gc.system.domain;

import com.gc.common.annotation.Excel;
import com.gc.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 *  用户对象 gc_user
 *
 *  @author gc
 **/
public class GcUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户ID **/
    @Excel(name = "用户序号", cellType = Excel.ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    /** 用户的学院ID **/
    @Excel(name = "学院编号", cellType = Excel.ColumnType.NUMERIC)
    private Long instId;

    /** 登录名 **/
    private String loginName;

    /** 用户名 **/
    private String userName;

    /** 用户类型-管理员/普通用户 **/
    private String userType;

    /** 邮箱 **/
    private String email;

    /** 电话 **/
    private String phone;

    /** 性别 **/
    private String sex;

    /** 头像 **/
    private String avatar;

    /** 密码 **/
    private String password;

    /** 密码的加密盐值 **/
    private String salt;

    /** 用户状态-正常/停用 **/
    private String status;

    /** 删除标志 **/
    private String delFlag;

    /** 最后登录IP **/
    private String loginIp;

    /** 最后登录时间 **/
    private Date loginDate;

    /** 用户创建时间 **/
    private Date creteTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInstId() {
        return instId;
    }

    public void setInstId(Long instId) {
        this.instId = instId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getCreteTime() {
        return creteTime;
    }

    public void setCreteTime(Date creteTime) {
        this.creteTime = creteTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("instId", getInstId())
                .append("loginName", getLoginName())
                .append("userName", getUserName())
                .append("userType", getUserType())
                .append("email", getEmail())
                .append("phone", getPhone())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("salt", getSalt())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createTime", getCreteTime())
                .toString();
    }
}
