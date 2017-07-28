package com.tqmall.ticket.web.vo;

import com.tqmall.ticket.biz.bo.TicketOpenCityBO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by wurenzhi on 2017/04/09.
 */
@Getter
@Setter
public class TicketOpenCityListVO {

    //城市名字拼音首字母
    private char firstSpellCityName;

    private List<TicketOpenCityVO> ticketOpenCityVOList;
}
