package com.chenxiaolani.lecmsend.service;

import com.chenxiaolani.lecmsend.entity.Role;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class ShrioRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    private RoleService roleService;

    public ShrioRealm() {
        this.setCredentialsMatcher((token, info) -> new String((char[]) token.getCredentials()).equals(info.getCredentials()));
    }

    // 权限认证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        String username = (String) principalCollection.getPrimaryPrincipal();
        // 角色
        Set<String> roles = new HashSet<String>();
        Role role = roleService.getRoleByUserName(username);
//        roles.add(role.getRoleName());
//        authorizationInfo.setRoles(roles);

        return authInfo;
    }

    // 身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 需要告诉shrio token 和正确的密码
        String username = (String) authenticationToken.getPrincipal();
        String password = userService.getUserInfoByUsername(username).getPassword();
        // 获取真实密码
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}