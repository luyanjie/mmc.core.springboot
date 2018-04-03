package com.maochong.helloword.controller;

import com.maochong.helloword.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author jokin
 * */

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping("/login")
    public String login(String account,String password,HttpServletRequest req)
    {
        HttpSession httpSession = req.getSession();
        String isLogin = "isLogin";

        if(httpSession.getAttribute(isLogin)!=null)
        {
            return "true Session";
        }
        else {
            System.out.println("no Session");
        }


        // 登录成功
        if(loginService.login(account,password))
        {
            httpSession.setAttribute(isLogin,true);
            return "true login";
        }
        return "false";
    }
}
