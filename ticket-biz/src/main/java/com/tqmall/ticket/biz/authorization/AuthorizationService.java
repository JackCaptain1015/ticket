package com.tqmall.ticket.biz.authorization;

import com.tqmall.ticket.dal.entity.TicketUser;
import org.springframework.stereotype.Service;

/**
 * Created by wurenzhi on 2016/12/26.
 */
public interface AuthorizationService {

    /**
     * 添加到 session
     * @param key key
     * @param value  value
     */
    public void putIntoSession(Object key, Object value) ;

    /**
     *
     * @return 当前用户信息
     */
    public TicketUser getCurrentUser();

    /**
     * 登录：使用shiro中的登录
     * @param userName 用户名
     * @param password 密码
     * @throws Exception
     */
    public void login(String userName,String password) throws Exception;

    /**
     * 用户logout
     */
    public void logout();
}
