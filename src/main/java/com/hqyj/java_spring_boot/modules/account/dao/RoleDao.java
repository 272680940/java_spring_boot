package com.hqyj.java_spring_boot.modules.account.dao;

import com.hqyj.java_spring_boot.modules.account.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RoleDao {
    //通过userId查询角色信息
    @Select("SELECT * FROM role r " +
            "Left JOIN user_role ur ON r.role_id=ur.role_id " +
            "where user_id = #{userId}")
    List<Role> getRolesByUserId(Integer userId);

    //查询所有的角色信息
    @Select("select * from role")
    List<Role> getRoles();
}
