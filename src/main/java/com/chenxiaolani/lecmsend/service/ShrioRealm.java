package com.chenxiaolani.lecmsend.service;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShrioRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    public ShrioRealm() {
        this.setCredentialsMatcher((token, info) -> token.getCredentials().equals(info.getCredentials()));
    }

    // 是否有权限访问这个资源
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // 校验此用户是不是你说的自己
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 需要告诉shrio token 和正确的密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String password = userService.getUserInfoByUsername(token.getUsername()).getPassword();
        // 获取真实密码
        return new SimpleAuthenticationInfo(token.getUsername(), password, getName());
    }
}