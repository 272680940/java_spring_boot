package com.hqyj.java_spring_boot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.account.dao.UserDao;
import com.hqyj.java_spring_boot.modules.account.entity.User;
import com.hqyj.java_spring_boot.modules.account.service.UserService;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import com.hqyj.java_spring_boot.utils.MD5Utils;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    //注册用户
    @Override
    public Result<User> insertUser(User user) {
        //判断用户名是否重复
        User userTemp = userDao.getUserByUserName(user.getUserName());
        if (userTemp != null){
            return new Result<User>(Result.ResultStatus.FAILD.status,
                    "用户名存在!",user);
        }
        //获取当前时间
        user.setCreateDate(LocalDateTime.now());
        //密码加密
        user.setPassword(MD5Utils.getMD5(user.getPassword()));
        userDao.insertUser(user);//注册用户
        return new Result<User>(Result.ResultStatus.SUCCESS.status,
                "注册成功!",user);
    }

    //登录
    @Override
    public Result<User> getUserByUserNameAndPassword(User user) {
        //通过userName得到User对象
        User userTemp = userDao.getUserByUserName(user.getUserName());
        //数据库存在User对象，且加密后的密码比对一致
        if (userTemp != null && userTemp.getPassword()
                .equals(MD5Utils.getMD5(user.getPassword()))){
            return new Result<User>(Result.ResultStatus.SUCCESS.status,"login success！",userTemp);
        }
        return new Result<User>(Result.ResultStatus.FAILD.status,"UserName or password is error！");
    }

    //脚本式多条件分页\查询
    @Override
    public PageInfo<User> getUsersBySearchVo(SearchVo searchVo) {
        searchVo.initSearchVo();//初始化
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<User>(
                Optional.ofNullable(userDao.getUsersBySearchVo(searchVo))//查询有结果
                        .orElse(Collections.emptyList())//查询为空返回空集
        );
    }
}
