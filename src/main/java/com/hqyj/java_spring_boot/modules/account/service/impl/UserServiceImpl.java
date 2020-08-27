package com.hqyj.java_spring_boot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.config.ResourceConfigBean;
import com.hqyj.java_spring_boot.modules.account.dao.UserDao;
import com.hqyj.java_spring_boot.modules.account.dao.UserRoleDao;
import com.hqyj.java_spring_boot.modules.account.entity.Role;
import com.hqyj.java_spring_boot.modules.account.entity.User;
import com.hqyj.java_spring_boot.modules.account.service.UserService;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import com.hqyj.java_spring_boot.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
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
        // shiro
        Subject subject = SecurityUtils.getSubject();

        //令牌token封装了用户名和密码
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName()
                ,MD5Utils.getMD5(user.getPassword()));
        token.setRememberMe(user.getRememberMe());
        try{
            subject.login(token);
            subject.checkRoles();
        }catch (Exception e){
            e.printStackTrace();
            return new Result<>(Result.ResultStatus.FAILD.status,"login faild");
        }

        //将登陆成功后的用户信息存储到 Session 中
        Session session = subject.getSession();
        session.setAttribute("user",(User)subject.getPrincipal());


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


    @Autowired
    private ResourceConfigBean resourceConfigBean;
    /**
     * 上传图片
     * 泛型 String 表示上传成功后,图片的相对路径的地址
     */
    @Override
    public Result<String> uploadUserImg(MultipartFile file) {
        //判断是否上传文件
        if (file.isEmpty()){
            //没有文件上传提示信息
            return new Result<String>(Result.ResultStatus.FAILD.status,
                    "please select img");
        }
        //上传成功后保存图片的相对路径
        String relativePath = "";
        //目标文件路径（本地硬盘上绝对路径）
        String descFilePath = "";
        try {
            String systemName = System.getProperty("os.name");
            if (systemName.toLowerCase().startsWith("win")){
                descFilePath = resourceConfigBean.getLocationPathForWindows()
                        +file.getOriginalFilename();
            }else{
                descFilePath = resourceConfigBean.getLocationPathForLinux()
                        +file.getOriginalFilename();
            }
            relativePath = resourceConfigBean.getRelativePath()
                    +file.getOriginalFilename();
            //目标文件
            File descFile = new File(descFilePath);
            //上传文件
            file.transferTo(descFile);
        } catch (IOException e) {
            e.printStackTrace();
            //出现异常，上传失败提示信息
            return new Result<String>(Result.ResultStatus.FAILD.status,"upload failed");
        }
        //上传成功后,将相对路径返回,页面拿到相对路径后设置到隐藏的<input>里面,
        //点击模态框保存按钮,将相对路径保存到数据库里
        return new Result<String>(Result.ResultStatus.SUCCESS.status,"upload success",relativePath);
    }

    @Override
    @Transactional
    public Result<User> updateUserProfile(User user) {
        User userTemp = userDao.getUserByUserName(user.getUserName());
        if (userTemp != null && userTemp.getUserId() != user.getUserId()) {
            return new Result<User>(Result.ResultStatus.FAILD.status, "User name is repeat.");
        }
        userDao.updateUser(user);
        return new Result<User>(Result.ResultStatus.SUCCESS.status, "Edit success.", user);
    }

    //通过userName获取User
    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    //退出
    @Override
    public void logout() {
        //退出
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        //将Session置空
        Session session = subject.getSession();
        session.setAttribute("user",null);
    }
}
