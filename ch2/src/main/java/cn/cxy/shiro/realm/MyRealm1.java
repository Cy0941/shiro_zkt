package cn.cxy.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/14 10:08 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MyRealm1 implements Realm {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 返回一个唯一的当前Realm名字
     * @return
     */
    public String getName() {
        return "myRealm1";
    }

    /**
     * 判断当前Realm是否支持目标token
     * @param token
     * @return
     */
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[])token.getCredentials());
        logger.warn("-----myRealm1----getAuthenticationInfo----------");
        if (!"zhang".equals(username)){
            throw new UnknownAccountException("unknown account");
        }
        if (!"123".equals(password)){
            throw new IncorrectCredentialsException("incorrect password");
        }
        //验证成功返回一个 AuthenticationInfo 实现
        return new SimpleAuthenticationInfo(username,password,getName());
    }
}
