package com.lanxin.realm;

import com.lanxin.dao.IusersDao;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2019/9/26 0026.
 */
public class MyCustomRealm extends AuthorizingRealm {

    @Autowired
    private IusersDao iusersDao;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username=String.valueOf(principalCollection.getPrimaryPrincipal());

        List<String> roles=iusersDao.selectRolesByUsername(username);

        List<String> perm=iusersDao.selectPermsByUsername(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(perm);
        return simpleAuthorizationInfo;
    }

    //验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

       String username=String.valueOf(authenticationToken.getPrincipal());

        String pass=iusersDao.selectPassByUsername(username);

        if(pass!=null){

            return new SimpleAuthenticationInfo(username,pass, ByteSource.Util.bytes(username),"cusomRealm");
        }
        return null;
    }
}
