package com.hqyj.java_spring_boot.modules.account.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserRoleDao {

    //删除user_role的关系数据
    @Delete("delete from user_role where user_id = #{userId}")
    void deleteUserRoleByUserId(Integer userId);

    //添加
    @Insert("insert into user_role(user_id,role_id) values(#{userId},#{roleId})")
    void insertUserRole(int userId, int roleId);
}
