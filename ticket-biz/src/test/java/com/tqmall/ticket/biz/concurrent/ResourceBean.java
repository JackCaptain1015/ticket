package com.tqmall.ticket.biz.concurrent;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by wurenzhi on 2017/05/17.
 */
@Getter
@Setter
public class ResourceBean {
    volatile Integer count ;
    private Boolean isOpen;
    private List<Integer> list;
}
