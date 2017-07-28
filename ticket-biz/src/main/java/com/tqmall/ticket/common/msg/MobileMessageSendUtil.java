package com.tqmall.ticket.common.msg;

import com.alibaba.fastjson.JSON;
import com.tqmall.ticket.common.constants.TicketConstants;
import com.tqmall.ticket.common.constants.VerityCodeConstants;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by wurenzhi on 2017/02/17.
 */
public class MobileMessageSendUtil {

    private static final String NONCE="123456";


    public static int sendMsg(String templateId,String mobile,String value) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(VerityCodeConstants.SERVER_URL);

        String curTime=String.valueOf((new Date().getTime()/1000L));
        String checkSum=CheckSumBuilder.getCheckSum(VerityCodeConstants.APP_SECRET,NONCE,curTime);

        //设置请求的header
        post.addHeader("AppKey",VerityCodeConstants.APP_KEY);
        post.addHeader("Nonce",NONCE);
        post.addHeader("CurTime",curTime);
        post.addHeader("CheckSum",checkSum);
        post.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        //设置请求参数
        List<NameValuePair> nameValuePairs =new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("templateid",templateId));
        nameValuePairs.add(new BasicNameValuePair("mobiles","[\""+mobile+"\"]"));
        nameValuePairs.add(new BasicNameValuePair("params","[\""+value+"\"]"));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));

        //执行请求
        HttpResponse response=httpclient.execute(post);

        String responseEntity= EntityUtils.toString(response.getEntity(),"utf-8");

        //判断是否发送成功，发送成功返回true
        String code= JSON.parseObject(responseEntity).getString("code");
        String sendId= JSON.parseObject(responseEntity).getString("msg");
        String codeString= JSON.parseObject(responseEntity).getString("obj");
        if (code.equals("200"))
        {return 0;}

        return 500;
    }

    /**
     * 入参格式为"1,2,3"这样
     * 返回的值可以直接放入sendMsg()方法中
     * */
    public static String moreParamDeal(String param){
        String[] splitStringArray = param.split(",");
        StringBuilder sb = new StringBuilder();
        for (String str : splitStringArray) {
            sb = sb.append("\"").append(str).append("\",");
        }
        for (int i = splitStringArray.length; i < 5 ; i++) {
            sb = sb.append("\"").append("\",");
        }
        String string = sb.toString();
        String returnString = string.substring(1, string.length() - 2);
        return returnString;
    }
}
