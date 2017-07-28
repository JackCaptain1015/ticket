<#include "common/head.ftl">
<link rel="stylesheet" href="/static/css/cinema-detail.css">

<div class="infomation-wrapper" data-spm="w1">
    <div class="center-wrap">
        <input id="cinemaId" value="${ticketCinemaVO.id}" hidden>
        <h4 class="title">${ticketCinemaVO.cinemaName}</h4>
        <div class="info">
            <a data-type="image" class="float-layer-hook float-layer-wrapper">
                <img data-src="http://bdworkflow.oss-cn-hangzhou.aliyuncs.com/e2a01ef6-0933-429e-bfef-1bbb54456e15.jpg" src="http://bdworkflow.oss-cn-hangzhou.aliyuncs.com/e2a01ef6-0933-429e-bfef-1bbb54456e15.jpg">
            </a>
            <ul class="info-list">
                <li>详细地址：${ticketCinemaVO.cinemaAddress}</li>
                <li>联系电话：${ticketCinemaVO.cinemaMobile}</li>
            </ul>
        </div>
    </div>
</div>

<div class="center-wrap tab-wrapper">
    <ul class="tab-list M-tabs">
        <li id="seat" class="J_seat current">
            <a href="#seat" data-href="seat">选座购票</a>
        </li>
    </ul>
</div>

<div class="center-wrap M-seat" data-spm="w2">
    <div class="schedule-wrap J_scheduleWrap schedule-loaded" data-type="cinema_detail" data-ajax="/cinemaDetailSchedule.htm" data-param="showId=&amp;cinemaId=31251&amp;ts=1492002134721&amp;n_s=new">

        <div class="center-wrap w-seat-wrapper">
            <div class="filter-wrap">
                <ul class="filter-select">
                    <li>
                        <label>选择影片</label>
                        <div class="select-tags movie-list">
                        </div>
                    </li>
                    <li>
                        <label>选择时间</label>
                        <div class="select-tags date-list">
                        </div>
                    </li>
                </ul>
            </div>
            <!-- movie bar -->
            <div class="movie-wrapper M-movie movie-container">
                <script id="movieTemplate" type="text/template">
                <img class="movie-post" width="120" height="160" src="<%=t.moviePicture%>">
                <div class="movie-info-wrap">
                    <h4 class="info-title">
                        <a href="/movie/to/detail?movieId=<%=t.id%>" class="movie-name"><%=t.movieName%></a>
                    </h4>
                    <div class="right-info">
                        <a class="detail" href="/movie/to/detail?movieId=<%=t.id%>">查看影片详情&nbsp;&gt;</a>
                    </div>
                    <div class="movie-info">
                        <ul>
                            <li>导演：<%=t.movieDirector%></li>
                            <li>主演：<%=t.movieActor%></li>
                            <li>类型：<%=t.movieType%></li>
                            <li>制片国家/地区：<%=t.movieProduct%></li>
                            <li>语言：<%=t.movieLanguage%></li>
                        </ul>
                    </div>
                </div>
                </script>
            </div>
            <!-- Hall Tabel-->
            <div class="center-wrap" style="margin-bottom: 100px;">
                <table class="hall-table">
                    <thead>
                    <tr>
                        <th class="hall-time">放映时间</th>
                        <th class="hall-type">语言版本</th>
                        <th class="hall-name">放映厅</th>
                        <th class="hall-flow">座位情况</th>
                        <th class="hall-price">现价/影院价（元）</th>
                        <th class="hall-buy">选座购票</th>
                    </tr>
                    </thead>
                    <tbody id="seatList">

                    </tbody>
                </table>
                <div class="error-wrap" style="">囧 ~没有找到你需要的排期，你可以查看 <a href="/movie/to/search">其他影院</a>或者<a href="/movie/to/list">其他影片</a></div>
            </div>
        </div></div>
</div>
<script type="text/template" id="scheduleTemplate">
    <%for(var i in list) {%>
    <%var t = list[i];%>
    <tr>
        <td class="hall-time <%if( (i+1) % 2 == 0) {%>event<%}%>">
            <em class="bold"><%= getTime(t.movieShowTime) %></em>
            预计<%=getTime(t.movieEndTime)%>散场
        </td>
        <td class="hall-type">
            <%=t.movieLanguageVersion%>
        </td>
        <td class="hall-name">
            <%=t.showroomName%>
        </td>

        <td class="hall-flow">
            <div class="flowing-wrap flowing-loose">
                <label> 宽松  </label>
                <span class="flowing-vol"><i style="width:0.0%"></i></span>
                        <span class="flowing-view J_flowingView rendered" data-scheduleid="389694062">
								   <i class="icon-zoom js-view-seat"></i>
                            </span>
            </div>
        </td>
        <td class="hall-price" data-partcode="dingxinnew">
            <em class="now"><%=t.presentPrice%></em>
            <del class="old"><%=t.cinemaPrice%></del>
        </td>
        <td class="hall-seat">
            <a class="seat-btn" href="/schedule/index?movieId=<%=info.movieId%>&scheduleId=<%=t.id%>&cinemaId=<%=info.cinemaId%>">选座购票</a>
        </td>
    </tr>
    <%}%>
</script>
<#include "template/seat.ftl">
<#include "template/signin.ftl">
<script src="/static/js/cinema-detail.js"></script>