package com.tqmall.ticket.biz.util;

import com.google.common.collect.Lists;
import com.tqmall.ticket.common.ReflectUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jackcaptain on 2017/7/27.
 */
public class ReflectUtilTest {
    @Test
    public void getFieldTest() throws InterruptedException, IllegalAccessException {
        List<StudentTestBean> list = Lists.newArrayList();
        StudentTestBean studentTestBean = new StudentTestBean();
        list.add(studentTestBean);
        ReflectUtil.setDefaultValueForNull(list);
        System.out.println(list);
    }

    @Test
    public void getConstValueTest(){
        StudentTestBean studentTestBean1 = new StudentTestBean();
        StudentTestBean studentTestBean2 = new StudentTestBean();
        StudentTestBean studentTestBean3 = new StudentTestBean();
        StudentTestBean studentTestBean4 = new StudentTestBean();
        studentTestBean1.setId(1L);
        studentTestBean2.setId(2L);
        studentTestBean3.setId(3L);
        studentTestBean4.setId(4L);
        studentTestBean1.setStuAgeLong(2L);
        studentTestBean2.setStuAgeLong(2L);
        studentTestBean3.setStuAgeLong(4L);
        studentTestBean4.setStuAgeLong(4L);

        List<StudentTestBean> list = Lists.newArrayList();
        list.add(studentTestBean1);
        list.add(studentTestBean2);
        list.add(studentTestBean3);
        list.add(studentTestBean4);
        Map<Long, List<Long>> longListMap = convert2LongMapList(list, "stuAgeLong", "id");
        System.out.println(longListMap);

    }

    public static <V> Map<Long, List<Long>> convert2LongMapList(List<V> list, String keyProperty, String valueProperty) {

        Map<Long, List<Long>> rst = new HashMap<>();
        if (list == null || list.size() == 0) return rst;

        for (V v : list) {
            try {
                if (v == null) continue;
                String objKey = BeanUtils.getProperty(v, keyProperty);
                if (objKey == null) continue;
                Long key = Long.parseLong(objKey);
                List<Long> valueList = rst.get(key);
                if (valueList == null) {
                    valueList = new ArrayList<>();
                    rst.put(key, valueList);
                }
                String objValue = BeanUtils.getProperty(v, valueProperty);
                if (objValue == null) continue;
                valueList.add(Long.parseLong(objValue));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return rst;
    }

}
