package com.tqmall.ticket.web.websocket;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tqmall.ticket.biz.authorization.AuthorizationService;
import com.tqmall.ticket.biz.bo.TicketOrderBO;
import com.tqmall.ticket.biz.order.OrderService;
import com.tqmall.ticket.biz.param.BOrderParam;
import com.tqmall.ticket.cache.JedisClient;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.JsonUtil;
import com.tqmall.ticket.common.result.Result;
import com.tqmall.ticket.dal.entity.TicketUser;
import com.tqmall.ticket.web.vo.TicketOrderVO;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wurenzhi on 2017/06/07.
 */
@Component
public class OrderWebSocketHandler implements WebSocketHandler{

    @Resource
    private OrderService orderService;
    @Resource
    private AuthorizationService authorizationService;
    @Resource
    private JedisClient jedisClient;

    private static final Map<Integer,WebSocketSession> webSocketSessionMap = Maps.newConcurrentMap();
    /**
     * 建立连接后执行该方法
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        TicketUser currentUser = authorizationService.getCurrentUser();
        if (currentUser != null){
            webSocketSessionMap.put(currentUser.getId(),webSocketSession);
        }
//        TicketUser currentUser = authorizationService.getCurrentUser();
//        if (currentUser != null){
//            /**检查所有待付款订单，如果失效则放入列表中*/
//            /**获取失效订单*/
//            BOrderParam bOrderParam = new BOrderParam();
//            bOrderParam.setUserId(currentUser.getId());
//            Result result = orderService.selectAllWaitPayByUserId(bOrderParam);
//
//            List<TicketOrderVO> invalidOrderList = Lists.newArrayList();
//            if (result != null && result.isSuccess()){
//                List<TicketOrderBO> boList = (List<TicketOrderBO>)result.getData();
//                List<TicketOrderVO> ticketOrderVOList = BdUtil.bo2do4List( boList, TicketOrderVO.class);
//                for (TicketOrderVO vo : ticketOrderVOList) {
//                    String orderEndDate = jedisClient.get(vo.getOrderNo(), String.class);
//                    /**失效的订单加入List中*/
//                    if (orderEndDate == null){
//                        orderService.dealDeadOrder(vo.getOrderNo());
//                        invalidOrderList.add(vo);
//                    }
//                }
//
////                for (int i = 0 ; i < ticketOrderVOList.size() ; i++){
////                    ticketOrderVOList.get(i).setTicketSpecificScheduleBO( boList.get(i).getTicketSpecificScheduleBO() );
////                    ticketOrderVOList.get(i).setTicketCinemaBO( boList.get(i).getTicketCinemaBO() );
////                    ticketOrderVOList.get(i).setTicketMovieBO( boList.get(i).getTicketMovieBO() );
////                }
//            }
//        }
    }

    /**
     * 接收前端消息的时候执行该方法
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        TicketUser currentUser = authorizationService.getCurrentUser();
        if (currentUser != null){
            /**检查所有待付款订单，如果失效则放入列表中*/
            /**获取失效订单*/
            BOrderParam bOrderParam = new BOrderParam();
            bOrderParam.setUserId(currentUser.getId());
            Result result = orderService.selectAllWaitPayByUserId(bOrderParam);

            List<TicketOrderVO> invalidOrderList = Lists.newArrayList();
            if (result != null && result.isSuccess()){
                List<TicketOrderBO> boList = (List<TicketOrderBO>)result.getData();
                List<TicketOrderVO> ticketOrderVOList = BdUtil.bo2do4List( boList, TicketOrderVO.class);
                for (TicketOrderVO vo : ticketOrderVOList) {
                    String orderEndDate = jedisClient.get(vo.getOrderNo(), String.class);
                    /**失效的订单加入List中*/
                    if (orderEndDate == null){
                        orderService.dealDeadOrder(vo.getOrderNo());
                        invalidOrderList.add(vo);
                    }
                }
                String invalidOrderListStr = JsonUtil.list2JsonStr(invalidOrderList,"invalidOrderList");
                TextMessage textMessage = new TextMessage(invalidOrderListStr.getBytes());
                webSocketSession.sendMessage(textMessage);
            }
        }
    }

    /**
     * 消息传输错误时执行
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    /**
     * 关闭连接后执行
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

    }

    /**
     *
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
