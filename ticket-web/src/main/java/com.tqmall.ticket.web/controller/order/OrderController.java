package com.tqmall.ticket.web.controller.order;

import com.google.common.collect.Lists;
import com.tqmall.ticket.biz.authorization.AuthorizationService;
import com.tqmall.ticket.biz.bo.TicketOrderBO;
import com.tqmall.ticket.biz.bo.TicketSpecificScheduleBO;
import com.tqmall.ticket.biz.movie.SpecificScheduleService;
import com.tqmall.ticket.biz.order.OrderService;
import com.tqmall.ticket.biz.param.BOrderParam;
import com.tqmall.ticket.biz.sys.SysSequenceService;
import com.tqmall.ticket.cache.JedisClient;
import com.tqmall.ticket.cache.RedisKeyBean;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.DateUtil;
import com.tqmall.ticket.common.result.Result;
import com.tqmall.ticket.dal.entity.TicketOrder;
import com.tqmall.ticket.dal.entity.TicketUser;
import com.tqmall.ticket.error.TicketErrorCode;
import com.tqmall.ticket.web.param.OrderParam;
import com.tqmall.ticket.web.vo.TicketOrderVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wurenzhi on 2017/04/14.
 */
@Controller
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private JedisClient jedisClient;
    @Resource
    private AuthorizationService authorizationService;


    @RequestMapping( value = "index" , produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET )
    public ModelAndView orderIndex(Integer orderId){
        Assert.notNull("orderId不能为空");

        TicketUser currentUser = authorizationService.getCurrentUser();
        if (currentUser == null){
            return new ModelAndView("page/index");
        }
        ModelAndView modelAndView = new ModelAndView("/page/order-pay");

        TicketOrderBO ticketOrderBO = orderService.selectById(orderId);
        TicketOrderVO ticketOrderVO = BdUtil.bo2do(ticketOrderBO, TicketOrderVO.class);

        ticketOrderVO.setTicketSpecificScheduleBO(ticketOrderBO.getTicketSpecificScheduleBO());
        ticketOrderVO.setTicketMovieBO(ticketOrderBO.getTicketMovieBO());
        ticketOrderVO.setTicketCinemaBO(ticketOrderBO.getTicketCinemaBO());

        modelAndView.addObject(ticketOrderVO);
        return modelAndView;
    }

    @RequestMapping( value = "save" , produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST )
    @ResponseBody
    public Result saveOrder(@RequestBody OrderParam orderParam){
        Assert.notNull(orderParam,"orderParam不能为空");

        TicketOrder ticketOrder = BdUtil.bo2do(orderParam, TicketOrder.class);
        Result result = orderService.insertSelective(ticketOrder);
        TicketOrderVO ticketOrderVO = new TicketOrderVO();
        if ( result != null && result.isSuccess() ){
            BdUtil.bo2do(result.getData(), ticketOrderVO);
        }
        result.setData(ticketOrderVO);
        return result;
    }

    /***
     * 校验订单时效性
     * @param orderNo
     * @return
     */
    @RequestMapping( value = "valid" , produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET )
    @ResponseBody
    public Result validOrder(String orderNo){
        Assert.notNull(orderNo,"订单编号不能为空");
        String orderEndDate = jedisClient.get(orderNo, String.class);
        if (orderEndDate == null){
            orderService.dealDeadOrder(orderNo);
            return Result.wrapSuccessfulResult(false);
        }
        return Result.wrapSuccessfulResult(DateUtil.string2Date(orderEndDate));
    }

    @RequestMapping( value = "pay" , produces = MediaType.APPLICATION_JSON_VALUE , method = RequestMethod.POST )
    @ResponseBody
    public Result confirmPay(Integer orderId) throws IOException {
        Assert.notNull("orderId不能为空");
        TicketOrderBO ticketOrderBO = orderService.selectById(orderId);
        TicketOrder ticketOrder = BdUtil.bo2do(ticketOrderBO, TicketOrder.class);
        orderService.update(ticketOrder);
        return Result.wrapSuccessfulResult(true);
    }

    /**
     * 查看用户所有订单
     */
    @RequestMapping( value = "all" , produces = MediaType.APPLICATION_JSON_VALUE , method = RequestMethod.GET )
    @ResponseBody
    public Result<List<TicketOrderVO>> allOrders(OrderParam orderParam) {
        Result<List<TicketOrderVO>> result = this.getOrderList(orderParam, "all");
        return result;
    }

    /**
     * 分页获取用户待付款订单
     */
    @RequestMapping( value = "all/wait/pay" , produces = MediaType.APPLICATION_JSON_VALUE , method = RequestMethod.GET )
    @ResponseBody
    public Result<List<TicketOrderVO>> allWaitPay(OrderParam orderParam){
        Result<List<TicketOrderVO>> result = this.getOrderList(orderParam, "waitPay");
        return result;
    }

    /**
     * 获取用户待付款订单总数
     */
    @RequestMapping( value = "wait/pay/count" , produces = MediaType.APPLICATION_JSON_VALUE , method = RequestMethod.GET )
    @ResponseBody
    public Result<Integer> waitPayCount(){
        int waitPayOrderCount = orderService.getWaitPayOrderCount();
        return Result.wrapSuccessfulResult(waitPayOrderCount);
    }

    private Result<List<TicketOrderVO>> getOrderList(OrderParam orderParam,String method){
        Assert.notNull(orderParam.getCurNo(),"当前页不能为空");

        BOrderParam bOrderParam = BdUtil.bo2do(orderParam, BOrderParam.class);
        Integer beginNum = ( orderParam.getCurNo()-1 ) * orderParam.getPageSize();
        bOrderParam.setBeginNum(beginNum);

        Result result = null;
        if ("all".equals(method)){
            result = orderService.selectAllOrder(bOrderParam);
        }else if ("waitPay".equals(method)){
            result = orderService.selectAllWaitPayByUserId(bOrderParam);
        }

        List<TicketOrderVO> ticketOrderVOList = Lists.newArrayList();
        if (result != null && result.isSuccess()){
            List<TicketOrderBO> boList = (List<TicketOrderBO>)result.getData();
            ticketOrderVOList = BdUtil.bo2do4List( boList, TicketOrderVO.class);
            for (int i = 0 ; i < ticketOrderVOList.size() ; i++){
                ticketOrderVOList.get(i).setTicketSpecificScheduleBO( boList.get(i).getTicketSpecificScheduleBO() );
                ticketOrderVOList.get(i).setTicketCinemaBO( boList.get(i).getTicketCinemaBO() );
                ticketOrderVOList.get(i).setTicketMovieBO( boList.get(i).getTicketMovieBO() );
            }
        }
        result.setData(ticketOrderVOList);
        return result;
    }
}
