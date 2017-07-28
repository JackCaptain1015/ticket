/**
 * Created by q68602276 on 2017/04/12.
 */
$(function () {
    var _timer;
    var SEAT_CONFIG = {
        width: 11,
        height: 8
    };

    $('.nav-movie').addClass('current');

    template.helper('getTime', function (t) {
        var ret = new Date(t);

        return ret.getHours() + ':' + ret.getMinutes();
    });

    var timer = setInterval(function () {
        if(window._cityId != null) {
            getArea(window._cityId);
            clearInterval(timer)
        }
    });

    function getArea(city) {
        $.get('/city/get/area', {cityId: city}, function (result) {
            if (result && result.success && result.data.length) {
                var list = result.data;
                var html = '';

                for (var i = 0; i < list.length; i++) {
                    html += '<a href="javascript:void(0)" class="js-area" data-id="' + list[i].areaId + '">' + list[i].areaName + '</a>';
                }

                $('.select-tags.area-list').append(html);
                getCinema(city, true);
            }
        });
    }

    $(document).on('click', '.js-area', function () {
        $('.select-tags .js-area.current').removeClass('current');
        $(this).addClass('current');
        var area = $('.select-tags .js-area.current').data('id');

        getCinema(area || window._cityId, !area);
    })
        .on('click', '.js-cinema', function () {
            $('.current.js-cinema').removeClass('current');
            $(this).addClass('current');
            getTimeline();
        });

    $('.js-city').hover(function () {
        $(this).addClass('cityWrap_hover');
    }, function () {
        $(this).removeClass('cityWrap_hover');
    });

    $(document).on('mouseenter', '.js-cinema-hover', function () {
        $('.js-cinema-hover.current').removeClass('current');
        $(this).addClass('current');
    })
        .on('click', '.js-time', function () {
            $('.js-time.current').removeClass('current');
            $(this).addClass('current');
            getScheduleList();
        })
        .on('mouseenter', '.js-view-seat', function () {
            $(this).parent().find('.view-wrap').show();
            clearTimeout(_timer);
        })
        .on('mouseleave', '.js-view-seat', function () {
            var that = this;
            _timer = setTimeout(function () {
                $(that).parent().find('.view-wrap').hide();
            }, 1000);
        })
        .on('mouseenter', '.view-box', function () {
            clearTimeout(_timer);
        })
        .on('mouseleave', '.view-box', function () {
            $(this).parent().hide();
        })

    function getCinema(id, isCity) {
        if(isCity) {
            $.get('/cinema/city', {
                cityId: id
            }, function (result) {
                if(result && result.success && result.data.length) {
                    setCinema(result.data);
                } else {
                    $('.cinema-list').html('<div style="margin-left: 22px">无</div>');
                    $('.date-list').html('<div style="margin-left: 22px">无</div>')
                }
            });
        } else

        $.get('/cinema/area', {
            areaId: id || ''
        }, function (result) {
            if(result && result.success) {
                if(result && result.success && result.data.length) {
                    setCinema(result.data);
                } else {
                    $('.cinema-list').html('<div style="margin-left: 22px">无</div>');
                    $('.date-list').html('<div style="margin-left: 22px">无</div>')
                }
            }
        });
    }

    function setCinema(data) {
        var t, html = '';
        for(var i in data) {
            t = data[i];
            html += '<a href="javascript:void(0)" class="js-cinema ' + (i == 0 ? 'current' : '') + '" data-id="' + t.id +
                '" data-address="' + t.cinemaAddress + '" data-mobile="' + t.cinemaMobile + '" data-name="' + t.cinemaName
                +'">' + t.cinemaName + '</a>';
        }

        $('.cinema-list').html(html);
        $('.cinemabar-wrap').html(template('cinemaTitle', {
            t: $('.js-cinema.current').data()
        }));

        getTimeline();
    }

    function getTimeline() {
        $.get('/movie/cinema/rel/schedule/day', {
            cinemaId: $('.js-cinema.current').data('id'),
            movieId: $('#movieId').val()
        }, function (result) {
            var html = '', t;
            if(result && result.success) {
                for(var i = 0; i < result.data.length && i < 5; i++) {
                    t = result.data[i];

                    html += '<a class="js-time" href="javascript:void(0)" data-day="'
                        + t.replace(/[年日月]/g, '-').slice(0, -1) + '">' + t.slice(5) + '(' + (i == 0 ? '今天' : getDay(t)) + ')</a>';
                }

                $('.date-list').html(html);

                if(!i) {
                    var t = new Date();
                    t = t.getFullYear() + '-' + (t.getMonth() + 1) + '-' + t.getDate();
                    $('.date-list').parent().remove();

                    getScheduleList(t);
                } else {
                    $('.js-time').eq(0).addClass('current');
                    getScheduleList();
                }
            }
        })
    }
    function getScheduleList(time) {
        $.get('/schedule/list', {
            movieId: $('#movieId').val(),
            cinemaId: $('.js-cinema.current').data('id'),
            day: time || $('.js-time.current').data('day')
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
                $('.error-wrap').hide();
                getSeat(result.data && result.data.length && result.data[0].id, 0);
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