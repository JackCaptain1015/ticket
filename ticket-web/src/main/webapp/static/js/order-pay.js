$(function () {
    $('.cinema-wrap .time').text( getMovieShowTime($('#showTime').val()) );

    var seats = $('#seatStr').val().replace(/\(/g, '')
            .replace(/\)/g, '座').replace(/,/g, '排').split('-');
    var t = '';
    seats.forEach(function (e) {
        t += '<div class="seat">' + e + '</div>';
    });

    $('.js-submit').on('click', function () {
        $.post('/order/pay', {
            orderId: $('#orderId').val()
        }, function (result) {
            if(result && result.data) {
                layer.msg('支付成功', function () {
                    location.href = '/user/index'
                });
            }
        })
    });

    $('.seat-wrap').append(t);

    $.get('/order/valid', {
        orderNo: $('#orderNo').val()
    }, function (result) {
        var t = result.data ? result.data - Date.now() : false;

        if(!t || t < 0) {
            orderFail();
        } else {
            timeCount((t / 1000).toFixed(), function (obj) {
                if(obj.min ==0 && obj ==0 || obj.min < 0) {
                    orderFail();
                    return;
                }
                $('.J_time').show();
                $('.scd-digit-m').text(obj.min > 10 ? obj.min : '0' + obj.min);
                $('.scd-digit-s').text(obj.sec > 10 ? obj.sec : '0' + obj.sec);
            });
        }
    });

    function orderFail() {
        layer.msg('订单不存在，请重新下单，即将跳转到重新选座', function () {
            location.href = '/movie/to/detail?movieId=' + $('#movieId').val();
        }, 1000);
    }

    function getMovieShowTime(time) {
        var now = new Date();
        var t = new Date(+time);
        var today = now.getYear() + now.getMonth() + now.getDate();
        var day = getDay(t);

        if(today == (t.getYear() + t.getMonth() + t.getDate())) {
            day = '今天'
        }

        return (t.getMonth() + 1) + '月' + t.getDate() + '日' + '(' + day + ') ' + t.getHours() + ':' + t.getMinutes();
    }

    function getDay(time) {
        var t = time.getDay();
        var ret;

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

    function timeCount(leave, fn) {
        var ret = {};
        leave = +leave;
        setInterval(function () {
            leave -= 1;

            ret.min = Math.floor(leave/60);
            ret.sec = leave - ret.min * 60;

            fn(ret);
        }, 1000)
    }
});