package com.tqmall.ticket.web.controller.authorization;

import com.tqmall.ticket.biz.authorization.AuthorizationService;
import com.tqmall.ticket.biz.authorization.VerifyService;
import com.tqmall.ticket.biz.base.LoginLogService;
import com.tqmall.ticket.biz.base.SmsService;
import com.tqmall.ticket.biz.user.UserService;
import com.tqmall.ticket.common.SimpleUtil;
import com.tqmall.ticket.common.constants.TicketConstants;
import com.tqmall.ticket.common.result.Result;
import com.tqmall.ticket.dal.entity.TicketUser;
import com.tqmall.ticket.error.TicketErrorCode;
import com.tqmall.ticket.error.TicketException;
import com.tqmall.ticket.web.vo.LoginInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wurenzhi on 2017/01/12.
 */
@Controller
@Slf4j
@RequestMapping("authorization")
public class AuthorizationController {

    //验证码开关
    @Value("${verify.switch}")
    private String verifySwitch;

    @Resource
    private AuthorizationService authorizationService;
    @Autowired
    private VerifyService verifyService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;
    @Resource
    private LoginLogService loginLogService;

    /**
     * 主界面
     * @return
     */
    @RequestMapping(value = "/main", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(TicketConstants.AUTHORIZATION_MAIN_PAGE);
        return modelAndView;
    }

    /***
     * 未验证情况下跳转到主页面并提示
     * @return
     */
    @RequestMapping(value = "/unauthorized", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ModelAndView unauthorizedMain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(TicketConstants.AUTHORIZATION_MAIN_PAGE);
        modelAndView.addObject("请登录");
        return modelAndView;
    }

    /**
     * 显示登录弹窗
     * @return
     */
    @RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Result<String> login() {
        return Result.wrapSuccessfulResult(verifySwitch);
    }

