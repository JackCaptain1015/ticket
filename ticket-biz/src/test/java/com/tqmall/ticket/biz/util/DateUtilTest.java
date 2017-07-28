package com.tqmall.ticket.biz.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tqmall.ticket.biz.bo.TicketCinemaBO;
import com.tqmall.ticket.biz.bo.TicketMovieBO;
import com.tqmall.ticket.biz.bo.TicketSpecificScheduleBO;
import com.tqmall.ticket.common.*;
import com.tqmall.ticket.dal.entity.TicketOpenCity;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wurenzhi on 2017/03/02.
 */
public class DateUtilTest<T> {

    private List<T> list = new ArrayList<>();

    @Test
    public void test() throws IllegalAccessException, InstantiationException {
//        List list = Lists.newArrayList();
        TicketSpecificScheduleBO bo = new TicketSpecificScheduleBO();
        bo.setGmtCreate(new Date());
        TicketMovieBO movieBO = new TicketMovieBO();
        movieBO.setGmtModified(new Date());
        bo.setTicketMovieBO(movieBO);

//        list.add(1);
//        list.add(1);
//        list.add(bo);
//        list.add(1);
//        list.add(1);
//        list.add(1);
//        System.out.println("list:"+list.size());
        jestsout(bo);
    }

    private void jestsout(Object ... os){
        System.out.println(os[0]);
        for (Object o  : (List) os[0]) {
            System.out.println(isSimpleObj(o.getClass()));
        }
    }

    public boolean isSimpleObj(Class<?> classObj) {

        List<Class<?>> SIMPLE_CLASS_OBJ = Lists.newArrayList();
        SIMPLE_CLASS_OBJ.add(Number.class);
        SIMPLE_CLASS_OBJ.add(String.class);
        SIMPLE_CLASS_OBJ.add(Boolean.class);

        for (Class<?> c : SIMPLE_CLASS_OBJ) {
            if (c.isAssignableFrom(classObj))
                return true;
        }
        return false;
    }

    @Test
    public void testDateUtil(){
        Calendar currentDate = new GregorianCalendar();
        currentDate.setFirstDayOfWeek(Calendar.SUNDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date time = currentDate.getTime();
        String s = DateUtil.date2Ymd(time);
        System.out.println(s);
    }


}
