package com.tqmall.ticket.biz.authorization.shiro;

import com.tqmall.ticket.biz.bo.TicketUserBO;
import com.tqmall.ticket.biz.user.UserService;
import com.tqmall.ticket.common.BdUtil;
import com.tqmall.ticket.common.constants.TicketConstants;
import com.tqmall.ticket.dal.entity.TicketUser;
import com.tqmall.ticket.error.TicketErrorCode;
import com.tqmall.ticket.error.TicketException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.tqmall.ticket.error.TicketErrorCode.ACCOUNT_NOT_ENABLE;

/**
 * Created by wurenzhi on 2016/12/26.
 */
@Component
@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //账号就是手机号
        String userMobile = authenticationToken.getPrincipal().toString();
        TicketUserBO ticketUserBO = userService.selectByMobile(Long.parseLong(userMobile));
        if (ticketUserBO == null){
            throw new UnknownAccountException("当前用户"+userMobile+"不存在");
            //如果没开通
        }else if(!ticketUserBO.getIsEnable()){
            throw new TicketException(TicketErrorCode.ACCOUNT_NOT_ENABLE);
        }
        TicketUser userInfo = BdUtil.bo2do(ticketUserBO, TicketUser.class);
        userInfo.setUserMobile(Long.parseLong(ticketUserBO.getUserMobile()));
        this.putIntoSession(TicketConstants.SESSION_KEY_CN_USER_INFO,userInfo);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userMobile,userMobile,getName());
        return simpleAuthenticationInfo;
    }

    /**
     *
     * @return 用户基本信息
     */
    public TicketUser getCurrentUser() {
        return (TicketUser) this.getSessionValue(TicketConstants.SESSION_KEY_CN_USER_INFO);
    }

    /**
     * 以key为键 值为value放放到sesson中
     * @param key
     * @param value
     */
    public void putIntoSession(Object key, Object value) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Session session = subject.getSession();
            session.setAttribute(key, value);
        }
    }

    /**
     * 获取session信息
     * @param key
     * @return
     */
    public Object getSessionValue(Object key) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Session session = subject.getSession();
            return session.getAttribute(key);
        }
        return null;
    }
}
