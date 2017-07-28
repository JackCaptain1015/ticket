package com.tqmall.ticket.biz.movie;

import com.tqmall.ticket.biz.bo.TicketMovieCinemaRelBO;
import com.tqmall.ticket.dal.entity.TicketMovieCinemaRel;

import java.util.List;

/**
 * Created by wurenzhi on 2017/03/02.
 */
public interface MovieCinemaRelService {

    /**
     * 根据电影Id搜索有该电影排片计划的影院
     * @param movieId
     * @return
     */
    List<TicketMovieCinemaRelBO> selectByMovieId(Integer movieId);
    /**
     * 根据影院Id搜索该影院下有排片计划的电影
     * @param cinemaId
     * @return
     */
    List<TicketMovieCinemaRelBO> selectByCinemaId(Integer cinemaId);

    /**
     * 搜索影院的排片日期
     * @param cinemaId
     * @param movieId
     * @return
     */
    List<String> getScheduleDayList( Integer cinemaId, Integer movieId);
}
