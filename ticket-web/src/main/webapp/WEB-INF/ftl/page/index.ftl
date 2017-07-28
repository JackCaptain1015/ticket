<#include "common/head.ftl">

<div class="center-wrap" data-spm="w2" data-spm-max-idx="23">
    <div class="tab-control tab-movie-tit">
        <a class="tab-control-item current show-tab js-tab " href="#" data-target=".show-list">正在热映</a>
        <a class="tab-control-item will-tab js-tab" href="#" data-target=".will-list">即将上映</a>
        <a class="more" href="/movie/to/list">查看全部&nbsp;&gt;</a>
    </div>
    <div class="tab-content">
        <!-- 正在热映 -->
        <div class="tab-movie-list show-list" style="display: block;"></div>



        <!-- 即将热映 -->
        <div class="tab-movie-list will-list">

        </div>

    </div>
</div>
<div class="process-wrap"></div>
<div class="center-wrap" style="margin-bottom: 40px;">
    <div class="home-toplist-item">
        <div class="movie-cinemalist title-wrap" data-spm="t2">
            <h5><span>热门影院排行</span><a href="/cinema/to/search" class="more">全部影院&nbsp;&gt;</a></h5>
            <ul class="cinemalist M-cinemalist">
            </ul>
        </div>

    </div>
</div>
<script id="cinemaList" type="text/template">
    <%for(var i = 0; i < list.length; i++) {%>
    <%var t = list[i];%>
    <li class="<% if(i == 0) {%>current<%}%> js-cinema">
        <i class="i-<%=i + 1%>"></i>
        <a href="" class="name"><%=t.cinemaName%></a>
        <span class="address"><%=t.cinemaAddress%></span>
                <span class="buynow">
				<a class="seat" href="/cinema/to/detail?cinemaId=<%=t.id%>">选座</a>
				 </span>
    </li>
    <%}%>
</script>


    <script id="movieTemp" type="text/template">
        <%var movie, i%>
        <%for(i = 0; i < movies.length; i++) {%>
        <% movie = movies[i] %>
        <div class="movie-card-wrap">
            <a href="/movie/to/detail?movieId=<%=movie.id%>" class="movie-card">
                <div class="movie-card-tag"><i class="t-203"></i></div>
                <div class="movie-card-poster">
                    <img src="<%=movie.moviePicture%>" width="160" height="224">
                </div>
                <div class="movie-card-name">
                    <span class="bt-l"><%=movie.movieName%></span>
                </div>
                <div class="movie-card-info">
                    <div class="movie-card-mask"></div>
                    <div class="movie-card-list">
                        <span>导演：<%=movie.movieDirector%></span>
                        <span>类型：<%=movie.movieType%></span>
                        <span>地区：<%=movie.movieProduct%></span>
                        <span>语言：<%=movie.movieLanguage%></span>
                        <span>片长：<%=movie.movieLength%>分钟</span>                        </div>
                </div>
            </a>
            <a href="/movie/to/detail?movieId=<%=movie.id%>" class="movie-card-buy" data-spm-anchor-id="a1z21.3046609.w2.5">选座购票</a>
        </div>
        <%}%>
    </script>
<#include "template/signin.ftl">
<script src="/static/js/index.js"></script>