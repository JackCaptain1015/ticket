package com.tqmall.ticket.biz.movie;


import com.tqmall.ticket.biz.bo.TicketMovieBO;

import java.util.List;

/**
 * Created by wurenzhi on 2017/01/09.
 */
public interface MovieService {
    //正在热映
    List<TicketMovieBO> selectAllShowingMovie(Integer pageSize);

    //即将热映
    List<TicketMovieBO> selectReadyShowingMovie(Integer pageSize);

    TicketMovieBO selectById(Integer id);
}
