package com.tqmall.ticket.biz.util;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by jackcaptain on 2017/7/18.
 */
public class StudentTestBeanVO {
    private String studentName;

    private Integer studentAge;

    private String studentAddress;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(Integer studentAge) {
        this.studentAge = studentAge;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }
}
