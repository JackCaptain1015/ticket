package com.tqmall.ticket.biz.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by jackcaptain on 2017/7/18.
 */

@Data
public class StudentTestBean {
    private Long id;

    private String studentName;

    private Integer studentAge;

    private int stuAge2;

    private BigDecimal stuMoney;

    private Long stuAgeLong;

    private Byte stuAgeByte;

    private Boolean isFlag;

    private String studentAddress;

    private Float aFloat;

    private Double aDouble;

}
