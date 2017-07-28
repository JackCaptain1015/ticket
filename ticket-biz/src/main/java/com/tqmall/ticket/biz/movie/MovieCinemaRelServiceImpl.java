package com.tqmall.ticket.biz.movie;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tqmall.ticket.biz.bo.TicketMovieCinemaRelBO;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.DateUtil;
import com.tqmall.ticket.dal.entity.TicketMovieCinemaRel;
import com.tqmall.ticket.dal.mapper.TicketMovieCinemaRelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wurenzhi on 2017/03/02.
 */
@Service
public class MovieCinemaRelServiceImpl implements MovieCinemaRelService {

    @Resource
    private TicketMovieCinemaRelMapper ticketMovieCinemaRelMapper;

    @Override
    public List<TicketMovieCinemaRelBO> selectByMovieId(Integer movieId) {
        List<TicketMovieCinemaRel> ticketMovieCinemaRelList = ticketMovieCinemaRelMapper.selectByMovieId(movieId);
        List<TicketMovieCinemaRelBO> ticketMovieCinemaRelBOList = BdUtil.bo2do4List(ticketMovieCinemaRelList, TicketMovieCinemaRelBO.class);
        return ticketMovieCinemaRelBOList;
    }

    @Override
    public List<TicketMovieCinemaRelBO> selectByCinemaId(Integer cinemaId) {
        List<TicketMovieCinemaRel> ticketMovieCinemaRelList = ticketMovieCinemaRelMapper.selectByCinemaId(cinemaId);
        List<TicketMovieCinemaRelBO> ticketMovieCinemaRelBOList = BdUtil.bo2do4List(ticketMovieCinemaRelList, TicketMovieCinemaRelBO.class);
        return ticketMovieCinemaRelBOList;
    }

    @Override
    public List<String> getScheduleDayList(Integer cinemaId, Integer movieId) {
        TicketMovieCinemaRel ticketMovieCinemaRel = ticketMovieCinemaRelMapper.selectByCinemaIdAndMovieId(cinemaId, movieId);

        List<String> betweenDayList = DateUtil.betweenDays( ticketMovieCinemaRel.getMovieEffectiveDate(),
                ticketMovieCinemaRel.getMovieExpirationDate() );

        String today = DateUtil.date2YYmdChinese(Calendar.getInstance().getTime());

        List returnList = Lists.newArrayList();
        for (int i = 0 ; i < betweenDayList.size() ; i++) {

            if ( today.equals( betweenDayList.get(i) ) ){
                for (int j = i ; j < i+5 ; j++){
                    returnList.add(betweenDayList.get(j));
                }
                break;
            }
        }
        return returnList;
    }

}
