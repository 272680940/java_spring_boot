package com.hqyj.java_spring_boot.modules.account.service;

import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.account.entity.Role;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;

import java.util.List;

public interface RoleService {
    //查询所有的角色信息
    List<Role> getRoles();

    //脚本式多条件查询
    PageInfo<Role> getRolesBySearchVo(SearchVo searchVo);

    //修改
    Result<Role> updateRole(Role role);

    //删除
    Result<Object> deleteRole(Integer roleId);

    //添加
    Result<Role> insertRole(Role role);

    //通过RoleId查询
    Role getRoleByRoleId(Integer roleId);
}
