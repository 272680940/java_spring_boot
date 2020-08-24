package com.hqyj.java_spring_boot.modules.account.controller;

import com.github.pagehelper.PageInfo;
import com.hqyj.java_spring_boot.modules.account.entity.Role;
import com.hqyj.java_spring_boot.modules.account.service.RoleService;
import com.hqyj.java_spring_boot.modules.common.vo.Result;
import com.hqyj.java_spring_boot.modules.common.vo.SearchVo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 脚本式分页、查询
     * localhost/api/roles ---- POST
     * {"currentPage":"1","pageSize":"5","keyWord":"ad"}
     */
    @PostMapping(value = "roles",consumes = "application/json")
    public PageInfo<Role> getRolesBySearchVo(@RequestBody SearchVo searchVo){
        return roleService.getRolesBySearchVo(searchVo);
    }

    /**
     * 修改Role
     * {"roleName":"admin1","roleId":"1"}
     * localhost/api/role --- PUT
     */
    @PutMapping(value = "role",consumes = "application/json")
    public Result<Role> updateRole(@RequestBody Role role){
        return roleService.updateRole(role);
    }

    /**
     * 删除Role
     * localhost/api/role/4 ---- DELETE
     */
    @DeleteMapping("/role/{roleId}")
    public Result<Object> deleteRole(@PathVariable Integer roleId){
        return roleService.deleteRole(roleId);
    }

    /**
     * 添加Role
     * {"roleName":"sssss"}
     * localhost/api/role --- POST
     */
    @PostMapping(value = "role",consumes = "application/json")
    public Result<Role> insertRole(@RequestBody Role role){
        return roleService.insertRole(role);
    }

    /**
     * 通过roleId查询
     * localhost/api/role/1 ---- GET
     */
    @GetMapping("/role/{roleId}")
    public Role getRoleByRoleId(@PathVariable Integer roleId){
        return roleService.getRoleByRoleId(roleId);
    }
}
