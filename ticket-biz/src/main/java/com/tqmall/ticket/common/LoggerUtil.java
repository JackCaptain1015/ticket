package com.tqmall.ticket.common;

import com.tqmall.ticket.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 *  日志工具类
 * Created by wurenzhi
 */
@Slf4j
public class LoggerUtil {

    /**
     * 把对象转换成字符串
     * @param object
     * @return
     */
    public static String objectToString(Object object){
        return object == null?"null": JsonUtil.obj2Str(object);
    }

    /**
     * 把结果集转换成字符串(result仅限单行数据,如果结果集中数据量多,则不应该把整个结果记录日志)
     * @param result
     * @return
     */
    public static String wraperResult(Result result){
        if(result == null){
            return "result is null";
        }

        if(result.isSuccess()){
            return "result is success";
        }

        List<String> errMsgList = new LinkedList();
        if(StringUtils.isNotBlank(result.getCode())){
            errMsgList.add(String.format("code[%s]",result.getCode()));
        }

        if(StringUtils.isNotBlank(result.getMessage())){
            errMsgList.add(String.format("message[%s]", result.getMessage()));
        }

        if(result.getData() != null){
            errMsgList.add(String.format("data[%s]", result.getData().toString()));
        }

        return StringUtils.join(errMsgList.toArray(), ',');
    }
}
