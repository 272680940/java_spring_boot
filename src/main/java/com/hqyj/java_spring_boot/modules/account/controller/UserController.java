package com.hqyj.java_spring_boot.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.account.entity.User;
import com.hqyj.java_spring_boot.modules.account.service.UserService;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *注册用户
     * {"userName":"zhangsan","password":"123456"}
     * localhost/api/user ---- POST
     */
    @PostMapping(value = "/user",consumes = "application/json")
    public Result<User> insertUser(@RequestBody User user){
        return userService.insertUser(user);
    }

    /**
     * 登录
     * {"userName":"zhangsan","password":"123456"}
     * localhost/api/login ---- POST
     */
    @PostMapping(value = "/login",consumes = "application/json")
    public Result<User> getUserByUserNameAndPassword(@RequestBody User user){
        return userService.getUserByUserNameAndPassword(user);
    }

    /**
     * 脚本式多条件分页、查询
     * {"currentPage":1,"pageSize":5"keyWord":"adm"}
     * localhost/api/users ---- POST
     */
    @PostMapping(value = "/users",consumes = "application/json")
    public PageInfo<User> getUsersBySearchVo(@RequestBody SearchVo searchVo){
        return userService.getUsersBySearchVo(searchVo);
    }
}
