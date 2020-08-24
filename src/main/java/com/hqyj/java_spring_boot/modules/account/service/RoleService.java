package com.hqyj.java_spring_boot.modules.account.service;

import com.hqyj.java_spring_boot.modules.account.entity.Role;

import java.util.List;

public interface RoleService {
    //查询所有的角色信息
    List<Role> getRoles();
}
