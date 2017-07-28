package com.tqmall.ticket.biz.authorization;

import com.tqmall.ticket.biz.authorization.shiro.CustomRealm;
import com.tqmall.ticket.dal.entity.TicketUser;
import com.tqmall.ticket.error.TicketErrorCode;
import com.tqmall.ticket.error.TicketException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wurenzhi on 2016/12/26.
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Resource
    private CustomRealm customRealm;

    @Override
    public void putIntoSession(Object key, Object value) {
        customRealm.putIntoSession(key,value);
    }

    @Override
    public TicketUser getCurrentUser() {
        return customRealm.getCurrentUser();
    }

    @Override
    public void login(String userName, String password) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(true);
        try {
            subject.login(token);
        } catch (Exception e) {
            if(e.getCause() instanceof TicketException){
                throw (TicketException)e.getCause();
            }
            throw new TicketException(TicketErrorCode.LOGIN_SYS_ERROR);
        }
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }
}
