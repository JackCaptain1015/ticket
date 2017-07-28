<link href="/static/css/commons/signin.css" rel="stylesheet">

        <script type="text/javascript">
            $(function () {
                var timer, left = 60,
                        _layer, _code = true;

                $.get('/authorization/current/user', function (result) {
                    if(result && result.data) {
                        var mobile = result.data.userMobile;
                        $('.js-signin').text(mobile)
                                .attr('href', '/user/index')
                                .attr('title', '个人中心')
                                .parent().addClass('signed');

                        window._mobile = mobile;
                    }
                });
                $.get('/authorization/login', function (result) {
                    if(result.data == 'off') {
                        $('.static-form .pwd-field').remove();
                        _code = false;
                    }
                });
                $('.js-signin').on('click.sign', function () {
                    if(window._mobile) {
                        return;
                    }
                    _layer = layer.open({
                        type: 1,
                        title: '登录',
                        content: $('#signin'),
                        resize: false,
                        move: false,
                        area: ['300px', '240px']
                    });
                    $('.login-text').trigger('input');
                });

                $('.js-signout').on('click', function () {
                    $.get('/authorization/logout', function (result) {
                        if(result && result.success) {
                            layer.msg('已退出');

                            setTimeout(function () {
                                location.reload();
                            }, 1000);
                        }
                    });
                });

                $(document).on('input', '.login-text', function () {
                    $(this).parent().toggleClass('ph-hide', $(this).val() !== '');
                })

                // 发送短信
                    .on('click', '.js-msg', function () {
                        var that = $(this);

                        if(left < 60) {
                            return;
                        } else {
                            timer = setInterval(function () {
                                if (left === 0) {
                                    clearInterval(timer);
                                    left = 60;
                                    that.text('发送短信').removeClass('disabled');
                                    return;
                                }
                                that.text('发送短信(' + left-- + ')').addClass('disabled');
                            }, 1000);
                        }

                        $.post('/authorization/send/verify', {
                            mobile: $('.form input[name="mobile"]').val()
                        }, function (result) {
                            if(result.success)
                                layer.msg('短信已发送，请注意查收');
                            else {
                                layer.msg(result.message);
                            }
                        });
                    })
                        .on('click', '.js-submit', function () {
                            var $form = $(this).parents('.form'),
                                    mobile= $form.find('input[name="mobile"]').val();

                            if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(mobile))){
                                layer.msg("不是完整的11位手机号或者正确的手机号前七位");
                                return false;
                            }
                            $.ajax({
                                url: '/authorization/do/login',
                                type: 'POST',
                                data: {
                                    mobile: mobile,
                                    checkCode: _code ? $form.find('input[name="checkCode"]').val() : ''
                                },
                                success: function (result) {
                                    if(result.success) {
                                        layer.msg('登陆成功');
                                        $('.js-signin').text(mobile)
                                                .attr('href', '/user/index')
                                                .attr('title', '个人中心')
                                                .parent().addClass('signed');
                                        layer.close(_layer);

                                        window._mobile = mobile;
                                    } else {
                                        layer.msg(result.message);
                                    }
                                }
                            })
                        })
            });
        </script>

        <div class="bd" id="signin" style="display: none;">

            <div class="static-form " id="J_StaticForm">
                <div class="form" id="J_Form">

                    <div class="field username-field">
                        <span class="ph-label">手机</span>
                        <input name="mobile"  class="login-text J_UserName" value="" maxlength="32" tabindex="1"
                               type="text">
                    </div>

                    <div class="field pwd-field">
                        <span class="ph-label">验证码</span>
				        <input name="checkCode"
                               class="login-text" maxlength="40" tabindex="2" autocomplete="off" type="password">
                        <span class="msg js-msg" title="获取验证码">发送短信</span>
                    </div>


                    <div class="submit">
                        <button class="J_Submit js-submit" tabindex="3" id="J_SubmitStatic" data-ing="正在登录...">登 录</button>
                    </div>
            </div>

        </div>
            </div>