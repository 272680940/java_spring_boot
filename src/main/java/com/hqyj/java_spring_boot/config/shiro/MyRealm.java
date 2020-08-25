package com.hqyj.java_spring_boot.config.shiro;

import com.hqyj.java_spring_boot.modules.account.entity.Resource;
import com.hqyj.java_spring_boot.modules.account.entity.Role;
import com.hqyj.java_spring_boot.modules.account.entity.User;
import com.hqyj.java_spring_boot.modules.account.service.ResourceService;
import com.hqyj.java_spring_boot.modules.account.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * shiro核心组件之一：realm。继承 AuthorizingRealm 类，重写身份验证和资源授权两个方法
 */
@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;


    /**
     * * 资源授权，包装资源授权验证器，将当前用户对应的角色信息，以及角色所拥有的资源信息放入验证器
     * * 通过前端页面shiro标签、控制器方法shiro注解与验证器比对，实现用户资源授权功能
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User user = (User)principalCollection.getPrimaryPrincipal();
        List<Role> roles = user.getRoles();
        if (roles!=null && !roles.isEmpty()){
            roles.stream().forEach(item->{
                simpleAuthorizationInfo.addRole(item.getRoleName());
                List<Resource> resources = resourceService.getResourcesByRoleId(item.getRoleId());
                if (resources!=null && !resources.isEmpty()){
                    resources.stream().forEach(resource->{
                        simpleAuthorizationInfo.addStringPermission(resource.getPermission());
                    });
                }
            });
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 包装身份验证器，将数据库用户名和密码放入验证器
     * * 通过登录框输入的用户名和密码与验证器做比对，实现身份验证功能
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        User user = userService.getUserByUserName(userName);
        if (user==null){
            throw new UnknownAccountException("The account do not exist!");
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
    }
}
