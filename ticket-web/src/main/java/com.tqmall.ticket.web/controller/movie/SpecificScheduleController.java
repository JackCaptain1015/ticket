package com.tqmall.ticket.web.controller.movie;

import com.alibaba.dubbo.common.utils.Assert;
import com.google.common.collect.Lists;
import com.tqmall.ticket.biz.bo.TicketShowroomBO;
import com.tqmall.ticket.biz.bo.TicketSpecificScheduleBO;
import com.tqmall.ticket.biz.movie.SpecificScheduleService;
import com.tqmall.ticket.biz.param.BSpecificScheduleParam;
import com.tqmall.ticket.biz.showroom.ShowroomService;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.result.Result;
import com.tqmall.ticket.web.param.SpecificScheduleParam;
import com.tqmall.ticket.web.vo.TicketCinemaVO;
import com.tqmall.ticket.web.vo.TicketMovieVO;
import com.tqmall.ticket.web.vo.TicketSpecificScheduleVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wurenzhi on 2017/03/03.
 */
@Controller
@RequestMapping("schedule")
public class SpecificScheduleController {

    @Resource
    private SpecificScheduleService specificScheduleService;
    @Resource
    private ShowroomService showroomService;

    @RequestMapping(value = "index",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public ModelAndView index(Integer scheduleId){
        ModelAndView modelAndView = new ModelAndView("/page/order");
        TicketSpecificScheduleBO ticketSpecificScheduleBO = specificScheduleService.selectByPrimaryKey(scheduleId);

        TicketSpecificScheduleVO ticketSpecificScheduleVO = BdUtil.do2bo(ticketSpecificScheduleBO, TicketSpecificScheduleVO.class);
        ticketSpecificScheduleVO.setSeatList(ticketSpecificScheduleBO.getSeatList());
        ticketSpecificScheduleVO.setExistSeatList(ticketSpecificScheduleBO.getExistSeatList());
        ticketSpecificScheduleVO.setTicketMovieVO( BdUtil.bo2do(ticketSpecificScheduleBO.getTicketMovieBO(), TicketMovieVO.class) );
        ticketSpecificScheduleVO.setTicketCinemaVO( BdUtil.bo2do(ticketSpecificScheduleBO.getTicketCinemaBO(), TicketCinemaVO.class) );

        modelAndView.addObject(ticketSpecificScheduleVO);
        return modelAndView;
    }

    /**
     * movieId、cinemaId、day都必填
     * @param param
     * @return
     */
    @RequestMapping(value = "list",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TicketSpecificScheduleVO>> selectSchedualList(SpecificScheduleParam param){
        Assert.notNull(param,"入参不能为空");
        Assert.notNull(param.getMovieId(),"movieId不能为空");
        Assert.notNull(param.getCinemaId(),"cinemaId不能为空");
        Assert.notNull(param.getDay(),"日期不能为空");

        BSpecificScheduleParam bSpecificScheduleParam = BdUtil.bo2do(param, BSpecificScheduleParam.class);
        List<TicketSpecificScheduleBO> ticketSpecificScheduleBOList = specificScheduleService.selectListByMovieIdAndCinemaId(bSpecificScheduleParam);
        List<TicketSpecificScheduleVO> ticketSpecificScheduleVOList = BdUtil.bo2do4List(ticketSpecificScheduleBOList, TicketSpecificScheduleVO.class);
        return Result.wrapSuccessfulResult(ticketSpecificScheduleVOList);
    }

    /**
     * 具体选座与订票
     * @param schedualId
     * @return
     */
    @RequestMapping(value = "detail",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ResponseBody
    public Result<TicketSpecificScheduleVO> schedualDetail(Integer schedualId){
        Assert.notNull(schedualId,"id不能为空");
        //具体某一时刻表中带有showroomId
        //内含已选座位表
        TicketSpecificScheduleBO ticketSpecificScheduleBO = specificScheduleService.selectByPrimaryKey(schedualId);

        TicketSpecificScheduleVO ticketSpecificScheduleVO = BdUtil.do2bo(ticketSpecificScheduleBO, TicketSpecificScheduleVO.class);
        ticketSpecificScheduleVO.setSeatList(ticketSpecificScheduleBO.getSeatList());
        ticketSpecificScheduleVO.setExistSeatList(ticketSpecificScheduleBO.getExistSeatList());
        return Result.wrapSuccessfulResult(ticketSpecificScheduleVO);
    }
}
