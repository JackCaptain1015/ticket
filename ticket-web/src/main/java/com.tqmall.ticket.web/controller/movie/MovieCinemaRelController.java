package com.tqmall.ticket.web.controller.movie;

import com.tqmall.ticket.biz.bo.TicketMovieCinemaRelBO;
import com.tqmall.ticket.biz.movie.MovieCinemaRelService;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.DateUtil;
import com.tqmall.ticket.common.result.Result;
import com.tqmall.ticket.web.vo.TicketMovieCinemaRelVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wurenzhi on 2017/03/02.
 */
@RequestMapping("movie/cinema/rel")
@Controller
public class MovieCinemaRelController {

    @Resource
    private MovieCinemaRelService movieCinemaRelService;

    @RequestMapping(value = "movie",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TicketMovieCinemaRelVO>> selectByMovieId(Integer movieId){
        Assert.notNull(movieId,"movieId不能为空");

        List<TicketMovieCinemaRelBO> ticketMovieCinemaRelBOList = movieCinemaRelService.selectByMovieId(movieId);
        List<TicketMovieCinemaRelVO> ticketMovieCinemaRelVOList = BdUtil.bo2do4List(ticketMovieCinemaRelBOList,TicketMovieCinemaRelVO.class);
        return Result.wrapSuccessfulResult(ticketMovieCinemaRelVOList);
    }


    @RequestMapping(value = "cinema",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TicketMovieCinemaRelVO>> selectByCinemaId(Integer cinemaId){
        Assert.notNull(cinemaId,"cinemaId不能为空");

        List<TicketMovieCinemaRelBO> ticketMovieCinemaRelBOList = movieCinemaRelService.selectByCinemaId(cinemaId);
        List<TicketMovieCinemaRelVO> ticketMovieCinemaRelVOList = BdUtil.bo2do4List(ticketMovieCinemaRelBOList,TicketMovieCinemaRelVO.class);
        return Result.wrapSuccessfulResult(ticketMovieCinemaRelVOList);
    }

    @RequestMapping(value = "schedule/day",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ResponseBody
    public Result<List<String>> getScheduleDayList(Integer cinemaId,Integer movieId){
        Assert.notNull(cinemaId,"cinemaId不能为空");
        Assert.notNull(movieId,"movieId不能为空");

        List<String> scheduleDayList = movieCinemaRelService.getScheduleDayList(cinemaId, movieId);
        return Result.wrapSuccessfulResult(scheduleDayList);
    }
    //在其中对日期排片日期做转化，取得DateList
//    private List<TicketMovieCinemaRelVO> getRelVOList(List<TicketMovieCinemaRelBO> ticketMovieCinemaRelBOList){
//        List<TicketMovieCinemaRelVO> ticketMovieCinemaRelVOList = BdUtil.bo2do4List(ticketMovieCinemaRelBOList, TicketMovieCinemaRelVO.class);
//
//        for (int i = 0 ; i < ticketMovieCinemaRelBOList.size() ; i++){
//            List<String> betweenDayList = DateUtil.betweenDays( ticketMovieCinemaRelBOList.get(i).getMovieEffectiveDate(),
//                    ticketMovieCinemaRelBOList.get(i).getMovieExpirationDate() );
//            ticketMovieCinemaRelVOList.get(i).setShowDayList(betweenDayList);
//        }
//
//        return ticketMovieCinemaRelVOList;
//    }
}
