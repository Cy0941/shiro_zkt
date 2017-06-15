import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.After;
import org.junit.Test;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/15 18:19 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class PasswordTest {

    private Subject subject;

    @Test
    public void testHashedCredentialsMatcherWithMyRealm2() {
        //使用testGeneratePassword生成的散列密码
        login("shiro-hashedCredentialsMatcher.ini", "liu", "123");
    }

    @Test
    public void testPasswordServiceWithMyRealm() {
        login("shiro-passwordservice.ini", "wu", "123");
    }

    @Test
    public void testPasswordServiceWithJdbcRealm() {
        login("shiro-jdbc-passwordservice.ini", "wu", "123");
    }

    public void login(String configFile, String username, String password) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:" + configFile);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

    @After
    public void logout() {
        subject.logout();
    }

}
