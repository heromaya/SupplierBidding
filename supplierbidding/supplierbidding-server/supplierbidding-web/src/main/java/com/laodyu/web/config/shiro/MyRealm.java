package com.laodyu.web.config.shiro;

import com.laodyu.commons.constant.ENUserState;
import com.laodyu.entity.User;
import com.laodyu.service.MenuService;
import com.laodyu.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/4/28
 * @Version 1.0
 **/
public class MyRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    @Autowired
    MenuService menuService;
    /***
     * 重写获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前用户的所有权限
        String code = principalCollection.getPrimaryPrincipal().toString();
        Set<String> urlSet = menuService.getUrlSet(code);
        //将权限放入授权信息中
        SimpleAuthorizationInfo simple = new SimpleAuthorizationInfo();
        simple.setStringPermissions(urlSet);
        return simple;
    }

    /***
     * 获取认证信息，根据 token 中的用户名从数据库中获取密码、盐值
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String code = token.getPrincipal().toString();
        User user = userService.findByCode(code);
        String passwordInDB = user.getPassword();
        String salt = user.getSalt();
        String status = user.getStatus();
        if(status.equals(ENUserState.FORBIDDEN.getValue())){
            //被冻结的账户
            throw new LockedAccountException("账号被冻结！");
        }else if(status.equals(ENUserState.DISACTIVE.getValue())){
            //未激活的账户
            throw new DisabledAccountException("账号未激活！");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(code, passwordInDB, ByteSource.Util.bytes(salt), getName());
        return authenticationInfo;
    }
}
