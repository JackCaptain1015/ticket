package com.tqmall.ticket.biz.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.JsonUtil;
import com.tqmall.ticket.common.ReflectUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jackcaptain on 2017/7/18.
 */
public class JsonUtilTest {

    @Test
    public void list2JsonTest(){
        List<StudentTestBean> studentTestBeanList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            StudentTestBean studentTestBean = new StudentTestBean();
            studentTestBean.setStudentAddress("地址："+i);
            studentTestBean.setStudentAge(i+20);
            studentTestBean.setStudentName("名字："+i+"丁丁");
            studentTestBeanList.add(studentTestBean);
        }
        String s = JsonUtil.list2JsonStr(studentTestBeanList,"list");
        System.out.println(s);
    }

    @Test
    public void json2Object(){
        System.out.println("方法:"+getError());
    }

    private String getError(){
        try {
            StudentTestBean studentTestBean = JSON.parseObject("123", StudentTestBean.class);
        }catch (Exception e){
            System.out.println("json错误");
            return "json错误了";
        }
        return "正确";
    }

    @Test
    public void getJsonArrayValueInKeyTest(){
        String jsonStr = "{\"userlist\":[{\"name\":\"name\",\"userid\":\"036510211921368142\"}]}";
        List<String> jsonArrayValueInKeyList = JsonUtil.getJsonArrayValueInKey(jsonStr, "userlist", "userid");
        System.out.println(jsonArrayValueInKeyList);
    }

    @Test
    public void getJsonArrayBeanTest(){
        String jsonstr = "{\"userlist\":[{\"name\":\"name\",\"userid\":\"09133132976231\"}]}";

        List<UserTestBean> userlist = JsonUtil.getJsonArrayBean(jsonstr, "userlist", UserTestBean.class);
        System.out.println(userlist);
    }

}
