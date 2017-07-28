package com.tqmall.ticket.biz.movie;

import com.google.common.collect.Lists;
import com.tqmall.ticket.biz.bo.TicketCinemaBO;
import com.tqmall.ticket.biz.bo.TicketMovieBO;
import com.tqmall.ticket.biz.bo.TicketShowroomBO;
import com.tqmall.ticket.biz.bo.TicketSpecificScheduleBO;
import com.tqmall.ticket.biz.cinema.CinemaService;
import com.tqmall.ticket.biz.order.OrderService;
import com.tqmall.ticket.biz.param.BSpecificScheduleParam;
import com.tqmall.ticket.biz.showroom.ShowroomService;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.DateUtil;
import com.tqmall.ticket.dal.entity.TicketSpecificSchedule;
import com.tqmall.ticket.dal.mapper.TicketSpecificScheduleMapper;
import com.tqmall.ticket.dal.param.DSpecificScheduleParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wurenzhi on 2017/03/02.
 */
@Service
public class SpecificScheduleServiceImpl implements SpecificScheduleService {

    @Resource
    private TicketSpecificScheduleMapper ticketSpecificScheduleMapper;
    @Resource
    private ShowroomService showroomService;
    @Resource
    private MovieService movieService;
    @Resource
    private CinemaService cinemaService;
    @Resource
    private OrderService orderService;

    @Override
    public List<TicketSpecificScheduleBO> selectListByMovieIdAndCinemaId(BSpecificScheduleParam param) {
        Date theDay= DateUtil.strToDate(param.getDay(),"yyyy-MM-dd");
        String today = DateUtil.date2Ymd(new Date());
        //获取AM零点
        Date amDay = DateUtil.weeHours(theDay, 0);
        /**如果是当天，那么搜索时间为现在到PM零点*/
        if ( today.equals(param.getDay() )){
            amDay = new Date();
        }

        //获取PM零点
        Date pmDay = DateUtil.weeHours(theDay, 1);

        DSpecificScheduleParam dSpecificScheduleParam = BdUtil.bo2do(param, DSpecificScheduleParam.class);
        dSpecificScheduleParam.setBeginTime(amDay);
        dSpecificScheduleParam.setEndTime(pmDay);

        List<TicketSpecificSchedule> ticketSpecificScheduleList = ticketSpecificScheduleMapper.selectListByMovieIdAndCinemaId(dSpecificScheduleParam);
        List<TicketSpecificScheduleBO> ticketSpecificScheduleBOList = BdUtil.bo2do4List(ticketSpecificScheduleList, TicketSpecificScheduleBO.class);
        return ticketSpecificScheduleBOList;
    }

    @Override
    public TicketSpecificScheduleBO selectByPrimaryKey(Integer id) {
        TicketSpecificSchedule ticketSpecificSchedule = ticketSpecificScheduleMapper.selectByPrimaryKey(id);
        TicketSpecificScheduleBO ticketSpecificScheduleBO = BdUtil.do2bo(ticketSpecificSchedule, TicketSpecificScheduleBO.class);

        TicketMovieBO ticketMovieBO = movieService.selectById(ticketSpecificScheduleBO.getMovieId());
        ticketSpecificScheduleBO.setTicketMovieBO(ticketMovieBO);
        TicketCinemaBO ticketCinemaBO = cinemaService.selectByPrimaryKey(ticketSpecificScheduleBO.getCinemaId());
        ticketSpecificScheduleBO.setTicketCinemaBO(ticketCinemaBO);
        //全部座位表
        TicketShowroomBO ticketShowroomBO = showroomService.selectByPrimaryKey(ticketSpecificScheduleBO.getShowroomId());
        //对座位表做处理
        String[] existTableArray = ticketSpecificScheduleBO.getExistSeatTable().split("-");
        List<String> existSeatList = Lists.newArrayList(existTableArray);

        String[] seatArray = ticketShowroomBO.getSeatTable().split("-");
        List<String> seatList = Lists.newArrayList(seatArray);

        /**已被选择的座位要加上被锁定的座位*/
        existSeatList.addAll( orderService.selectLockSeatByScheduleId(id) );
        ticketSpecificScheduleBO.setExistSeatList(existSeatList);

        ticketSpecificScheduleBO.setSeatList(seatList);
        return ticketSpecificScheduleBO;
    }

    @Override
    public int updateBySelective(TicketSpecificSchedule record) {
        record.setGmtModified(new Date());

        return ticketSpecificScheduleMapper.updateByPrimaryKeySelective(record);
    }
}
