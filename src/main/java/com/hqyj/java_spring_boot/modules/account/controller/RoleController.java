package com.hqyj.java_spring_boot.modules.account.controller;

import com.hqyj.java_spring_boot.modules.account.entity.Role;
import com.hqyj.java_spring_boot.modules.account.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询所有Role信息
     * localhost/api/roles ---- GET
     */
    @GetMapping("/roles")
    public List<Role> getRoles(){
        return roleService.getRoles();
    }
}
