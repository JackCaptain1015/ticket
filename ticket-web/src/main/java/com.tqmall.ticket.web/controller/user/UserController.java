package com.tqmall.ticket.web.controller.user;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wurenzhi on 2017/04/16.
 */
@RequestMapping("user")
@Controller
public class UserController {

    @RequestMapping( value = "index" , produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET )
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("/page/user-index");
        return modelAndView;
    }
}
