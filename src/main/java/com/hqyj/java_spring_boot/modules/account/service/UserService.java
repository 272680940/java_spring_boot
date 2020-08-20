package com.hqyj.java_spring_boot.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.account.entity.User;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;

public interface UserService {
    //注册用户
    Result<User> insertUser(User user);

    //登录
    Result<User> getUserByUserNameAndPassword(User user);

    //脚本式多条件分页\查询
    PageInfo<User> getUsersBySearchVo(SearchVo searchVo);
}
