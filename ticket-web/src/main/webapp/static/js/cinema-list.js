$(function () {
    var city = window._cityId;

    var _timer = setInterval(function () {
        if(window._cityId) {
            city = window._cityId;
            searchCinema('', city);
            getArea(city);
            clearInterval(_timer);
        }
    }, 100);

    $('.nav-cinema').addClass('current');

    function getArea(city) {
        $.get('/city/get/area', {cityId: city}, function (result) {
            if (result && result.success) {
                var list = result.data;
                var html = '';

                for (var i = 0; i < list.length; i++) {
                    html += '<a href="javascript:void(0)" class="js-area" data-id="' + list[i].areaId + '">' + list[i].areaName + '</a>';
                }

                $('.select-tags').find('[data-id]').remove().end().append(html)
                    .find('a').eq(0).addClass('current');
            }
        });
    }

    $(document).on('click', '.js-area', function () {
        $('.select-tags .current').removeClass('current');
        $(this).addClass('current');
        var area = $('.select-tags .current').data('id');

        searchCinema($.trim( $('.search-input').val() ), city, area, null, true);
    });

    $('.js-search').on('click', function () {
        var area = $('.select-tags .current').data('id');

        searchCinema($.trim( $('.search-input').val() ), city, area, null, true);
    });

    $(document).on('mouseenter', '.js-cinema-hover', function () {
        $('.js-cinema-hover.current').removeClass('current');
        $(this).addClass('current');
    });
    $(document).on('click', '.js-select-city', function () {
        city = $(this).data('id');

        getArea(city);
        searchCinema('', city);
    });

    $('.js-more').on('click', function () {
        searchCinema($('.search-input').val(), city, $('.select-tags .current').data('id'), ($('.sortbar-detail li').length / 5).toFixed());
    });

    function searchCinema(key, city, area, curNo, empty) {
        $.get('/cinema/search', {
            cityId: city,
            areaId: area,
            curNo: +curNo || 1,
            pageSize: 5,
            param: key
        }, function (result) {
            if(result && result.success) {
                var html = template('cinemaTemplate', {
                    list: result.data
                });

                if(empty) {
                    $('.sortbar-detail').empty();
                }
                $('.sortbar-detail').empty().append(html);

                var len = $('.sortbar-detail li').length;

                $('span.count').text(len);

                if(len < 5) {
                    $('.sortbar-more').hide();
                }
            } else {
                $('.sortbar-more').hide();
            }
        });
    }
});