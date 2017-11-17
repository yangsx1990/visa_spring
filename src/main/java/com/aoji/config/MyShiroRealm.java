package com.aoji.config;

import com.aoji.model.SysUser;
import com.aoji.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午5:14 2017/11/16
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    /**
     * 认证、登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken userToken=(UsernamePasswordToken)authenticationToken;
        String username=userToken.getUsername();
        SysUser user=userService.getUserByName(username);
        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
    }


    /**
     * 授权 TODO
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user=(SysUser)principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        //查询用户的角色
        //根据角色查询权限
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        Set<String> roles=new HashSet<>();
        roles.add("admin");
        Set<String> permissions=new HashSet<>();
        permissions.add("学生管理");
        permissions.add("顾问管理");
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }
}
