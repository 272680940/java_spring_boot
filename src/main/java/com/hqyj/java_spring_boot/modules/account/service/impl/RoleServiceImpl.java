package com.hqyj.java_spring_boot.modules.account.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.account.dao.RoleDao;
import com.hqyj.java_spring_boot.modules.account.entity.Role;
import com.hqyj.java_spring_boot.modules.account.service.RoleService;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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

    //脚本式多条件查询
    @Override
    public PageInfo<Role> getRolesBySearchVo(SearchVo searchVo) {
        //初始化Search
        searchVo.initSearchVo();
        //分页
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getPageSize());
        return new PageInfo<Role>(
                Optional.ofNullable(roleDao.getRolesBySearchVo(searchVo))
                        .orElse(Collections.emptyList())
        );
    }

    //修改
    @Override
    @Transactional
    public Result<Role> updateRole(Role role) {
        roleDao.updateRole(role);
        return new Result<>(Result.ResultStatus.SUCCESS.status,"修改成功");
    }

    //删除
    @Override
    @Transactional
    public Result<Object> deleteRole(Integer roleId) {
        roleDao.deleteRole(roleId);
        return new Result<>(Result.ResultStatus.SUCCESS.status,"删除成功");
    }

    //添加
    @Override
    @Transactional
    public Result<Role> insertRole(Role role) {
        roleDao.insertRole(role);
        return new Result<>(Result.ResultStatus.SUCCESS.status,"添加成功",role);
    }

    //通过RoleId查询
    @Override
    public Role getRoleByRoleId(Integer roleId) {
        return  roleDao.getRoleByRoleId(roleId);
    }
}
