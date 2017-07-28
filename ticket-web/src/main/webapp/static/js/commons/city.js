$(function () {
    var cityMap = {};

    $('.js-city').hover(function () {
        $(this).addClass('cityWrap_hover');
    }, function () {
        $(this).removeClass('cityWrap_hover');
    });

    $(document).on('click', '.js-select-city', function () {
        setCity($(this).data('id'), $(this).text());
    });

    $.get('/city/all', function (result) {
        if(result && result.success && result.data) {
            var list = result.data;
            var html = '', _citys;

            for(var i = 0; i < list.length; i++) {
                _citys = list[i].ticketOpenCityVOList;
                html += '<li>';

                html += '<label>' + list[i].firstSpellCityName + '</label>';
                for(var j = 0; j < _citys.length; j++) {
                    cityMap[ _citys[j].cityId ] = _citys[j].cityName;

                    html += '<a class="js-select-city" data-id="' + _citys[j].cityId + '" href="javascript:void(0);">' + _citys[j].cityName + '</a>';
                }
                html += '</li>'
            }

            $('.M-cityList ul').html(html);
            getCurrentCity();
        }
    });

    function getCurrentCity() {
        $.get('/authorization/get/cookie/value', function (result) {
            if(result && result.data) {
                $('#cityName span, .city-name').text( cityMap[result.data] );
                window._cityId = result.data;
            }
        })
    }

    function setCity(id, name) {
        $('#cityName span, .city-name').text( name );
        
        window._cityId = id;
        $.get('/authorization/set/cookie', {
            cityId: id
        });
    }
})