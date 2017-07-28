$(function () {
    $('.nav-movie').addClass('current');

    $.get('/movie/showing', {pageSize: 0}, function (result) {
        var html = template('movieTemp', {
            movies: result.data
        });

        $('.show-tab').text('正在热映(' + result.data.length + ')');

        $('.tab-movie-list.show-list').append(html);
    });

    $.get('/movie/ready', {pageSize: 0},  function (result) {
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
    })
});