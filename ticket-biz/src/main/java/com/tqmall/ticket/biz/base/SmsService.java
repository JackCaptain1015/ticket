package com.tqmall.ticket.biz.base;

import java.io.IOException;
import java.util.Date;

/**
 * Created by wurenzhi on 2017/02/17.
 */
public interface SmsService {

    public boolean sendMessage(Long mobile) throws IOException;

    public boolean checkVerifyCode(Long mobile,Integer verifyCode);

    public boolean ticketsNo(Long mobile,String ticketsNo, Date movieShowTime) throws IOException;
}
