<#include "common/head.ftl">
<#include "template/signin.ftl">

<link rel="stylesheet" href="/static/css/user-index.css">


<div id="J_bought_main" style="margin: 0 auto; width: 80%;min-height: 600px;">
    <div class="index-mod__root___3ZrD7" data-reactid=".0">
        <div class="tabs-mod__container___33DJv nav-mod__tabs___1D0ZI" data-reactid=".0.0">
            <div class="tabs-mod__main___3rZKX" data-reactid=".0.0.0">
                <div class="tabs-mod__tab___2xjiP tabs-mod__selected___2DmO6 js-to-all" data-reactid=".0.0.0.$all"><span
                        class="nav-mod__tab___1PnZ4" data-reactid=".0.0.0.$all.0"><span class="nav-mod__text___3O7jT"
                                                                                        data-reactid=".0.0.0.$all.0.0">所有订单</span><span
                        class="nav-mod__count___hc9IJ" data-reactid=".0.0.0.$all.0.1"></span></span><span
                        class="tabs-mod__sep___1sK_N" data-reactid=".0.0.0.$all.1">|</span></div>
                <div class="tabs-mod__tab___2xjiP js-to-pay" data-reactid=".0.0.0.$waitPay">
                    <span class="nav-mod__tab___1PnZ4"
                                                                                        data-reactid=".0.0.0.$waitPay.0"><span
                        class="nav-mod__text___3O7jT" data-reactid=".0.0.0.$waitPay.0.0">待付款</span><span
                        class="nav-mod__count___hc9IJ to-pay-count" data-reactid=".0.0.0.$waitPay.0.1"></span></span>
                </div>

            </div>
        </div>
        <div class="index-mod__ems-ad___3fP6L js-ems-ad" data-reactid=".0.1">
            <div data-reactid=".0.1.0">
                <div data-reactid=".0.1.0.0">

                    <span></span>

                </div>
            </div>
        </div>
        <table class="bought-table-mod__table___2POWd table-head-mod__table___17eFg" data-reactid=".0.4">
            <colgroup data-reactid=".0.4.0">
                <col class="bought-table-mod__col1___2dvLQ" data-reactid=".0.4.0.0">
                <col class="bought-table-mod__col2___1TYHO" data-reactid=".0.4.0.1">
                <col class="bought-table-mod__col3___3tiox" data-reactid=".0.4.0.2">
                <col class="bought-table-mod__col4___2DOBG" data-reactid=".0.4.0.3">
                <col class="bought-table-mod__col5___2dl3b" data-reactid=".0.4.0.4">
                <col class="bought-table-mod__col6___qlx1I" data-reactid=".0.4.0.5">
                <col class="bought-table-mod__col7___2H5pO" data-reactid=".0.4.0.6">
            </colgroup>
            <tbody data-reactid=".0.4.1">
            <tr data-reactid=".0.4.1.0">
                <th data-reactid=".0.4.1.0.0">宝贝</th>
                <th data-reactid=".0.4.1.0.1">单价</th>
                <th data-reactid=".0.4.1.0.2">数量</th>
                <th data-reactid=".0.4.1.0.3">商品操作</th>
                <th data-reactid=".0.4.1.0.4">实付款</th>
                <th data-reactid=".0.4.1.0.5"><span
                        class="field-select-mod__select___1XP55 trade-select table-head-mod__status___SBEwU rc-select rc-select-enabled"
                        data-reactid=".0.4.1.0.5.0"><span class="rc-select-selection rc-select-selection--single"
                                                          role="combobox" aria-autocomplete="list" aria-haspopup="true"
                                                          aria-expanded="false" tabindex="0"
                                                          data-reactid=".0.4.1.0.5.0.$selection"><span
                        class="rc-select-selection__rendered" data-reactid=".0.4.1.0.5.0.$selection.0"><span
                        data-reactid=".0.4.1.0.5.0.$selection.0.$value">交易状态</span></span></span></span>
                    </span>
                </th>
                <th data-reactid=".0.4.1.0.6">交易操作</th>
            </tr>
            </tbody>
        </table>
        <div class="index-mod__order-container___1ur4- js-order-container" data-reactid=".0.6:$order-3227642411804645">
            <div data-id="3227642411804645" class="bought-wrapper-mod__trade-order___2lrzV"
                 data-reactid=".0.6:$order-3227642411804645.$3227642411804645">
                <table class="order-table all bought-table-mod__table___2POWd bought-wrapper-mod__table___3xFFM"
                       data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0">
                    <colgroup data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0">
                        <col class="bought-table-mod__col1___2dvLQ"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.0">
                        <col class="bought-table-mod__col2___1TYHO"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.1">
                        <col class="bought-table-mod__col3___3tiox"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.2">
                        <col class="bought-table-mod__col4___2DOBG"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.3">
                        <col class="bought-table-mod__col5___2dl3b"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.4">
                        <col class="bought-table-mod__col6___qlx1I"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.5">
                        <col class="bought-table-mod__col7___2H5pO"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.6">
                    </colgroup>
                    <script type="text/template" id="orderTemplate">
                        <%var t;%>
                        <%for(var i in list) {%>
                        <% t = list[i];%>
                    <tbody class="bought-wrapper-mod__head___2vnqo <%if(t.orderStatus == 0) {%> to-pay<%}%> order-item">
                    <tr>
                        <td class="bought-wrapper-mod__head-info-cell___29cDO">
                            <label
                                class="bought-wrapper-mod__checkbox-label___3Va60">
                            <span class="bought-wrapper-mod__create-time___yNWVS"
                            ><%=t.time%></span></label><span><span
                            >订单号</span><span>: </span><span
                            ><%=t.orderNo%></span></span>
                        </td>
                        <td colspan="2"></td>
                        <td></td>
                        <td colspan="3">
                        </td>
                    </tr>
                    </tbody>
                    <tbody data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0" class="<%if(t.orderStatus == 0) {%> to-pay<%}%> order-item">
                    <tr data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0">
                        <td class="sol-mod__no-br___3DsOm"
                            data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0">
                            <div class="ml-mod__container___2yasl production-mod__production___3z1Mb suborder-mod__production___3WebF"
                                 data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0">
                                <div class="ml-mod__media___2UQOD" style="width:80px;"
                                     data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0.0">
                                    <a
                                        href="/movie/to/detail?movieId=<%=t.ticketMovieBO.id%>"
                                        class="production-mod__pic___2HeLR" target="_blank" rel="noopener noreferrer"
                                        data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0.0.0">
                                    <img
                                        src="<%=t.ticketMovieBO.moviePicture%>"
                                        data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0.0.0.0"><span
                                        data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0.0.0.1"> </span></a>
                                </div>
                                <div style="margin-left:90px;"
                                     data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0.1"><p
                                        data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0.1.0">
                                    <a href="/movie/to/detail?movieId=<%=t.ticketMovieBO.id%>"
                                       target="_blank" rel="noopener noreferrer"><span
                                            style="line-height:16px;">
                                        《<%=t.ticketMovieBO.movieName%>》<%=t.ticketCinemaBO.cinemaName%>电影票</span><span>
                                    </span></a>
                                    </p>
                                    <p>
                                        <span class="production-mod__sku-item___2r2NU"><span>影厅</span><span>：</span><span>
                                            <%=t.ticketSpecificScheduleBO.showroomName%>
                                        </span></span>
                                    </p>
                                    <p>
                                        <span class="production-mod__sku-item___2r2NU"><span>播放时间</span><span>：</span><span>
                                            <%=getDate(t.ticketSpecificScheduleBO.movieShowTime)%></span></span>
                                    </p>
                                    <p>
                                        <span class="production-mod__sku-item___2r2NU"><span>座位号</span><span>：</span><span>
                                            <%=t.orderSeat%></span></span>
                                    </p>
                                </div>
                            </div>
                        </td>
                        <td class="sol-mod__no-br___3DsOm"
                            data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$1">
                            <div class="price-mod__price___1BVLR"
                                 data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$1.0"><p
                                    data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$1.0.1">
                                <span data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$1.0.1.0">￥</span><span
                                    data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$1.0.1.1"><%=t.orderPresentPrice / t.orderTickets%></span>
                            </p></div>
                        </td>
                        <td class="sol-mod__no-br___3DsOm"
                            data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$2">
                            <div data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$2.0"><p
                                    data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$2.0.0">
                                <%=t.orderTickets%></p></div>
                        </td>
                        <td data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$3">
                            <div data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$3.0"></div>
                        </td>
                        <td class="" data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$4">
                                <div class="price-mod__price___1BVLR"
                                     data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$4.0.0">
                                    <p>
                                    <strong><span>￥</span><span><%=t.orderPresentPrice%></span></strong>
                                </p></div>
                        </td>
                        <td class="" data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$5">
                            <div data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$5.0"><p
                                    style="margin-bottom:3px;"
                                    data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$5.0.0">
                                <span class="text-mod__link___2QaZ1"
                                      data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$5.0.0.0">
                                <%if(t.orderStatus == 3) {%>
                            超时未付款，订单已关闭
                            <%} else if(t.orderStatus == 0) {%>
                                    等待买家付款
                                     <%} else if(t.orderStatus == 1) {%>
                            订单已完成
                            <%}%>
                                </span>
                            </p>
                            </div>
                        </td>
                        <td class="" data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$6">
                            <div data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$6.0"><p
                                    style="margin-bottom:3px;">
                            <%if(t.orderStatus == 0) {%>
                            <a
                                    href="/order/index?orderId=<%=t.id%>"
                                    class="button-mod__button___2GAem button-mod__primary___PMxc3 button-mod__button___3fTIA"
                                    target="_blank" id="pay"
                            >立即付款</a>

                            <%}%>
                            </p>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                        <%}%>
                    </script>
                </table>
                <table style="display: none" class="order-table wait-pay bought-table-mod__table___2POWd bought-wrapper-mod__table___3xFFM"
                       data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0">
                    <colgroup data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0">
                        <col class="bought-table-mod__col1___2dvLQ"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.0">
                        <col class="bought-table-mod__col2___1TYHO"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.1">
                        <col class="bought-table-mod__col3___3tiox"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.2">
                        <col class="bought-table-mod__col4___2DOBG"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.3">
                        <col class="bought-table-mod__col5___2dl3b"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.4">
                        <col class="bought-table-mod__col6___qlx1I"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.5">
                        <col class="bought-table-mod__col7___2H5pO"
                             data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.0.6">
                    </colgroup>
                    <script type="text/template" id="orderTemplate">
                        <%var t;%>
                        <%for(var i in list) {%>
                        <% t = list[i];%>
                        <tbody class="bought-wrapper-mod__head___2vnqo <%if(t.orderStatus == 0) {%> to-pay<%}%> order-item">
                        <tr>
                            <td class="bought-wrapper-mod__head-info-cell___29cDO">
                                <label
                                        class="bought-wrapper-mod__checkbox-label___3Va60">
                            <span class="bought-wrapper-mod__create-time___yNWVS"
                            ><%=t.time%></span></label><span><span
                            >订单号</span><span>: </span><span
                            ><%=t.orderNo%></span></span>
                            </td>
                            <td colspan="2"></td>
                            <td></td>
                            <td colspan="3">
                            </td>
                        </tr>
                        </tbody>
                        <tbody data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0" class="<%if(t.orderStatus == 0) {%> to-pay<%}%> order-item">
                        <tr data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0">
                            <td class="sol-mod__no-br___3DsOm"
                                data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0">
                                <div class="ml-mod__container___2yasl production-mod__production___3z1Mb suborder-mod__production___3WebF"
                                     data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0">
                                    <div class="ml-mod__media___2UQOD" style="width:80px;"
                                         data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0.0">
                                        <a
                                                href="/movie/to/detail?movieId=<%=t.ticketMovieBO.id%>"
                                                class="production-mod__pic___2HeLR" target="_blank" rel="noopener noreferrer"
                                                data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0.0.0">
                                            <img
                                                    src="<%=t.ticketMovieBO.moviePicture%>"
                                                    data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0.0.0.0"><span
                                                data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0.0.0.1"> </span></a>
                                    </div>
                                    <div style="margin-left:90px;"
                                         data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0.1"><p
                                            data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$0.0.1.0">
                                        <a href="/movie/to/detail?movieId=<%=t.ticketMovieBO.id%>"
                                           target="_blank" rel="noopener noreferrer"><span
                                                style="line-height:16px;">
                                        《<%=t.ticketMovieBO.movieName%>》<%=t.ticketCinemaBO.cinemaName%>电影票</span><span>
                                    </span></a>
                                    </p>
                                        <p>
                                        <span class="production-mod__sku-item___2r2NU"><span>影厅</span><span>：</span><span>
                                            <%=t.ticketSpecificScheduleBO.showroomName%>
                                        </span></span>
                                        </p>
                                        <p>
                                        <span class="production-mod__sku-item___2r2NU"><span>播放时间</span><span>：</span><span>
                                            <%=getDate(t.ticketSpecificScheduleBO.movieShowTime)%></span></span>
                                        </p>
                                        <p>
                                        <span class="production-mod__sku-item___2r2NU"><span>座位号</span><span>：</span><span>
                                            <%=t.orderSeat%></span></span>
                                        </p>
                                    </div>
                                </div>
                            </td>
                            <td class="sol-mod__no-br___3DsOm"
                                data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$1">
                                <div class="price-mod__price___1BVLR"
                                     data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$1.0"><p
                                        data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$1.0.1">
                                    <span data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$1.0.1.0">￥</span><span
                                        data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$1.0.1.1"><%=t.orderPresentPrice / t.orderTickets%></span>
                                </p></div>
                            </td>
                            <td class="sol-mod__no-br___3DsOm"
                                data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$2">
                                <div data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$2.0"><p
                                        data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$2.0.0">
                                    <%=t.orderTickets%></p></div>
                            </td>
                            <td data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$3">
                                <div data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$3.0"></div>
                            </td>
                            <td class="" data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$4">
                                <div class="price-mod__price___1BVLR"
                                     data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$4.0.0">
                                    <p>
                                        <strong><span>￥</span><span><%=t.orderPresentPrice%></span></strong>
                                    </p></div>
                            </td>
                            <td class="" data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$5">
                                <div data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$5.0"><p
                                        style="margin-bottom:3px;"
                                        data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$5.0.0">
                                <span class="text-mod__link___2QaZ1"
                                      data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$5.0.0.0">
                                <%if(t.orderStatus == 3) {%>
                            超时未付款，订单已关闭
                            <%} else if(t.orderStatus == 0) {%>
                                    等待买家付款
                                     <%} else if(t.orderStatus == 1) {%>
                            订单已完成
                            <%}%>
                                </span>
                                </p>
                                </div>
                            </td>
                            <td class="" data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$6">
                                <div data-reactid=".0.6:$order-3227642411804645.$3227642411804645.0.1:1:0.$0.$6.0"><p
                                        style="margin-bottom:3px;">
                                    <%if(t.orderStatus == 0) {%>
                                    <a
                                            href="/order/index?orderId=<%=t.id%>"
                                            class="button-mod__button___2GAem button-mod__primary___PMxc3 button-mod__button___3fTIA"
                                            target="_blank" id="pay"
                                    >立即付款</a>

                                    <%}%>
                                </p>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                        <%}%>
                    </script>
                </table>


            </div>
        </div>
        <div class="row-mod__row___29QMl js-actions-row-top" data-reactid=".0.5">
            <div style="float:left;width:;" data-reactid=".0.5.0">
                <div class="batch-mod__container___sK68S" data-reactid=".0.5.0.1"></div>
            </div>
            <div style="float:right;margin-bottom: 20px;" data-reactid=".0.5.1">
                <div class="simple-pagination-mod__container___2e0_5 all-pay" data-reactid=".0.5.1.0">
                    <button class="js-next button-mod__button___2GAem button-mod__default___1cG9O button-mod__small___34MuZ"
                            data-reactid=".0.5.1.0.1">下一页
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/static/js/user-index.js"></script>
<#include "common/foot.ftl">