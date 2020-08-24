package com.hqyj.java_spring_boot.modules.account.dao;

import com.hqyj.java_spring_boot.modules.account.entity.Role;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import org.apache.ibatis.annotations.*;
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

    //脚本式多条件查询
    @Select("<script>" +
            "select * from role "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (role_name like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='orderBy != \"\" and orderBy != null'>"
            + " order by ${orderBy} ${sort}"
            + "</when>"
            + "<otherwise>"
            + " order by role_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Role> getRolesBySearchVo(SearchVo searchVo);

    //修改
    @Update("update role set role_name = #{roleName} where role_id = #{roleId}")
    void updateRole(Role role);

    //删除
    @Delete("delete from role where role_id = #{roleId}")
    void deleteRole(Integer roleId);

    //添加
    @Insert("insert into role(role_name) values (#{roleName})")
    @Options(useGeneratedKeys = true,keyColumn = "role_id",keyProperty = "roleId")
    void insertRole(Role role);

    //通过roleId查询
    @Select("select * from role where role_id = #{roleId}")
    Role getRoleByRoleId(Integer roleId);

}
