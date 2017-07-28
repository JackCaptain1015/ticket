package com.tqmall.ticket.web.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wurenzhi on 2017/02/27.
 */
@Getter
@Setter
public class CinemaParam extends PageParam{
    private Integer cityId;
    private Integer areaId;
    //搜索关键字
    private String param;

}
