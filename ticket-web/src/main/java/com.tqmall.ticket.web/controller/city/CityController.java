package com.tqmall.ticket.web.controller.city;

import com.alibaba.dubbo.common.utils.Assert;
import com.google.common.collect.Lists;
import com.tqmall.ticket.biz.bo.TicketOpenCityBO;
import com.tqmall.ticket.biz.bo.TicketOpenCityListBO;
import com.tqmall.ticket.biz.city.OpenCityService;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.result.Result;
import com.tqmall.ticket.web.vo.TicketOpenCityListVO;
import com.tqmall.ticket.web.vo.TicketOpenCityVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wurenzhi on 2017/04/09.
 */
@RequestMapping("city")
@Controller
public class CityController {

    @Resource
    private OpenCityService openCityService;

    @RequestMapping(value = "all" , produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TicketOpenCityListVO>> getAllCity(){
        List<TicketOpenCityListBO> ticketOpenCityListBOList = openCityService.selectAllCity();
        List<TicketOpenCityListVO> ticketOpenCityListVOList = Lists.newArrayList();
        for (TicketOpenCityListBO bo : ticketOpenCityListBOList) {
            List<TicketOpenCityVO> ticketOpenCityVOList = BdUtil.bo2do4List(bo.getTicketOpenCityBOList(), TicketOpenCityVO.class);

            TicketOpenCityListVO ticketOpenCityListVO = new TicketOpenCityListVO();
            ticketOpenCityListVO.setTicketOpenCityVOList(ticketOpenCityVOList);
            ticketOpenCityListVO.setFirstSpellCityName(bo.getFirstSpellCityName());

            ticketOpenCityListVOList.add(ticketOpenCityListVO);
        }
        return Result.wrapSuccessfulResult(ticketOpenCityListVOList);
    }

    @RequestMapping(value = "get/area" , produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TicketOpenCityVO>> getArea(Integer cityId){
        Assert.notNull(cityId,"城市id不能为空");
        List<TicketOpenCityBO> ticketOpenCityBOList = openCityService.selectAreaByCityId(cityId);
        List<TicketOpenCityVO> ticketOpenCityVOList = BdUtil.bo2do4List(ticketOpenCityBOList, TicketOpenCityVO.class);
        return Result.wrapSuccessfulResult(ticketOpenCityVOList);
    }
}
