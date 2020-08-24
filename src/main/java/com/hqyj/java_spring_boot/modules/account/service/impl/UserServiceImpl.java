package com.hqyj.java_spring_boot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.account.dao.UserDao;
import com.hqyj.java_spring_boot.modules.account.dao.UserRoleDao;
import com.hqyj.java_spring_boot.modules.account.entity.Role;
import com.hqyj.java_spring_boot.modules.account.entity.User;
import com.hqyj.java_spring_boot.modules.account.service.UserService;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import com.hqyj.java_spring_boot.utils.MD5Utils;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    //注册用户
    @Override
    @Transactional
    public Result<User> insertUser(User user) {
        //判断用户名是否重复
        User userTemp = userDao.getUserByUserName(user.getUserName());
        if (userTemp != null){
            return new Result<User>(Result.ResultStatus.FAILD.status,
                    "用户名存在!",user);
        }
        //初始化数据并添加User
        user.setCreateDate(LocalDateTime.now());//获取当前时间
        user.setPassword(MD5Utils.getMD5(user.getPassword())); //密码加密
        userDao.insertUser(user);//注册用户,返回新增数据的 userId

        //先清空当前用户角色关系信息--user_role
        userRoleDao.deleteUserRoleByUserId(user.getUserId());
        //获取用户选择的角色
        List<Role> roles = user.getRoles();
        if(roles!=null && !roles.isEmpty()){
            //遍历集合新写法
            roles.stream().forEach(item -> {
                //添加User时添加用户角色关系
                userRoleDao.insertUserRole(user.getUserId(),item.getRoleId());
            });
        }

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

    //修改
    @Override
    @Transactional
    public Result<User> updateUser(User user) {
        //重名判断
        User userTemp = userDao.getUserByUserName(user.getUserName());
        if (userTemp!=null && userTemp.getUserId()!=user.getUserId()){
            return new Result<User>(Result.ResultStatus.FAILD.status,"修改失败");
        }

        //修改User信息
        userDao.updateUser(user);

        //先清空当前用户角色关系信息--user_role
        userRoleDao.deleteUserRoleByUserId(user.getUserId());
        //获取用户选择的角色
        List<Role> roles = user.getRoles();
        if(roles!=null && !roles.isEmpty()){
            //遍历集合新写法
            roles.stream().forEach(item -> {
                //修改用户角色关系
                userRoleDao.insertUserRole(user.getUserId(),item.getRoleId());
            });
        }

        return new Result<User>(Result.ResultStatus.SUCCESS.status,"修改成功");
    }

    //删除
    @Override
    @Transactional
    public Result<Object> deleteUser(Integer userId) {
        userDao.deleteUser(userId);//删除user中的数据
        //删除user的角色信息，将中间表user_role中对应的数据删除
        userRoleDao.deleteUserRoleByUserId(userId);
        return new Result<>(Result.ResultStatus.SUCCESS.status,"删除成功");
    }

    //查询user中单条数据，并携带role的信息
    @Override
    public User getUserByUserId(Integer userId) {
        return userDao.getUserByUserId(userId);
    }
}
