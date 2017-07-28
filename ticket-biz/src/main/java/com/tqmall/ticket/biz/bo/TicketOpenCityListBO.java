package com.tqmall.ticket.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by wurenzhi on 2017/01/09.
 */
@Getter
@Setter
public class TicketOpenCityListBO {
    //城市名字拼音首字母
    private char firstSpellCityName;

    private List<TicketOpenCityBO> ticketOpenCityBOList;
}
