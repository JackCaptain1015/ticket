$(function () {
    $('.nav-index').addClass('current');

    var timer = setInterval(function () {
        if(window._cityId) {
            clearInterval(timer);

            getCinema(window._cityId);
        }
    }, 100);

    $(document).on('click', '.js-select-city', function () {
        getCinema($(this).data('id'));
    });
    
    function getCinema(city) {
        $.get('/cinema/search', {cityId: city, curNo: 1}, function (result) {
            var html = template('cinemaList', {
                list: result.data
            });

            $('.home-toplist-item .movie-cinemalist ul').html(html);
        });
    }
    
    $.get('/movie/showing', {pageSize: 5}, function (result) {
        var html = template('movieTemp', {
            movies: result.data
        });

        $('.show-tab').text('正在热映(' + result.data.length + ')');
        
        $('.tab-movie-list.show-list').append(html);
    });

    $.get('/movie/ready', {pageSize: 5}, function (result) {
        var html = template('movieTemp', {
            movies: result.data
        });

        $('.will-tab').text('即将上映(' + result.data.length + ')');

        $('.tab-movie-list.will-list').append(html);
    });

    $('.js-tab').click(function () {
        var $target = $( $(this).data('target') );

        $('.tab-movie-list').hide();
        $target.show();
        
        $('.js-tab.current').removeClass('current');
        $(this).addClass('current');
    });

    $(document).on('mouseenter', '.js-cinema', function () {
        $('.js-cinema.current').removeClass('current');
        $(this).addClass('current');
    });

});