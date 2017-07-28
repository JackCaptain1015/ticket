package com.tqmall.ticket.web.controller.cinema;

import com.alibaba.dubbo.common.utils.Assert;
import com.tqmall.ticket.biz.bo.TicketCinemaBO;
import com.tqmall.ticket.biz.cinema.CinemaService;
import com.tqmall.ticket.biz.param.BCinemaParam;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.result.Result;
import com.tqmall.ticket.web.param.CinemaParam;
import com.tqmall.ticket.web.vo.TicketCinemaVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wurenzhi on 2017/02/27.
 */
@RequestMapping("cinema")
@Controller
public class CinemaController {

    @Resource
    private CinemaService cinemaService;

    /**
     * 跳转到搜索页面
     */
    @RequestMapping(value = "to/search",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public ModelAndView toSearchView(){
        ModelAndView modelAndView = new ModelAndView("/page/cinema-list");
        return modelAndView;
    }

    @RequestMapping(value = "to/detail",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public ModelAndView toDetailView(Integer cinemaId){
        Assert.notNull(cinemaId,"cinemaId不能为空");

        TicketCinemaBO ticketCinemaBO = cinemaService.selectByPrimaryKey(cinemaId);
        TicketCinemaVO ticketCinemaVO = BdUtil.bo2do(ticketCinemaBO, TicketCinemaVO.class);

        ModelAndView modelAndView = new ModelAndView("/page/cinema-detail");
        modelAndView.addObject(ticketCinemaVO);
        return modelAndView;
    }
    /**
     * 影院查找
     * @param cinemaParam
     * @return
     */
    @RequestMapping(value = "search",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TicketCinemaVO>> searchCinema(CinemaParam cinemaParam){
        Assert.notNull(cinemaParam.getCityId(),"城市id不能为空");
        Assert.notNull(cinemaParam.getCurNo(),"页号不能为空");

        BCinemaParam bCinemaParam = BdUtil.bo2do(cinemaParam, BCinemaParam.class);
        Integer beginNum = ( cinemaParam.getCurNo()-1 ) * cinemaParam.getPageSize();
        bCinemaParam.setBeginNum(beginNum);
        List<TicketCinemaBO> ticketCinemaBOList = cinemaService.searchByParam(bCinemaParam);
        List<TicketCinemaVO> ticketCinemaVOList = BdUtil.bo2do4List(ticketCinemaBOList, TicketCinemaVO.class);
        return Result.wrapSuccessfulResult(ticketCinemaVOList);
    }

    /**
     * 城市部分影院
     * @param cityId
     * @return
     */
    @RequestMapping(value = "city",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TicketCinemaVO>> cityCinema(Integer cityId){
        Assert.notNull(cityId,"城市id不能为空");

        List<TicketCinemaBO> ticketCinemaBOList = cinemaService.selectByCityId(cityId);
        List<TicketCinemaVO> ticketCinemaVOList = BdUtil.bo2do4List(ticketCinemaBOList, TicketCinemaVO.class);
        return Result.wrapSuccessfulResult(ticketCinemaVOList);
    }

    /**
     * 区域中包含的影院
     * @param cityId
     * @return
     */
    @RequestMapping(value = "area",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TicketCinemaVO>> areaCinema(Integer areaId){
        Assert.notNull(areaId,"areaId不能为空");

        List<TicketCinemaBO> ticketCinemaBOList = cinemaService.selectByAreaId(areaId);
        List<TicketCinemaVO> ticketCinemaVOList = BdUtil.bo2do4List(ticketCinemaBOList, TicketCinemaVO.class);
        return Result.wrapSuccessfulResult(ticketCinemaVOList);
    }
}
