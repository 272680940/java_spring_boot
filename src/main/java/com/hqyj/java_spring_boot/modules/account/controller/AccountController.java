package com.hqyj.java_spring_boot.modules.account.controller;

import com.hqyj.java_spring_boot.modules.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页面
     * localhost/account/login --- GET
     */
    @GetMapping("/login")
    public String loginPage(){
        //返回外层的碎片组装器
        return "indexSimple";
    }

    /**
     * 跳转到注册页面
     * localhost/account/register --- GET
     */
    @GetMapping("/register")
    public String registerPage(){
        //返回外层的碎片组装器
        return "indexSimple";
    }

    /**
     * 退出,跳转到登录页面
     * localhost/account/logout ---- GET
     */
    @GetMapping("/logout")
    public String logout(ModelMap modelMap){
        //退出, 清除session
        userService.logout();
        //跳转到登录页面
        modelMap.addAttribute("template","account/login");
        //返回外层的碎片组装器
        return "indexSimple";
    }

    /**
     * 跳转到User页面
     * localhost/account/users --- GET
     */
    @GetMapping("/users")
    public String usersPage(){
        //返回外层的碎片组装器
        return "index";
    }

    /**
     * 跳转到Roles页面
     * localhost/account/roles --- GET
     */
    @GetMapping("/roles")
    public String rolesPage(){
        //返回外层的碎片组装器
        return "index";
    }

    /**
     * 跳转到Resources页面
     * localhost/account/roles --- GET
     */
    @GetMapping("/resources")
    public String resourcesPage(){
        //返回外层的碎片组装器
        return "index";
    }

    /**
     * 跳转到profile页面
     * localhost/account/profile --- GET
     */
    @GetMapping("/profile")
    public String profilePage(){
        //返回外层的碎片组装器
        return "index";
    }


}
