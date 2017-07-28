//工具类，声明全明方法放这里
var util = {};
$(function(){
    var UA = window.navigator.userAgent;
    // util通用方法
    util = {
        UA: UA,
        isChrome: /chrome/gi.test(UA),
        isIE: /msie/gi.test(UA),
        /**
         * 打印
         * @param url 地址（可选，默认当前页面url）
         */
        print: function(url) {
            var name = 'printFrame', iframe,
                print = function (win) {
                    win = win == null ? window : win;
                    win.print();
                };

            if (url) {
                iframe = frames[name];
                if (iframe) {
                    iframe.location = url;
                } else {
                    iframe = document.createElement(this.isIE ? '<iframe name="' + name + '"></iframe>' : "iframe");
                    iframe.style.cssText = "height: 0;opacity: 0;filter: opacity(0);";
                    if (this.isIE) {
                        iframe.attachEvent("onload", function () {
                            print(iframe.contentWindow);
                        });
                    } else {
                        iframe.name = name;
                        iframe.onload = function () {
                            print(iframe.contentWindow);
                        };
                    }
                    iframe.src = url;
                    document.body.appendChild(iframe);
                }
            } else {
                print();
            }

        },
        /**
         * 获得展示的时间
         * @param timestamp 时间戳（精确到毫秒）
         * @param format 展现格式（可选，默认yyyy-MM-dd hh:mm:ss）
         * @returns String 时间字符串
         */
        getDateTime: function(timestamp, format) {
            if (isNaN(timestamp) || timestamp == null) {
                return "";
            }

            var date = new Date(timestamp),
                map = {
                    "M": date.getMonth() + 1, //月份
                    "d": date.getDate(), //日
                    "h": date.getHours(), //小时
                    "m": date.getMinutes(), //分
                    "s": date.getSeconds() //秒
                };

            if (!format) {
                format = "yyyy-MM-dd hh:mm:ss"
            }

            format = format.replace(/([yMdhms])+/g, function (all, t) {
                var v = map[t];
                if (v != null) {
                    if (all.length > 1) {
                        v = '0' + v;
                        v = v.substr(v.length - 2);
                    }
                    return v;
                }
                else if (t === 'y') {
                    return (date.getFullYear() + '').substr(4 - all.length);
                }
                return all;
            });
            return format;
        },
        /**
         *
         * @param timeStr 时间字符串
         * @returns Number 时间戳（精确到毫秒）
         */
        getTimestamp: function(timeStr) {
            if (typeof timeStr === "string") {
                try {
                    if (/^\d{4}-\d{2}-\d{2}$/.test($.trim(timeStr))) {
                        timeStr += " 00:00:00";
                    }
                    return new Date(timeStr).getTime();
                } catch(ex) {
                    console&&console.error(timeStr + "的格式不正确!", "util.getTimestamp");
                    return null;
                }
            } else {
                return null;
            }
        },
        getDiscount: function (discount) {
            if (isNaN(discount)) {
                return 1;
            } else {
                return discount/10;
            }
        },
        /**
         * 单选、全选处理
         * @param scope 作用域
         */
        selectAllInit: function(scope) {
            scope = scope ? scope : '.select-group';
            $(document)
                .on('change', '.js-select-all', function() {
                    var $this = $(this),
                        $scope = $this.parents(scope),
                        checked = $this.prop('checked');
                    $('.js-select-item', $scope).prop('checked', checked);
                })
                .on('change', '.js-select-item', function() {
                    var $self = $(this),
                        $scope = $self.parents(scope),
                        checked = $self.prop('checked'),
                        flag = true;
                    if (checked) {
                        $('.js-select-item', $scope).not($self).each(function() {
                            var $this = $(this),
                                checked = $this.prop('checked');
                            if (!checked) {
                                // 取消选中状态
                                flag = false;
                                return flag;
                            }
                        });
                        $('.js-select-all', $scope).prop('checked', flag);
                    } else {
                        $('.js-select-all', $scope).prop('checked', checked);
                    }
                });
        },
        /**
         * url 参数提取
         * @param search location.search
         * @returns object
         */
        parseUrl: function(search) {
            var params = {};

            if (!/^\?/.test(search)) {
                search = location.search;
            }

            search = search.substr(1).split('&');
            $.each(search, function (i, v) {
                var tmp = v.split("=");
                params[ tmp[0] ] = tmp[1];
            });

            return params;
        }
    };

    // template 扩展方法
    seajs.use("art", function(tpl) {
        // 时间戳转时间字符串
        tpl.helper('$getDateTime', function (timestamp, format) {
            return util.getDateTime(timestamp, format);
        });
        
        // JSON对象转换JSON字符串
        tpl.helper('$getJSONStr', function(json) {
            try {
                return JSON.stringify(json);
            } catch (ex) {
                return json;
            }
        });
        
        // 折扣处理
        tpl.helper('$setDiscount', function (discount) {
            if (isNaN(discount)) {
                return 10;
            } else {
                if (discount > 10 || discount <= 0) {
                    return 10;
                } else {
                    if (discount <= 1) {
                        discount *= 10;
                    }
                    if (discount % 1 === 0) {
                        return discount;
                    } else {
                        return Number(discount).toFixed(1);
                    }
                }
            }
        })
    });
});

String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function($0, $1) {
        return args[$1];
    });
};