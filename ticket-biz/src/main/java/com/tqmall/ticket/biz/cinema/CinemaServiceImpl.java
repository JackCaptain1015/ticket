package com.tqmall.ticket.biz.cinema;

import com.tqmall.ticket.biz.bo.TicketCinemaBO;
import com.tqmall.ticket.biz.bo.TicketMovieBO;
import com.tqmall.ticket.biz.param.BCinemaParam;
import com.tqmall.ticket.cache.JedisClient;
import com.tqmall.ticket.cache.RedisKeyBean;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.DateUtil;
import com.tqmall.ticket.dal.entity.TicketCinema;
import com.tqmall.ticket.dal.mapper.TicketCinemaMapper;
import com.tqmall.ticket.dal.param.DCinemaParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wurenzhi on 2017/01/09.
 */
@Service
public class CinemaServiceImpl implements CinemaService {
    @Resource
    private TicketCinemaMapper ticketCinemaMapper;
    @Resource
    private JedisClient jedisClient;

    @Override
    public int insertSelective(TicketCinema record) {
        record.setGmtModified(new Date());
        record.setGmtCreate(new Date());
        String cityCinemaKey = String.format( RedisKeyBean.CINEMA_CITY_KEY,
                record.getCinemaCityId() + DateUtil.date2Ymd(new Date()) );
        String areaCinemaKey = String.format( RedisKeyBean.CINEMA_AREA_KEY,
                record.getCinemaAreaId() + DateUtil.date2Ymd(new Date()) );
        jedisClient.trimAllList(cityCinemaKey);
        jedisClient.trimAllList(areaCinemaKey);
        return ticketCinemaMapper.insertSelective(record);
    }

    @Override
    public List<TicketCinemaBO> selectByCityId(Integer cityId) {
        String keySuffixAndDate = cityId + DateUtil.date2Ymd(new Date());
        String key = String.format(RedisKeyBean.CINEMA_CITY_KEY,keySuffixAndDate);
        List<TicketCinemaBO> ticketCinemaBOList = jedisClient.getListAllRange(key, TicketCinemaBO.class);
        /**如果缓存存在，那么直接返回数据*/
        if (ticketCinemaBOList.size() > 0){
            return ticketCinemaBOList;
        }
        List<TicketCinema> ticketCinemaList = ticketCinemaMapper.selectByCityId(cityId);
        ticketCinemaBOList = BdUtil.bo2do4List(ticketCinemaList, TicketCinemaBO.class);
        jedisClient.pushList(key,RedisKeyBean.ONE_DAY,ticketCinemaBOList);
        return ticketCinemaBOList;
    }

    @Override
    public List<TicketCinemaBO> selectByAreaId(Integer areaId) {
        String keySuffixAndDate = areaId + DateUtil.date2Ymd(new Date());
        String key = String.format(RedisKeyBean.CINEMA_AREA_KEY,keySuffixAndDate);
        List<TicketCinemaBO> ticketCinemaBOList = jedisClient.getListAllRange(key, TicketCinemaBO.class);
        /**如果缓存存在，那么直接返回数据*/
        if (ticketCinemaBOList.size() > 0){
            return ticketCinemaBOList;
        }
        List<TicketCinema> ticketCinemaList = ticketCinemaMapper.selectByAreaId(areaId);
        ticketCinemaBOList = BdUtil.bo2do4List(ticketCinemaList, TicketCinemaBO.class);
        jedisClient.pushList(key,RedisKeyBean.ONE_DAY,ticketCinemaBOList);
        return ticketCinemaBOList;
    }

    @Override
    public List<TicketCinemaBO> searchByParam(BCinemaParam bCinemaParam) {
        DCinemaParam dCinemaParam = BdUtil.bo2do(bCinemaParam, DCinemaParam.class);
        List<TicketCinema> ticketCinemaList = ticketCinemaMapper.searchByParam(dCinemaParam);
        List<TicketCinemaBO> ticketCinemaBOList = BdUtil.bo2do4List(ticketCinemaList, TicketCinemaBO.class);
        return ticketCinemaBOList;
    }

    @Override
    public TicketCinemaBO selectByPrimaryKey(Integer id) {
        String keySuffixAndDate = id + DateUtil.date2Ymd(new Date());
        String key = String.format(RedisKeyBean.CINEMA_DETAIL_KEY,keySuffixAndDate);
        TicketCinemaBO ticketCinemaBO = jedisClient.get(key, TicketCinemaBO.class);
        /**如果缓存存在，那么直接返回数据*/
        if (ticketCinemaBO != null){
            return ticketCinemaBO;
        }
        TicketCinema ticketCinema = ticketCinemaMapper.selectByPrimaryKey(id);
        ticketCinemaBO = BdUtil.bo2do(ticketCinema, TicketCinemaBO.class);
        /**update Movie的时候也要做redis更新*/
        jedisClient.set(key,RedisKeyBean.ONE_DAY,ticketCinemaBO);
        return ticketCinemaBO;
    }
}
