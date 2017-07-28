package com.tqmall.ticket.dal.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wurenzhi on 2017/03/01.
 */
@Getter
@Setter
public class DCinemaParam {
    private Integer cityId;
    private Integer areaId;
    //搜索关键字
    private String param;

    private Integer pageSize;   //每页条数
    private Integer beginNum;
}
