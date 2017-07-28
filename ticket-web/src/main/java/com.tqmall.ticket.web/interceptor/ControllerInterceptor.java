package com.tqmall.ticket.web.interceptor;

import com.tqmall.ticket.error.TicketErrorCode;
import com.tqmall.ticket.error.TicketException;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller 拦截器 做错误码
 * Created by wurenzhi
 */
@Component
@Slf4j
public class ControllerInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        try {
            return methodInvocation.proceed();
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException:", e);
            String msg = e.getMessage();
            return TicketErrorCode.PARAM_ERROR.newDataResult(msg);
        } catch (TicketException e) {
            log.error("TicketException:", e);
            String msg = e.getMessage();
            return e.getErrorCode().newDataResult(msg);
        } catch (Exception e) {
            log.error("未定义异常:", e);
            String msg = e.getMessage();
            return TicketErrorCode.UNKNOW_ERROR.newDataResult(msg);
        }
    }
}
