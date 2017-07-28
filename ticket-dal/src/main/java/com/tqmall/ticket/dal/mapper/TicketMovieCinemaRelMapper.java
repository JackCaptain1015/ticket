package com.tqmall.ticket.dal.mapper;

import com.tqmall.ticket.dal.entity.TicketMovieCinemaRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TicketMovieCinemaRelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TicketMovieCinemaRel record);

    int insertSelective(TicketMovieCinemaRel record);

    TicketMovieCinemaRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TicketMovieCinemaRel record);

    int updateByPrimaryKey(TicketMovieCinemaRel record);

    /**
     * 根据电影Id搜索有该电影排片计划的影院
     * @param movieId
     * @return
     */
    List<TicketMovieCinemaRel> selectByMovieId(Integer movieId);
    /**
     * 根据影院Id搜索该影院下有排片计划的电影
     * @param cinemaId
     * @return
     */
    List<TicketMovieCinemaRel> selectByCinemaId(Integer cinemaId);

    /**
     * 搜索影院的排片日期
     * @param cinemaId
     * @param movieId
     * @return
     */
    TicketMovieCinemaRel selectByCinemaIdAndMovieId(@Param("cinemaId") Integer cinemaId,
                                                    @Param("movieId") Integer movieId);
}