    /**
     * 在登录的时候，如果是第一次登录就直接创建账号
     * @param checkCode
     * @return
     */
    @RequestMapping(value = "do/login", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public Result<LoginInfoVO> doLogin(String mobile, String checkCode ) {
        LoginInfoVO loginInfoVO = new LoginInfoVO();
        TicketUser loginUser = authorizationService.getCurrentUser();
        //登陆过并未退出
        if (loginUser != null) {
            loginInfoVO.setMobile(loginUser.getUserMobile().toString());
            loginInfoVO.setPageUrl("/");
            return Result.wrapSuccessfulResult(loginInfoVO);
        }

        Assert.notNull(mobile,"请输入账户");
        Assert.isTrue(SimpleUtil.isMobile(mobile),"手机号格式错误");
        loginInfoVO.setMobile(mobile);
        loginInfoVO.setVerifySwitch(verifySwitch);

        Long mobileLong = Long.parseLong(mobile);

        /**对频繁登录做限制*/
        boolean isFrequent = loginLogService.isTooFrequent(mobileLong);
        if (isFrequent){
            throw new TicketException(TicketErrorCode.LOGIN_TOO_FREQUENT);
        }
        /**如果没有频繁登录，那么记录这次登录请求*/
        loginLogService.insertSelective(mobileLong);



        //开启验证码
        if (TicketConstants.VERIFY_ON.equals(verifySwitch)){
            //比较验证码
            if (StringUtils.isEmpty(checkCode)){
                loginInfoVO.setMessage("请输入验证码");
                throw new TicketException(TicketErrorCode.LOGIN_SYS_ERROR);
            }else{
                Integer checkCodeInt = Integer.parseInt(checkCode);
                //检查验证码是否正确
                boolean verifying = verifyService.verifying(mobileLong, checkCodeInt);
                //验证码出错，把错误返回前端展示
                if (!verifying){
                    loginInfoVO.setCheckCode(checkCode);
                    loginInfoVO.setMessage(TicketErrorCode.LOGIN_SYS_ERROR.getMessage());
                    throw new TicketException(TicketErrorCode.LOGIN_SYS_ERROR);
                }
            }

        }

        int existByMobile = userService.isExistByMobile(mobileLong);
        //已经存在该账户，则登录
        if (existByMobile > 0){
            try {
                authorizationService.login(mobile,mobile);
                loginInfoVO.setPageUrl("/");
                log.info(mobile+"登录系统");

            }catch(TicketException e){
                loginInfoVO.setMessage(e.getErrorCode().getMessage());
                log.error(e.getErrorCode().getMessage());
                authorizationService.putIntoSession(TicketConstants.SESSION_KEY_CN_USER_INFO,null);
                throw e;
            }catch (Exception e){
                loginInfoVO.setCheckCode(checkCode);
                loginInfoVO.setMessage(TicketErrorCode.LOGIN_SYS_ERROR.getMessage());
                authorizationService.putIntoSession(TicketConstants.SESSION_KEY_CN_USER_INFO,null);
                log.info("账号或验证码错误,mobile：{},checkCode：{}",mobile,checkCode);
                throw new TicketException(TicketErrorCode.LOGIN_SYS_ERROR);
            }
        }else{
            //不存在该账户，则注册
            TicketUser ticketUser = new TicketUser();
            ticketUser.setIsEnable(true);
            ticketUser.setUserMobile(mobileLong);
            userService.insertSelective(ticketUser);
        }

        return Result.wrapSuccessfulResult(loginInfoVO);
    }

    /**
     * 发送验证码
     * @param mobile
     * @return
     */
    @RequestMapping(value = "send/verify", method = RequestMethod.POST)
    @ResponseBody
    public Result sendVerify(String mobile) throws IOException {
        boolean isSuccess = smsService.sendMessage(Long.parseLong(mobile));
        if (isSuccess) {
            return Result.wrapSuccessfulResult("验证码发送成功") ;
        }
        return Result.wrapErrorResult(TicketErrorCode.MSG_SENDING_ERROR.getCode(),TicketErrorCode.MSG_SENDING_ERROR.getMessage());
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Result logout() {
        authorizationService.logout();
        return Result.wrapSuccessfulResult(true);
    }


    @RequestMapping(value = "/current/user", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Result<TicketUser> getSession() {
        TicketUser loginUser = authorizationService.getCurrentUser();
        if (loginUser != null) {
            log.info(loginUser.getUserMobile() + " session");
        }
        return Result.wrapSuccessfulResult(loginUser);
    }

    private static final Integer DAY = 24 * 3600;

    @RequestMapping(value = "set/cookie",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public void setToCookie(HttpServletRequest request, HttpServletResponse response,Integer cityId){
//        Cookie[] cookieArray = request.getCookies();
//        boolean isExistCityCookie = false;
        Cookie setCookie = new Cookie("cityCookie",cityId.toString());
        setCookie.setPath("/");
        setCookie.setMaxAge( 30*DAY );
//        for (Cookie cookie : cookieArray) {
//            if ( "cityCookie".equals( cookie.getName() ) ){
//                cookie.setValue(cityId.toString());
//                cookie.setPath("/");
//                cookie.setMaxAge( 30*DAY );
//                setCookie = cookie;
//                isExistCityCookie = true;
//            }
//        }
//        if ( !isExistCityCookie ){
//            Cookie cookie = new Cookie("cityCookie","1");
//            cookie.setPath("/");
//            setCookie = cookie;
//            cookie.setMaxAge( 30*DAY );
//        }
        response.addCookie(setCookie);
    }

    @RequestMapping(value = "get/cookie/value",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    @ResponseBody
    public Result<Integer> getCookieValue(HttpServletRequest request){
        Cookie[] cookieArray = request.getCookies();

        int cityId = 0;
        for (Cookie cookie : cookieArray) {
            if ( "cityCookie".equals( cookie.getName() ) ){
                cityId = Integer.parseInt( cookie.getValue() );
            }
        }

        return Result.wrapSuccessfulResult(cityId);
    }



}
