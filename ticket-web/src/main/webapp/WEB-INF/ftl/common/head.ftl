<link href="/static/css/index.css" rel="stylesheet">
<link href="/static/css/index.less.css" rel="stylesheet">
<script src="/static/plugin/jQuery-2.1.4.min.js"></script>
<script src="/static/plugin/layer/layer.js"></script>
<script src="/static/plugin/artTemplate/artTemplate.js"></script>
<script src="/static/js/commons/city.js"></script>

<div class="head-wrap" data-spm="header" data-spm-max-idx="6">
    <div class="head-content center-wrap">
        <h1 class="logo">
            <a target="_top" href="/" style="background: url('/static/img/commons/logo.png') no-repeat; background-size: 150px 52px; height: 52px; margin-top: 10px;"></a>
        </h1>
        <div class="cityWrap M-cityWrap js-city">
            <a id="cityName" class="cityName" href="javascript:" data-spm-anchor-id="a1z21.3046609.header.2"><span class="name" data-id="330100">杭州</span><s class="tri"></s></a>
            <input id="H_CityId" value="310100" type="hidden">
            <input id="H_CityName" value="上海" type="hidden">
            <div class="cityList ">
                <div class="cityBox" data-spm="city">
                    <div class="M-cityList scrollStyle" style="height: initial;">
                        <ul style=""></ul>
                    </div>
                </div>
                <div class="cityShade"></div>
            </div>
        </div>
        <div class="nav-wrap">
            <ul class="nav">
                <li class="J_NavItem nav-index">
                    <a href="/" target="_top" data-spm-anchor-id="a1z21.3046609.header.3">首页</a>
                </li>
                <li class="J_NavItem nav-movie">
                    <a href="/movie/to/list" target="_top" data-type="movie">影片</a>
                </li>
                <li class="J_NavItem nav-cinema">
                    <a href="/cinema/to/search" target="_top" data-type="cinema">影院</a>
                </li>

            </ul>
        </div>
        <div class="entrance-wrap">
            <ul class="entrance">

                <li class="entrance-item" style="position: relative;">

                    <a class="service js-signin" href="javascript:">登录</a>

                    <a class="js-signout sign-out" href="javascript:void(0)" style="position: absolute;top: 20px; right: 0; background: initial;">退出</a>
                </li>
            </ul>
        </div>
    </div>
</div>
<style>
    .sign-out {
        display: none !important;
    }

    .entrance-item.signed:hover .sign-out {
        display: block !important;
        color: red !important;
    }
</style>