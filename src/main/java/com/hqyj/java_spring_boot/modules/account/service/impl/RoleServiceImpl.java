package com.hqyj.java_spring_boot.modules.account.service.impl;

import com.hqyj.java_spring_boot.modules.account.dao.RoleDao;
import com.hqyj.java_spring_boot.modules.account.entity.Role;
import com.hqyj.java_spring_boot.modules.account.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    //查询所有的角色信息
    @Override
    public List<Role> getRoles() {
        return Optional.ofNullable(roleDao.getRoles())
                .orElse(Collections.emptyList());
    }
}
