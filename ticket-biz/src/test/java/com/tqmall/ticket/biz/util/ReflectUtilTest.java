package com.tqmall.ticket.biz.util;

import com.tqmall.ticket.common.ReflectUtil;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by jackcaptain on 2017/7/27.
 */
public class ReflectUtilTest {
    @Test
    public void getFieldTest() throws InterruptedException, IllegalAccessException {
        StudentTestBean studentTestBean = new StudentTestBean();
        ReflectUtil.setNullDefaultValueForBean(studentTestBean);
        System.out.println(studentTestBean);
    }
}
