
<link href="/static/css/movie-detail.css" rel="stylesheet">
<#include "common/head.ftl">
<#include "template/seat.ftl">
<div class="detail-wrap J_detailWrap" data-spm="w1">
    <div class="detail-bg J_detailBg" data-bg="https://img.alicdn.com/bao/uploaded/i3/TB1MeUCPVXXXXXNXXXXXXXXXXXX_.jpg_60x60.jpg" style="opacity: 0.4;"><img src="https://img.alicdn.com/bao/uploaded/i3/TB1MeUCPVXXXXXNXXXXXXXXXXXX_.jpg_60x60.jpg"></div>
    <div class="detail-movies J_moviesWindow">
        <div class="center-wrap">
            <a href="javascript:;" class="scroll-btn"></a>
            <a href="javascript:;" class="scroll-btn scroll-btn-right"></a>
            <div class="movies-scroll">
                <ul class="J_moviesList">
                    <li>
                        <a href="showDetail.htm?showId=163989&amp;source=current&amp;n_s=new">
                            <img src="https://img.alicdn.com/bao/uploaded/i3/TB1MeUCPVXXXXXNXXXXXXXXXXXX_.jpg_300x300.jpg" alt="速度与激情8">
                            <span><em class="name">速度与激情8</em><em class="score">9.2</em></span>
                        </a>
                    </li>
                    <li>
                        <a href="showDetail.htm?showId=159639&amp;source=current&amp;n_s=new">
                            <img src="https://img.alicdn.com/bao/uploaded/i4/TB1uKWIQpXXXXaLXXXXXXXXXXXX_.jpg_300x300.jpg" alt="攻壳机动队">
                            <span><em class="name">攻壳机动队</em><em class="score">7.7</em></span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="center-wrap">
        <a class="movies-open J_moviesOpen" href="javascript:;">切换影片</a>
    </div>
    <div class="detail-cont">
        <div class="center-wrap">
            <h3 class="cont-title">${ticketMovieVO.movieName}</h3>
            <input id="movieId" value="${ticketMovieVO.id}" hidden>
            <div class="cont-pic">
                <img with="230" heigh="300" src="${ticketMovieVO.moviePicture}" alt="">
            </div>
            <ul class="cont-info">
                <li>导演：${ticketMovieVO.movieDirector}</li>
                <li>主演：${ticketMovieVO.movieActor}</li>
                <li>类型：${ticketMovieVO.movieType}</li>
                <li>制片国家/地区：${ticketMovieVO.movieProduct}</li>
                <li>语言：${ticketMovieVO.movieLanguage}</li>
                <li>片长：${ticketMovieVO.movieLength}分钟</li>
                <li class="J_shrink shrink">剧情介绍：${ticketMovieVO.movieDesc}</li>
            </ul>
        </div>
    </div>
</div>

<div class="title-wrap">
    <div class="center-wrap">
        <h3>选座购票</h3>
    </div>
</div>

<div class="schedule-wrap J_scheduleWrap schedule-loaded" data-spm="w2" data-ajax="/showDetailSchedule.htm" data-param="showId=155476&amp;ts=1492000261189&amp;n_s=new">
    <!-- Filter -->
    <div class="filter-wrap">
        <div class="center-wrap">
            <ul class="filter-select">
                <li>
                    <label>选择区域</label>
                    <div class="select-tags area-list">
                        <a class="current js-area" href="javascript:;" data-param="showId=155476&amp;n_s=new">全部区域</a>

                    </div>
                </li>
                <li>
                    <label>选择影城</label>
                    <div class="select-tags cinema-list">
                    </div>
                </li>
                <li>
                    <label>选择时间</label>
                    <div class="select-tags date-list">
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <!-- Cinema bar -->
    <div class="center-wrap cinemabar-wrap">
    </div>
<script type="text/template" id="cinemaTitle">

    <h4><%=t.name%></h4>
    地址：<%=t.address%> <span style="margin-left: 20px;">电话：<%=t.mobile%></span>
    <a class="more" href="/cinema/to/detail?cinemaId=<%=t.id%>">查看影院详情&nbsp;&gt;</a>
</script>
    <!-- Hall Tabel -->
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
        <div class="error-wrap" style="">囧 ~没有找到你需要的排期，你可以查看 <a href="/cinema/to/search">其他影院</a>或者<a href="/movie/to/list">其他影片</a></div>
    </div>

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
<#include "template/signin.ftl">
<script src="/static/js/movie-detail.js"></script>