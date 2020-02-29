package com.gc.system.domain;

import com.gc.common.core.domain.BaseEntity;

/**
 * 我的课表的显示 model
 *
 * @author gc
 **/
public class TimeTableModel extends BaseEntity {

    /** 节次 */
    private Integer point;

    /** w1v1: 代表周一 第 1 志愿 */
    /** w1v1 */
    private String w1v1;

    /** w1v2 */
    private String w1v2;

    /** w1v3 */
    private String w1v3;

    /** w2v1 */
    private String w2v1;

    /** w2v2 */
    private String w2v2;

    /** w2v3 */
    private String w2v3;

    /** w3v1 */
    private String w3v1;

    /** w3v2 */
    private String w3v2;

    /** w3v3 */
    private String w3v3;

    /** w4v1 */
    private String w4v1;

    /** w4v2 */
    private String w4v2;

    /** w4v3 */
    private String w4v3;

    /** w5v1 */
    private String w5v1;

    /** w5v2 */
    private String w5v2;

    /** w5v3 */
    private String w5v3;

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getW1v1() {
        return w1v1;
    }

    public void setW1v1(String w1v1) {
        this.w1v1 = w1v1;
    }

    public String getW1v2() {
        return w1v2;
    }

    public void setW1v2(String w1v2) {
        this.w1v2 = w1v2;
    }

    public String getW1v3() {
        return w1v3;
    }

    public void setW1v3(String w1v3) {
        this.w1v3 = w1v3;
    }

    public String getW2v1() {
        return w2v1;
    }

    public void setW2v1(String w2v1) {
        this.w2v1 = w2v1;
    }

    public String getW2v2() {
        return w2v2;
    }

    public void setW2v2(String w2v2) {
        this.w2v2 = w2v2;
    }

    public String getW2v3() {
        return w2v3;
    }

    public void setW2v3(String w2v3) {
        this.w2v3 = w2v3;
    }

    public String getW3v1() {
        return w3v1;
    }

    public void setW3v1(String w3v1) {
        this.w3v1 = w3v1;
    }

    public String getW3v2() {
        return w3v2;
    }

    public void setW3v2(String w3v2) {
        this.w3v2 = w3v2;
    }

    public String getW3v3() {
        return w3v3;
    }

    public void setW3v3(String w3v3) {
        this.w3v3 = w3v3;
    }

    public String getW4v1() {
        return w4v1;
    }

    public void setW4v1(String w4v1) {
        this.w4v1 = w4v1;
    }

    public String getW4v2() {
        return w4v2;
    }

    public void setW4v2(String w4v2) {
        this.w4v2 = w4v2;
    }

    public String getW4v3() {
        return w4v3;
    }

    public void setW4v3(String w4v3) {
        this.w4v3 = w4v3;
    }

    public String getW5v1() {
        return w5v1;
    }

    public void setW5v1(String w5v1) {
        this.w5v1 = w5v1;
    }

    public String getW5v2() {
        return w5v2;
    }

    public void setW5v2(String w5v2) {
        this.w5v2 = w5v2;
    }

    public String getW5v3() {
        return w5v3;
    }

    public void setW5v3(String w5v3) {
        this.w5v3 = w5v3;
    }

    /**
     * 根据 [周几 + 第几志愿] 进行对象属性的赋值
     **/
    public void setWv(String week, String voluntaty, String courseName) {
        if (week.equals("1") && voluntaty.equals("1")) {
            setW1v1(courseName);
        } else if (week.equals("1") && voluntaty.equals("2")) {
            setW1v2(courseName);
        } else if (week.equals("1") && voluntaty.equals("3")) {
            setW1v3(courseName);
        } else if (week.equals("2") && voluntaty.equals("1")) {
            setW2v1(courseName);
        } else if (week.equals("2") && voluntaty.equals("2")) {
            setW2v2(courseName);
        } else if (week.equals("2") && voluntaty.equals("3")) {
            setW2v3(courseName);
        } else if (week.equals("3") && voluntaty.equals("1")) {
            setW3v1(courseName);
        } else if (week.equals("3") && voluntaty.equals("2")) {
            setW3v2(courseName);
        } else if (week.equals("3") && voluntaty.equals("3")) {
            setW3v3(courseName);
        } else if (week.equals("4") && voluntaty.equals("1")) {
            setW4v1(courseName);
        } else if (week.equals("4") && voluntaty.equals("2")) {
            setW4v2(courseName);
        } else if (week.equals("4") && voluntaty.equals("3")) {
            setW4v3(courseName);
        } else if (week.equals("5") && voluntaty.equals("1")) {
            setW5v1(courseName);
        } else if (week.equals("5") && voluntaty.equals("2")) {
            setW5v2(courseName);
        } else if (week.equals("5") && voluntaty.equals("3")) {
            setW5v3(courseName);
        }
    }
}
