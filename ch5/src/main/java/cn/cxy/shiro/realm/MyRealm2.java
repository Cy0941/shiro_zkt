package cn.cxy.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/15 19:04 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MyRealm2 extends AuthorizingRealm {
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //cxy 加密算法设置 - shiro.ini
        String username = "yang";
        String password = "8b192c7c48377b4e6cf31de6ffd5db45";
        String salt2 = "9c26206f1f0440a9aeb2e37b0a1e08c6";
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(username+salt2));
        return info;
    }
}
