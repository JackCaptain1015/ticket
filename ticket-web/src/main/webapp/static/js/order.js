$(function () {
    var SEAT_CONFIG = {
        width: 46,
        height: 33
    };
    var pickedSeatCount = 0;

    var _timer = setInterval(function () {
        if(window._mobile) {
            $('.js-code-phone').val(window._mobile);

            clearInterval(_timer);
        }
    }, 100);
    setSeat();
    $('#timeStr').text( getMovieShowTime($('#time').val()) );
    $('.nav-cinema').addClass('current');
    
    $('.js-code-phone').on('click', function () {
        if(!window._mobile) {
            alert('请先登录');
            $('.js-signin').trigger('click.sign');
        }
    });

    $('.js-confirm').on('click', function () {
        var val = $('.js-code-phone').val(),
            seat = [];

        if(!val) {
            alert('请输入接收电子码的手机号');
            return;
        }
        if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(val))){
            alert("不是完整的11位手机号或者正确的手机号前七位");
            return false;
        }

        $('.js-pick-seat.seat-checked').each(function () {
             seat.push('(' + $(this).data('seatid') + ')');
        });

        $.ajax({
            url: '/order/save',
            data: JSON.stringify({
                orderMobile: $.trim(val),
                orderSeat: seat.join('-'),
                scheduleId: $('#scheduleId').val()
            }),
            contentType: 'application/json',
            type: 'post',
            success: function (result) {
                if (result && result.success) {
                    location.href = '/order/index?orderId=' + result.data.id;
                } else {
                    if(result.data) {
                        layer.msg(result.data, function () {
                            location.reload();
                        });
                    }
                }
            }
        });
    });

    $(document).on('click', '.js-pick-seat', function () {
        if($(this).hasClass('seat-sold')) {
            return;
        }

        if($(this).hasClass('seat-checked')) {
            pickedSeatCount--;
        } else if(pickedSeatCount < 5) {
            pickedSeatCount++;
        } else {
            alert('最多只能选择五个座位');
            return;
        }
        $('.seat-count').text(pickedSeatCount);
        $('.total-price').text(pickedSeatCount * $('#price').attr('value'));

        $(this).toggleClass('seat-checked');
        setPickedSeat();
    });

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

    function setSeat() {
        $.get('/schedule/detail', {
            schedualId: $('#scheduleId').val()
        }, function (result) {
            if(result && result.data && result.data.seatList
                && result.data.seatList.length) {
                var seatList = [
                    window.convertSeatData(result.data.seatList),
                    window.convertSeatData(result.data.existSeatList)
                ];

                var html = template('seatTemplate', {
                    config: SEAT_CONFIG,
                    allSeat: seatList[0],
                    pickedSeat: seatList[1],
                    width: window.getSeatViewWidth(SEAT_CONFIG.width, seatList[0])
                });

                $('#seatView').append(html).children().show();

                $('#totalSeat').text( getCount(seatList[0]) );
                $('#pickedSeat').text( getCount(seatList[1]) );

                var len = seatList[0].length;
                html = '';
                for(var i = 1; i <= len; i++) {
                    html += '<label style="position: absolute; top: ' + (i-1) * 33 + 'px; margin-top: 6px;">' + (i-1) + '</label>'
                }

                $('.J_coulmn ul').append(html);
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

    function setPickedSeat() {
        var $list = $('.js-pick-seat.seat-checked');
        var html = '';
        if($list.length) {
            $list.each(function (i, e) {
                html += '<a class="seatNum">' + $(this).data('name') + '</a>'
            });

            $('.No_set').hide();
        } else {
            $('.No_set').show();
        }

        $('.picked-seat-list').html(html);
    }
});