$(function () {
    var _curNo = 1;
    var getWaitPayOrder = (function () {
        var no = 1;
        return function () {
            $.get('/order/all/wait/pay', {
                curNo: no
            }, function (result) {
                if (result && result.data && result.data.length) {
                    result.data.forEach(function (e) {
                        e.time = '';
                        if (e.orderSeat) {
                            e.orderSeat = e.orderSeat.replace(/\(/g, '')
                                .replace(/\)/g, '座').replace(/,/g, '排').split('-').join('|')
                        }
                    });
                    var html = template('orderTemplate', {
                        list: result.data
                    });

                    var box = $(html);

                    $('.order-table.wait-pay').append(box);
                    no++;
                } else {
                    layer.msg('无更多数据');
                }
            })
        }
    }());

    getOrders(_curNo);

    template.helper('getDate', function (time) {
        var t = new Date(time);

        return t.getFullYear() + '年' + (t.getMonth() + 1) + '月' + t.getDate() + '日' + ' ' + t.getHours() + ':' + t.getMinutes();
    });

    $('.js-next').on('click', function () {
        if($('.js-to-pay').hasClass('tabs-mod__selected___2DmO6')) {
            getWaitPayOrder();
            return;
        }
        getOrders(++_curNo, function () {
            _curNo--;
        });
    });

    $('.js-to-pay').on('click', function () {
        $('.tabs-mod__selected___2DmO6').removeClass('tabs-mod__selected___2DmO6');

        $(this).addClass('tabs-mod__selected___2DmO6');
        
        $('.order-table.all').hide();
        $('.order-table.wait-pay').show();
    });
    $('.js-to-all').on('click', function () {
        $('.tabs-mod__selected___2DmO6').removeClass('tabs-mod__selected___2DmO6');
        
        $(this).addClass('tabs-mod__selected___2DmO6');
        $('.order-table.all').show();
        $('.order-table.wait-pay').hide();
    });

    getWaitPayCount();

    function getWaitPayCount() {
        $.get('/order/wait/pay/count', function (result) {
            if(result && result.success && result.data) {
                $('.to-pay-count').text(result.data);
                getWaitPayOrder();
            }
        });
    }

    function getOrders(curNo, cb) {
        $.get('/order/all', {
            curNo: curNo
        }, function (result) {
            if (result && result.data && result.data.length) {
                result.data.forEach(function (e) {
                    e.time = '';
                    if (e.orderSeat) {
                        e.orderSeat = e.orderSeat.replace(/\(/g, '')
                            .replace(/\)/g, '座').replace(/,/g, '排').split('-').join('|')
                    }
                });
                var html = template('orderTemplate', {
                    list: result.data
                });
                var count = 0;
                for(var i in result.data) {
                    if(result.data[i].orderStatus == 0) {
                        count++;
                    }
                }

                var box = $(html);

                if($('.js-to-pay').hasClass('tabs-mod__selected___2DmO6')) {
                    box.find('.order-item').hide();
                    box.find('.to-pay').show();
                }
                $('.order-table.all').append(box);
            } else {
                layer.msg('无更多数据', cb);
            }
        })
    }
});