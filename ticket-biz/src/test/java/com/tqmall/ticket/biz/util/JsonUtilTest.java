package com.tqmall.ticket.biz.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.JsonUtil;
import com.tqmall.ticket.common.ReflectUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

}
