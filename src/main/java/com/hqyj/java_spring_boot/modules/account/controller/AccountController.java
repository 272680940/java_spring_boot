package com.hqyj.java_spring_boot.modules.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/users")
    public String usersPage(){
        //返回外层的碎片组装器
        return "index";
    }

    /**
     * localhost/account/login --- GET
     */
    //登录页面
    @GetMapping("/login")
    public String loginPage(){
        //返回外层的碎片组装器
        return "indexSimple";
    }

    /**
     * localhost/account/register --- GET
     */
    //注册页面
    @GetMapping("/register")
    public String registerPage(){
        //返回外层的碎片组装器
        return "indexSimple";
    }
}
