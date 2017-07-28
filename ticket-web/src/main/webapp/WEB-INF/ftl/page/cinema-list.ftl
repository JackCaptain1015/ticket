<#include "common/head.ftl">
<link href="/static/css/cinema-list.css" rel="stylesheet">

<div class="center-wrap movie-path" data-spm="w1">
    <div class="path"><a href="/">首页</a> &gt; <a href="/cinema/to/search">影院</a> &gt; <span class="city-name">杭州</span></div>
    <div class="steps"><span>3步轻松购票:1.选座购票/买券</span><i></i><span>2.收电子码</span><i></i><span>3.影院取票</span></div>
</div>

<div class="cinema-wrap center-wrap">
    <div class="filter-wrap" data-spm="w2" style="width: 100%;">
        <ul class="filter-select">
            <li>
                <label>选择区域</label>
                <div class="select-tags">
                    <a class="current" href="/cinema/to/search" data-param="city=330100">全部区域</a>
                </div>
            <li>
                <label>搜索</label>
                <div class="search-wrap J_searchWrap">
                    <input placeholder="请输入影院名称/关键字" class="search-input" type="text" value=""><a href="javascript:;"  class="search-btn js-search"><i class="icon-search"></i>查询</a>
                </div>
            </li>
        </ul>
        <div class="list-sort M-sort">
            当前条件下共有<span class="count">0</span>家影院
        </div>
        <ul class="sortbar-detail J_cinemaList">

        </ul>

        <div class="sortbar-more J_cinemaMore">
            <div>
                <a href="javascript:;" class="js-more">点击显示更多</a>
            </div>
        </div>

    </div>

</div>
<script id="cinemaTemplate" type="text/template">
<%if(list && list.length) {%>
<%for(var i = 0; i < list.length; i++) {%>
<%var t = list[i];%>
<li class="<%if(i==0) {%>current<%}%> js-cinema-hover">
    <div class="detail-right">
        <div class="right-buy ">
            <a class="js-pick-seat" href="/cinema/to/detail?cinemaId=<%=t.id%>">选座</a>
        </div>
    </div>
    <a href="javascript:void(0)" class="detail-left pictures">
        <span><img src="https://img.alicdn.com/bao/uploaded/i4/TB1xkraIFXXXXb.aXXXXXXXXXXX_.jpg_120x120.jpg"></span>

    </a>
    <div class="detail-middle">
        <div class="middle-hd">
            <h4><a href="/cinema/to/detail?cinemaId=<%=t.id%>"><%=t.cinemaName%></a></h4>
        </div>
        <div class="middle-p">
            <div class="middle-p-list"><i>地址：</i><span class="limit-address"><%=t.cinemaAddress%></span></div>
            <div class="middle-p-list"><i>电话：</i><%=t.cinemaMobile%></div>
        </div>
    </div>
</li>
<%}%>
    <%}%>
</script>
<#include "template/signin.ftl">
<script src="/static/js/cinema-list.js"></script>