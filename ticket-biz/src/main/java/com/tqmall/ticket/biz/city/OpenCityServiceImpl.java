package com.tqmall.ticket.biz.city;

import com.google.common.collect.Lists;
import com.tqmall.ticket.biz.bo.TicketOpenCityBO;
import com.tqmall.ticket.biz.bo.TicketOpenCityListBO;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.ChineseToSpellUtil;
import com.tqmall.ticket.dal.entity.TicketOpenCity;
import com.tqmall.ticket.dal.mapper.TicketOpenCityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by wurenzhi on 2017/01/09.
 */
@Service
public class OpenCityServiceImpl implements OpenCityService {
    @Resource
    private TicketOpenCityMapper ticketOpenCityMapper;


    @Override
    public List<TicketOpenCityListBO> selectAllCity() {
        List<TicketOpenCity> ticketOpenCities = ticketOpenCityMapper.selectAllCity();
        List<TicketOpenCityBO> ticketOpenCityBOList = BdUtil.bo2do4List(ticketOpenCities, TicketOpenCityBO.class);
        //存入城市中文的拼音到bo中
        for (TicketOpenCityBO bo : ticketOpenCityBOList) {
            bo.setSpellCityName(ChineseToSpellUtil.getSpellStringOfChineseWords(bo.getCityName()));
        }
        //对整个BoList进行排序
        Collections.sort(ticketOpenCityBOList, new Comparator<TicketOpenCityBO>() {
            @Override
            public int compare(TicketOpenCityBO o1, TicketOpenCityBO o2) {
                return o1.getSpellCityName().compareTo(o2.getSpellCityName());
            }
        });
        //对list进行分组,TicketOpenCityListBO为分组后的List
        Map<Character,TicketOpenCityListBO> groupMap = new HashMap<Character,TicketOpenCityListBO>();
        //根据首字母分成26组左右
        for (TicketOpenCityBO bo : ticketOpenCityBOList) {
            groupMap.put(bo.getSpellCityName().charAt(0),null);
        }
        List<TicketOpenCityListBO> resultList = Lists.newArrayList();
        for (Character c : groupMap.keySet()){
            //分组后存list与城市首字母的BO封装类
            TicketOpenCityListBO groupBO = new TicketOpenCityListBO();
            groupBO.setFirstSpellCityName(c);
            List<TicketOpenCityBO> list = Lists.newArrayList();
            for (TicketOpenCityBO bo : ticketOpenCityBOList) {
                if (bo.getSpellCityName().charAt(0) == c){
                    list.add(bo);
                }
            }
            //分组后存城市的list列表存入bo封装类中
            groupBO.setTicketOpenCityBOList(list);
            //bo类存入result中
            resultList.add(groupBO);
        }
        //至此分组完成
        return resultList;
    }

    @Override
    public List<TicketOpenCityBO> selectAreaByCityId(Integer cityId) {
        List<TicketOpenCity> ticketOpenCityList = ticketOpenCityMapper.selectAreaByCityId(cityId);
        List<TicketOpenCityBO> ticketOpenCityBOList = BdUtil.bo2do4List(ticketOpenCityList, TicketOpenCityBO.class);
        return ticketOpenCityBOList;
    }
}
