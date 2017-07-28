<link href="/static/css/order.css" rel="stylesheet">
<link href="/static/css/order-pay.css" rel="stylesheet">

<#include "common/head.ftl">
<#include "template/seat.ftl">
<#include "template/signin.ftl">

<div class="center-wrap">
    <div class="seat-step">
        <ul>
            <li class="down"><s></s>1.选择影片，场次</li>
            <li class="down">2.选座，填手机号</li>
            <li class="future current">3.确认订单，支付</li>
            <li class="future">4.支付成功，影院取票观影</li>
        </ul>
    </div>

<div class="seat-tips">
    <div class="time-info">
        剩余支付时间
        <span class="J_time" style="display: none"><s class="scd-digit-m"></s>:<s class="scd-digit-s"></s></span>
    </div>
    请您确认您的订单信息，并请在15分钟内完成付款，如超时系统将自动释放已选座位。
</div>

<div class="order-num-wrap">
    <div class="order-num">订单号：${ticketOrderVO.orderNo}</div>
    <input type="hidden" value="${ticketOrderVO.orderNo}" id="orderNo">
    <input type="hidden" value="${ticketOrderVO.id}" id="orderId">
</div>

<div class="order-table">
    <table style="font-size: 12px;">
        <tbody><tr class="menu"><td class="movie">电影</td><td class="changci">场次</td><td class="seat">票数/座位</td><td class="money">金额小计</td><td class="phone">接收电子码的电话号码</td></tr>
        <tr class="info">
            <td class="movie-wrap">
                <img width="84" heigh="116" src="${ticketOrderVO.ticketMovieBO.moviePicture}">
                <ul>
                    <input id="movieId" value="${ticketOrderVO.ticketMovieBO.id}" hidden>
                    <li class="movie-name">${ticketOrderVO.ticketMovieBO.movieName}</li>
                    <li>版本： ${ticketOrderVO.ticketMovieBO.movieLanguage}</li>
                    <li>片长： ${ticketOrderVO.ticketMovieBO.movieLength}分钟</li>
                </ul>
            </td>
            <td class="cinema-wrap">
                <div class="cinema-name">${ticketOrderVO.ticketCinemaBO.cinemaName}</div>
                <div class="house">${ticketOrderVO.ticketSpecificScheduleBO.showroomName}</div>
                <input id="showTime" hidden value="${ticketOrderVO.ticketSpecificScheduleBo.movieShowTime}">
                <div class="time"></div>
            </td>
            <td class="seat-wrap">
                <div class="count">${ticketOrderVO.orderTickets}张</div>
                <input id="seatStr" value="${ticketOrderVO.orderSeat}" hidden>
            </td>
            <td class="money-wrap">
                <div class="money">
                    <div class="main M-main-price" data-money="104">￥${ticketOrderVO.orderPresentPrice}</div>
                    <div class="other"></div>
                </div>
            </td>
            <td class="phone-wrap">
                <div class="phone">${ticketOrderVO.orderMobile}</div>
                <div>1.短信可能会被手机软件拦截，请到该软件中查找短信</div>
                <div>2.若要修改手机号，需要您重新下单</div>
            </td>
        </tr>
        </tbody></table>
    <div class="price-info">
			<span class="curr-money">
			实付款:<span class="price M-price">￥${ticketOrderVO.orderPresentPrice}</span>
			</span>
    </div>
    <a href="javascript:void(0)" class="js-submit submit-button">
        确认订单，立即支付
    </a>
</div>
</div>

<script src="/static/js/order-pay.js"></script>