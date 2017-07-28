package com.tqmall.ticket.web.controller.movie;

import com.alibaba.dubbo.common.utils.Assert;
import com.google.common.collect.Lists;
import com.tqmall.ticket.biz.bo.TicketMovieBO;
import com.tqmall.ticket.biz.movie.MovieService;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.result.Result;
import com.tqmall.ticket.web.vo.TicketMovieVO;
import org.omg.CORBA.Request;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wurenzhi on 2017/02/27.
 */
@Controller
@RequestMapping("movie")
public class MovieController {

    @Resource
    private MovieService movieService;

    @RequestMapping(value = "to/list",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public ModelAndView toFullMovie(){
        ModelAndView modelAndView = new ModelAndView("/page/movie-list");
        return modelAndView;
    }

    @RequestMapping(value = "to/detail",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public ModelAndView toFullMovie(Integer movieId){
        Assert.notNull(movieId,"movieId不能为空");
        TicketMovieBO ticketMovieBO = movieService.selectById(movieId);
        TicketMovieVO ticketMovieVO = BdUtil.bo2do(ticketMovieBO, TicketMovieVO.class);
        ModelAndView modelAndView = new ModelAndView("/page/movie-detail");
        modelAndView.addObject(ticketMovieVO);
        return modelAndView;
    }

    /**
     * 正在热映
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "showing",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TicketMovieVO>> selectShowingMovie(Integer pageSize){
        List<TicketMovieBO> ticketMovieBOList = movieService.selectAllShowingMovie(pageSize);
        List<TicketMovieVO> ticketMovieVOList = BdUtil.bo2do4List(ticketMovieBOList, TicketMovieVO.class);

        return Result.wrapSuccessfulResult(ticketMovieVOList);
    }

    /***
     * 即将上映
     */
    @RequestMapping(value = "ready",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TicketMovieVO>> selectReadyMovie(Integer pageSize){
        List<TicketMovieBO> ticketMovieBOList = movieService.selectReadyShowingMovie(pageSize);
        List<TicketMovieVO> ticketMovieVOList = BdUtil.bo2do4List(ticketMovieBOList, TicketMovieVO.class);
        return Result.wrapSuccessfulResult(ticketMovieVOList);
    }

    @RequestMapping(value = "detail",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ResponseBody
    public Result<TicketMovieVO> movieDetail(Integer movieId){
        TicketMovieBO ticketMovieBO = movieService.selectById(movieId);
        TicketMovieVO ticketMovieVO = BdUtil.bo2do(ticketMovieBO, TicketMovieVO.class);
        return Result.wrapSuccessfulResult(ticketMovieVO);
    }
}
