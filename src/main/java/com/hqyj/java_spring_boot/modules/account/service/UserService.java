package com.hqyj.java_spring_boot.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.account.entity.User;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    //注册用户
    Result<User> insertUser(User user);

    //登录
    Result<User> getUserByUserNameAndPassword(User user);

    //脚本式多条件分页\查询
    PageInfo<User> getUsersBySearchVo(SearchVo searchVo);

    //修改
    Result<User> updateUser(User user);

    //删除
    Result<Object> deleteUser(Integer userId);

    //查询user中单条数据，并携带role的信息
    User getUserByUserId(Integer userId);

    //上传图片
    Result<String> uploadUserImg(MultipartFile file);

    Result<User> updateUserProfile(User user);
}
