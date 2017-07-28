package com.tqmall.ticket.biz.order;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tqmall.ticket.biz.authorization.AuthorizationService;
import com.tqmall.ticket.biz.base.SmsService;
import com.tqmall.ticket.biz.bo.TicketCinemaBO;
import com.tqmall.ticket.biz.bo.TicketMovieBO;
import com.tqmall.ticket.biz.bo.TicketOrderBO;
import com.tqmall.ticket.biz.bo.TicketSpecificScheduleBO;
import com.tqmall.ticket.biz.cinema.CinemaService;
import com.tqmall.ticket.biz.movie.MovieService;
import com.tqmall.ticket.biz.movie.SpecificScheduleService;
import com.tqmall.ticket.biz.param.BOrderParam;
import com.tqmall.ticket.biz.sys.SysSequenceService;
import com.tqmall.ticket.cache.JedisClient;
import com.tqmall.ticket.cache.RedisKeyBean;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.DateUtil;
import com.tqmall.ticket.common.LoggerUtil;
import com.tqmall.ticket.common.result.Result;
import com.tqmall.ticket.common.status.OrderTypeEnum;
import com.tqmall.ticket.dal.entity.TicketOrder;
import com.tqmall.ticket.dal.entity.TicketSpecificSchedule;
import com.tqmall.ticket.dal.entity.TicketUser;
import com.tqmall.ticket.dal.mapper.TicketOrderMapper;
import com.tqmall.ticket.dal.param.DOrderParam;
import com.tqmall.ticket.error.TicketErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wurenzhi on 2017/04/10.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private TicketOrderMapper ticketOrderMapper;
    @Resource
    private SpecificScheduleService specificScheduleService;
    @Resource
    private SysSequenceService sysSequenceService;
    @Resource
    private JedisClient jedisClient;
    @Resource
    private MovieService movieService;
    @Resource
    private CinemaService cinemaService;
    @Resource
    private AuthorizationService authorizationService;
    @Resource
    private SmsService smsService;

    private static final Lock OrderSeatLock = new ReentrantLock(true);


    @Override
    public Result insertSelective(TicketOrder record) {
        TicketUser currentUser = authorizationService.getCurrentUser();
        if (currentUser == null){
            return Result.wrapErrorResult(TicketErrorCode.USER_AUTH_ERROR.getCode(),TicketErrorCode.USER_AUTH_ERROR.getMessage());
        }
        /**在锁前准备好数据*/
        String[] seatArray = record.getOrderSeat().split("-");
        record.setGmtCreate(new Date());
        record.setGmtModified(new Date());
        record.setOrderStatus(OrderTypeEnum.DFK.getOrderStatus());
        record.setOrderTickets(seatArray.length);
        record.setUserId(currentUser.getId());

        OrderSeatLock.lock();
        try {
            /**判断并发情况下是否存在座位已被锁定的情况*/
            TicketSpecificScheduleBO ticketSpecificScheduleBO = specificScheduleService.selectByPrimaryKey(record.getScheduleId());
            for (String seatStr : seatArray) {
                if ( ticketSpecificScheduleBO.getExistSeatTable().contains(seatStr) ){
                    return Result.wrapErrorResult(TicketErrorCode.ORDER_SEAT_EXIST_ERROR.getCode(),TicketErrorCode.ORDER_SEAT_EXIST_ERROR.getMessage());
                }
            }
            /**座位没被锁定，插入数据库*/
            String orderNo = sysSequenceService.genOrderNo();
            record.setOrderNo(orderNo);
            jedisClient.set(orderNo, RedisKeyBean.FIFTEEN_MINUTE, DateUtil.date2Hms(DateUtil.afterTime(Calendar.MINUTE,15)) );

            BigDecimal ticketsNumBigDecimal = new BigDecimal(record.getOrderTickets());
            record.setOrderPresentPrice( ticketSpecificScheduleBO.getPresentPrice().multiply( ticketsNumBigDecimal ) );
            record.setOrderCinemaPrice( ticketSpecificScheduleBO.getCinemaPrice().multiply( ticketsNumBigDecimal ) );
            record.setMovieShowTime( ticketSpecificScheduleBO.getMovieShowTime() );

            ticketOrderMapper.insertSelective(record);

            TicketOrderBO ticketOrderBO = new TicketOrderBO();
            ticketOrderBO.setId(record.getId());
            ticketOrderBO.setOrderNo(orderNo);
            return Result.wrapSuccessfulResult(ticketOrderBO);
        } catch (Exception e) {
            log.error("订单保存失败，record:{}", LoggerUtil.objectToString(record));
            return Result.wrapErrorResult(TicketErrorCode.ORDER_SAVE_ERROR.getCode(),TicketErrorCode.ORDER_SAVE_ERROR.getMessage());
        }finally{
            OrderSeatLock.unlock();
        }

    }

    @Override
    public TicketOrderBO selectById(Integer orderId) {
        TicketOrder ticketOrder = ticketOrderMapper.selectByPrimaryKey(orderId);
        TicketOrderBO ticketOrderBO = BdUtil.bo2do(ticketOrder, TicketOrderBO.class);

        TicketSpecificScheduleBO ticketSpecificScheduleBO = specificScheduleService.selectByPrimaryKey(ticketOrderBO.getScheduleId());
        TicketMovieBO ticketMovieBO = movieService.selectById(ticketSpecificScheduleBO.getMovieId());
        TicketCinemaBO ticketCinemaBO = cinemaService.selectByPrimaryKey(ticketSpecificScheduleBO.getCinemaId());

        ticketOrderBO.setTicketSpecificScheduleBO(ticketSpecificScheduleBO);
        ticketOrderBO.setTicketMovieBO(ticketMovieBO);
        ticketOrderBO.setTicketCinemaBO(ticketCinemaBO);
        return ticketOrderBO;
    }

    @Transactional
    @Override
    public int update(TicketOrder record) throws IOException {
        record.setGmtModified(new Date());
        record.setOrderStatus(OrderTypeEnum.YFK.getOrderStatus());

        StringBuffer sb = new StringBuffer();
        for (int i = 0 ; i < record.getOrderTickets() ; i++){
            sb = sb.append(sysSequenceService.genTicketNo()).append(",");
        }
        sb = sb.deleteCharAt(sb.length()-1);
        record.setOrderTicketsNo(sb.toString());

        //发送取票码短信
        smsService.ticketsNo(record.getOrderMobile(),sb.toString(),record.getMovieShowTime());

        TicketSpecificScheduleBO ticketSpecificScheduleBO = specificScheduleService.selectByPrimaryKey(record.getScheduleId());
        ticketSpecificScheduleBO.setExistSeatTable( ticketSpecificScheduleBO.getExistSeatTable()+"-"+record.getOrderSeat() );
        TicketSpecificSchedule ticketSpecificSchedule = BdUtil.bo2do(ticketSpecificScheduleBO, TicketSpecificSchedule.class);

        specificScheduleService.updateBySelective(ticketSpecificSchedule);
        return ticketOrderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Result selectAllOrder(BOrderParam bOrderParam) {
        TicketUser currentUser = authorizationService.getCurrentUser();
        if (currentUser == null){
            return Result.wrapErrorResult(TicketErrorCode.USER_AUTH_ERROR.getCode(),TicketErrorCode.USER_AUTH_ERROR.getMessage());
        }
        DOrderParam dOrderParam = BdUtil.bo2do(bOrderParam, DOrderParam.class);
        dOrderParam.setUserId(currentUser.getId());
        List<TicketOrder> ticketOrderList = ticketOrderMapper.selectAllByUserId(dOrderParam);
        List<TicketOrderBO> ticketOrderBOList = BdUtil.bo2do4List(ticketOrderList, TicketOrderBO.class);
        List<TicketOrderBO> returnList = Lists.newArrayList();
        for (TicketOrderBO bo: ticketOrderBOList) {
            TicketOrderBO ticketOrderBO = this.selectById(bo.getId());
            returnList.add(ticketOrderBO);
        }
        return Result.wrapSuccessfulResult(returnList);
    }

    @Override
    public List<String> selectLockSeatByScheduleId(Integer scheduleId) {
        List<TicketOrder> lockedSeatList = ticketOrderMapper.selectLockSeatByScheduleId(scheduleId);

        /**如果当前时间比（订单创建时间+15min）还要大（并且未付款），说明订单已失效，
         * 被锁的座位将被释放（不显示）*/
        Date now = new Date();
        Set<String> set = Sets.newHashSet();
        for (TicketOrder order : lockedSeatList) {
            /**如果还没过期*/
            if ( now.before( DateUtil.dateAdd(order.getGmtCreate(),Calendar.MINUTE,15) ) ){
                String[] seatArray = order.getOrderSeat().split("-");
                set.addAll( Arrays.asList(seatArray) );
            }
        }


        return Lists.newArrayList(set);
    }

    @Override
    public int dealDeadOrder(String orderNo) {
        return ticketOrderMapper.dealDeadOrder(orderNo);
    }

    @Override
    public int getWaitPayOrderCount() {
        TicketUser currentUser = authorizationService.getCurrentUser();
        if (currentUser == null){
            return 0;
        }
        int waitPayOrderCount = ticketOrderMapper.getWaitPayOrderCount(currentUser.getId());
        return waitPayOrderCount;
    }

    @Override
    public Result selectAllWaitPayByUserId(BOrderParam bOrderParam) {
        TicketUser currentUser = authorizationService.getCurrentUser();
        if (currentUser == null){
            return Result.wrapErrorResult(TicketErrorCode.USER_AUTH_ERROR.getCode(),TicketErrorCode.USER_AUTH_ERROR.getMessage());
        }
        DOrderParam dOrderParam = BdUtil.bo2do(bOrderParam, DOrderParam.class);
        dOrderParam.setUserId(currentUser.getId());
        List<TicketOrder> ticketOrderList = ticketOrderMapper.selectAllWaitPayByUserId(dOrderParam);
        List<TicketOrderBO> ticketOrderBOList = BdUtil.bo2do4List(ticketOrderList, TicketOrderBO.class);
        List<TicketOrderBO> returnList = Lists.newArrayList();
        for (TicketOrderBO bo: ticketOrderBOList) {
            TicketOrderBO ticketOrderBO = this.selectById(bo.getId());
            returnList.add(ticketOrderBO);
        }
        return Result.wrapSuccessfulResult(returnList);
    }
}
