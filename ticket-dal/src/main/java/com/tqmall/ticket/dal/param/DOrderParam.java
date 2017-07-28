package com.tqmall.ticket.dal.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wurenzhi on 2017/04/16.
 */
@Getter
@Setter
public class DOrderParam {
    private Integer userId;

    private Integer pageSize;   //每页条数
    private Integer beginNum;
}
