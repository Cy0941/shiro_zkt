package cn.cxy.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/13 17:10 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class LoginLogoutTest {

    @Test
    public void loginLogoutTest(){
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(true, subject.isAuthenticated());
        subject.logout();
    }

}
