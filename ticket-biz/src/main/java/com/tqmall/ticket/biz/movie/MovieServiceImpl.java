package com.tqmall.ticket.biz.movie;

import com.google.common.collect.Lists;
import com.tqmall.ticket.biz.bo.TicketMovieBO;
import com.tqmall.ticket.cache.JedisClient;
import com.tqmall.ticket.cache.RedisKeyBean;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.DateUtil;
import com.tqmall.ticket.dal.entity.TicketMovie;
import com.tqmall.ticket.dal.mapper.TicketMovieMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wurenzhi on 2017/01/09.
 */
@Service
public class MovieServiceImpl implements MovieService {
    @Resource
    private TicketMovieMapper ticketMovieMapper;
    @Resource
    private JedisClient jedisClient;

    @Override
    public List<TicketMovieBO> selectAllShowingMovie(Integer pageSize) {
        List<TicketMovie> ticketMovies = ticketMovieMapper.selectAllShowingMovie(pageSize);
        List<TicketMovieBO> ticketMovieBOList = BdUtil.bo2do4List(ticketMovies, TicketMovieBO.class);

        return ticketMovieBOList;
    }

    @Override
    public List<TicketMovieBO> selectReadyShowingMovie(Integer pageSize) {
        List<TicketMovie> ticketMovies = ticketMovieMapper.selectReadyShowingMovie(pageSize);
        List<TicketMovieBO> ticketMovieBOList = BdUtil.bo2do4List(ticketMovies, TicketMovieBO.class);
        return ticketMovieBOList;
    }

    @Override
    public TicketMovieBO selectById(Integer id) {
        String keySuffixAndDate = id + DateUtil.date2Ymd(new Date());
        String key = String.format(RedisKeyBean.MOVIE_DETAIL_KEY,keySuffixAndDate);
        TicketMovieBO ticketMovieBO = jedisClient.get(key, TicketMovieBO.class);
        /**如果缓存存在，那么直接返回数据*/
        if (ticketMovieBO != null){
            return ticketMovieBO;
        }
        TicketMovie ticketMovie = ticketMovieMapper.selectByPrimaryKey(id);
        ticketMovieBO = BdUtil.bo2do(ticketMovie, TicketMovieBO.class);
        /**update Movie的时候也要做redis更新*/
        jedisClient.set(key,RedisKeyBean.ONE_DAY,ticketMovieBO);
        return ticketMovieBO;
    }

}
