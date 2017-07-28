package com.tqmall.ticket.common.constants;


    public interface VerityCodeConstants {

        //默认验证码时效性为10分钟
        Integer DEFAULT_USEFUL_TIME = 10;

        /**
         * 验证码状态
         */
        byte VERIFY_STATUS_INIT = 0;
        byte VERIFY_STATUS_SUCCESS = 1;
        byte VERIFY_STATUS_FAILED = 2;


        //发送模板短信的请求URL
        String SERVER_URL="https://api.netease.im/sms/sendtemplate.action";//请求的URL
        //注册应用的APP_KEY与APP_SECRET
        String APP_KEY="";
        String APP_SECRET="";//密码

        /**
         * 通知模板的短信模板ID
         */
        //验证码短信模板
        String VERITY_CODE_TEMPLATE_ID = "";
        //取票码短信模板
        String TICKET_CODE_TEMPLATE_ID = "";
    }


