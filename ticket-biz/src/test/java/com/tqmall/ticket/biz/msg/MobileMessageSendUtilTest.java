package com.tqmall.ticket.biz.msg;

import com.tqmall.ticket.common.VerifyGenerator;
import com.tqmall.ticket.common.msg.MobileMessageSendUtil;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by wurenzhi on 2017/02/17.
 */
public class MobileMessageSendUtilTest {


    @Test
    public void testString(){
        String a = "\"asd\"";
        System.out.println(a);
    }
    /**
     * 测试短信能否正常发送
     * @throws IOException
     */
    @Test
    public void sendMsgTest() throws IOException {
        String verify = VerifyGenerator.genVerify();
        System.out.println("verify:"+verify);
        Integer successInt = MobileMessageSendUtil.sendMsg("3063024","18375327107",verify);
        System.out.println(successInt);
    }

}
