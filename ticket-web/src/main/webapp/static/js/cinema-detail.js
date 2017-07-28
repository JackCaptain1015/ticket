/**
 * Created by q68602276 on 2017/04/12.
 */
$(function () {
    var _timer;
    var SEAT_CONFIG = {
        width: 11,
        height: 8
    };

    template.helper('getTime', function (t) {
        var ret = new Date(t);

        return ret.getHours() + ':' + ret.getMinutes();
    });

    $('.nav-cinema').addClass('current');

    getMovie($('#cinemaId').val());

    $(document)
        .on('mouseenter', '.js-view-seat', function () {
            $(this).parent().find('.view-wrap').show();
            clearTimeout(_timer);
        })
        .on('click', '.js-movie', function () {
            $('.js-movie.current').removeClass('current');
            $(this).addClass('current');
            setMovie($(this).data('id'));
            getTimeline();
        })
        .on('mouseleave', '.js-view-seat', function () {
            var that = this;
            _timer = setTimeout(function () {
                $(that).parent().find('.view-wrap').hide();
            }, 1000);
        })
        .on('click', '.js-time', function () {
            $('.js-time.current').removeClass('current');
            $(this).addClass('current');
            getScheduleList();
        })
    
    function getMovie(id) {
        $.get('/movie/cinema/rel/cinema', {
            cinemaId: id
        }, function (result) {
            if(result && result.data && result.data.length) {
                var t = result.data, html = '';

                for(var i in t) {
                    html += '<a class="js-movie ' + (i == 0 ? 'current' : '') + '" data-id="' + t[i].movieId + '">' + t[i].movieName + '</a>';
                }

                setMovie(t[0].movieId);
                $('.movie-list').html(html);
                getTimeline();
            }
        })
    }

    function getTimeline() {
        $.get('/movie/cinema/rel/schedule/day', {
            cinemaId: $('#cinemaId').val(),
            movieId: $('.js-movie.current').data('id')
        }, function (result) {
            var html = '', t;
            if(result && result.success && result.data && result.data.length) {
                for (var i = 0; i < result.data.length && i < 5; i++) {
                    t = result.data[i];

                    html += '<a class="js-time" href="javascript:void(0)" data-day="'
                        + t.replace(/[年日月]/g, '-').slice(0, -1) + '">' + t.slice(5) + '(' + (i == 0 ? '今天' : getDay(t)) + ')</a>';
                }

                $('.date-list').html(html);
                $('.js-time').eq(0).addClass('current');
                getScheduleList();
            }
        })
    }

    function setMovie(id) {
        $.get('/movie/detail', {
            movieId: id
        }, function (result) {
            if(result && result.data) {
                var html = template('movieTemplate', {
                    t: result.data
                });

                $('.movie-container').html(html);
            }
        })
    }

    function getScheduleList() {
        $.get('/schedule/list', {
            movieId: $('.js-movie.current').data('id'),
            cinemaId: $('#cinemaId').val(),
            day: $('.js-time.current').data('day')
        }, function (result) {
            if(result && result.data && result.data.length) {
                var html  = template('scheduleTemplate', {
                    list: result.data,
                    info: {
                        movieId: $('#movieId').val(),
                        cinemaId: $('.js-cinema.current').data('id')
                    }
                });

                $('#seatList').html(html);
                getSeat(result.data && result.data.length && result.data[0].id, 0);

                $('.error-wrap').hide();
            } else {
                $('#seatList').empty();
                $('.error-wrap').show();
            }
        });
    }

    function getSeat(id, index) {
        if(!id) {
            return;
        }
        $.get('/schedule/detail', {
            schedualId: id
        }, function (result) {
            if(result && result.data && result.data.seatList
                && result.data.seatList.length) {
                var seatList = [
                    window.convertSeatData(result.data.seatList),
                    window.convertSeatData(result.data.existSeatList)
                ];
                var per = getCount(seatList[1]) / getCount(seatList[0]);
                var html = template('seatTemplate', {
                    config: SEAT_CONFIG,
                    allSeat: seatList[0],
                    pickedSeat: seatList[1],
                    width: window.getSeatViewWidth(SEAT_CONFIG.width, seatList[0])
                });

                $('#seatList tr').eq(index).find('.rendered').append(html)
                    .end().end().find('.flowing-vol i').css('width',  per ? per * 100 + '%' : 0);
            }
        });
    }

    function getCount(list) {
        var count = 0;
        for(var i in list) {
            for(var j in list[i]) {
                count ++;
            }
        }
        return count;
    }

    function getDay(time) {

        var t = new Date(time.replace(/[年日月]/g, '/'));
        var ret;
        t = t.getDay();

        switch(t) {
            case 1:
                ret = '一';
                break;
            case 2:
                ret = '二';
                break;
            case 3:
                ret = '三';
                break;
            case 4:
                ret = '四';
                break;
            case 5:
                ret = '五';
                break;
            case 6:
                ret = '六';
                break;
            case 0:
                ret ='日';
                break;
        }
        return '周' + ret;
    }
});
