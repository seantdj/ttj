package com.sean.ttj.controllers;

import com.alibaba.fastjson.JSON;
import com.sean.ttj.model.UserModel;
import com.sean.ttj.responseEnv.ResponseEnvelope;
import com.sean.ttj.service.UserService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by tengdj on 2016/6/23.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Resource
    private UserService userService;

    @RequestMapping("/index")
    public String index(ModelMap modelMap){
        modelMap.put("title", "特特街，一个可以消费的网站");
        UserModel userMapper = userService.queryById(1L);
        modelMap.put("user", JSON.toJSONString(userMapper));
        return "home/index";
    }

    @RequestMapping("/login")
    public String login(ModelMap modelMap){
        modelMap.put("title", "特特街，一个可以消费的网站");
        return "home/login";
    }

    @RequestMapping("/sign-out")
    public String signOut(){
        return "home/login";
    }

    @RequestMapping(value = "/authenticationSuccess")
    @ResponseBody
    public ResponseEnvelope<String> authenticationSuccess(HttpServletRequest request){
        return new ResponseEnvelope<>(true,"");
    }

    @RequestMapping(value = "/authenticationFailure")
    @ResponseBody
    public ResponseEnvelope<String> authenticationFailure(HttpServletRequest request){
        AuthenticationException exception = (AuthenticationException)request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        return new ResponseEnvelope<>(false,exception.getMessage());
    }
}
