<link href="/static/css/order.css" rel="stylesheet">
<#include "common/head.ftl">
<#include "template/seat.ftl">

<div class="center-wrap">
    <div class="seat-step">
        <ul>
            <li class="down"><s></s>1.选择影片，场次</li>
            <li class="current">2.选座，填手机号</li>
            <li class="future">3.确认订单，支付</li>
            <li class="future">4.支付成功，影院取票观影</li>
        </ul>
    </div>
</div>

<div class="center-wrap seat-wrap">
    <div class="seat-left">
        <div class="clearfix">
            <div class="J_coulmn">
                <ul>
                </ul>
            </div>
            <div class="seatContainer" style="height: 465.867px;">
                <input hidden value="${ticketSpecificScheduleVO.id}" id="scheduleId">
                <div class="seatTitle">
                    <h2>
                    ${ticketSpecificScheduleVO.ticketCinemaVO.cinemaName} ${ticketSpecificScheduleVO.showroomName}厅 银幕
                    </h2>
                    <s></s>
                </div>
                <div class="seatChoose J_Boundry">
                    <div class="seat_zuo J_seat_zuo" id="seatView">
                    </div>
                </div>
            </div>
        </div>
        <div class="seatChooseInfor">
            <ul class="clearfix">
                <li><span class="hasSeat"></span>可选座位</li>
                <li><span class="sellSeat"></span>已选座位</li>
                <li><span class="checkSeat"></span>不可选座位</li>
                <li><span class="loveSeat"></span>情侣座</li>
            </ul>
            <p> 本影厅共有座位<span id="totalSeat"></span>个，当前已售<span class="J_sell" id="pickedSeat">0</span>个</p>
        </div>


    </div>
    <div class="seat-right">
            <div class="seatMovie clearfix">
                    <div class="picBox">
                    <a href="/movie/to/detail?movieId=${ticketSpecificScheduleVO.ticketMovieVO.id}">
                    <img src="${ticketSpecificScheduleVO.ticketMovieVO.moviePicture}"></a>
                    </div>
                <ul>
                    <li><h3><movie.movieName</h3></li>
                    <li>版本： ${ticketSpecificScheduleVO.ticketMovieVO.movieLanguage} </li>
                    <li>片长： ${ticketSpecificScheduleVO.ticketMovieVO.movieLength}分钟 </li>
                </ul>
            </div>
        <div class="seatContent">
            <ul>
                <li>
                    <label>影院：</label>
                    <strong>${ticketSpecificScheduleVO.ticketCinemaVO.cinemaName}</strong>
                </li>
                <li>
                    <label>影厅：</label>
                    <strong>${ticketSpecificScheduleVO.showroomName}</strong>
                </li>
                <li>
                    <label>场次：</label>
                    <a href="javascript:" class="changPlace J_show"><s></s>更换场次</a>
                    <em id="timeStr"><input hidden value="${ticketSpecificScheduleVO.movieShowTimeMill}" id="time"></em>
                </li>
                <li class="line">
                    <label>座位:</label>
                    <span class="picked-seat-list"></span>
                    <span class="No_set" style="">还未选择座位</span>
                    <div class="J_pewGroup"></div>
                    <p class="J_seatNum_tips" style="">点击<em>左侧座位图</em>选择座位，再次点击取消</p>
                    <p class="J_seatNum_text" style="display: none;">已选择<em>0</em>个座位，再次点击座位可以取消</p>
                </li>
                <li>
                    <label>原价：</label>
                    <span class="J_originPrice originPrice" data-originprice="2300">¥${ticketSpecificScheduleVO.presentPrice} x <i class="seat-count">0</i></span>
                    <input hidden id="price" value="${ticketSpecificScheduleVO.presentPrice}">
                    <span class="offer J_originOffer"></span>
                </li>

                <li class="total">
                    <label>总计:</label>
                    <div>
                        ¥
                        <span class="J_price total-price">
							0
						</span>
                    </div>
                </li>
            </ul>
        </div>
        <div class="seatForm">
            <input type="hidden" id="scheduleId" value="389694066" class="J_scheduleId">
            <input type="hidden" id="maxCanBuy" class="J_seatNum" value="5">
            <input type="hidden" id="seatInfo" class="J_seatNum">
            <input name="_tb_token_" type="hidden" value="ff11316175b3b">
            <ul>
                <li>接收电子码的手机号(11位)</li>
                <li class="telphone">
                    <label class="placehold J_overTxtLabel" style="display: none;">请输入接收电子码的手机号</label>
                    <input type="text" class="txt J_tel js-code-phone" value="" maxlength="11">
                    <div class="telphone_show J-telphone_show" style="display: none;"></div>
                </li>
                <li>
                    <a href="javascript:" class="sub J_bt js-confirm">确认信息，下单</a>
                </li>
            </ul>
        </div>

    </div>
</div>

<div class="center-wrap seatFoot">
    <div class="seatTips">
        <h4>特别提示：</h4>
        <ul>
            <li>1、下单前请仔细核对影片、影院、场次、手机号等信息。</li>
            <li>2、下单后请于15分钟内完成支付，超时系统将不保留座位。</li>
            <li>3、每笔订单最多可以选择5个座位，请选择连续的座位。</li>
            <li>4、电影票售出后暂不支持退换 。</li>
        </ul>
    </div>
    <!--卖家信息-->
    <div class="seatInfor">
        <h4>代理商信息：</h4>
        <p>鼎新NEW</p>
        <p>客服电话： 0571-88157838 </p>
    </div>
</div>
<#include "template/signin.ftl">
<script src="/static/js/order.js"></script>