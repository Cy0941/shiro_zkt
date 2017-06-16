import junit.framework.Assert;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.junit.Test;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/16 14:41 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class UserRealmTest extends BaseTest {

    @Test
    public void testLoginSuccess() {
        login("shiro.ini", u1.getUsername(), password);
        Assert.assertTrue(subject.isAuthenticated());
    }

    @Test(expected = UnknownAccountException.class)
    public void testLoginFailWithUnknownUsername() {
        login("shiro.ini", u1.getUsername() + "1", password);
    }

    @Test(expected = IncorrectCredentialsException.class)
    public void testLoginFailWithErrorPassword() {
        login("shiro.ini", u1.getUsername(), password + "1");
    }

    @Test(expected = LockedAccountException.class)
    public void testLoginFailWithLocked() {
        login("shiro.ini", u4.getUsername(), password);
    }

    @Test(expected = ExcessiveAttemptsException.class)
    public void testLoginFailWithLimitRetryCount() {
        for (int i = 1; i <= 5; i++) {
            try {
                login("shiro.ini", u3.getUsername(), password);
            } catch (ExcessiveAttemptsException e) {
                System.err.println("登录次数过多，请稍后再试");
            }
        }
        login("shiro.ini", u3.getUsername(), password);

        //需要清空缓存，否则后续的执行就会遇到问题(或者使用一个全新账户测试)
    }


    @Test
    public void testHasRole() {
        login("shiro.ini", u1.getUsername(), password);
        Assert.assertTrue(subject.hasRole("admin"));
    }

    @Test
    public void testNoRole() {
        login("shiro.ini", u2.getUsername(), password);
        Assert.assertFalse(subject.hasRole("admin"));
    }

    @Test
    public void testHasPermission() {
        login("shiro.ini", u1.getUsername(), password);
        Assert.assertTrue(subject.isPermittedAll("user:create", "menu:create"));
    }

    @Test
    public void testNoPermission() {
        login("shiro.ini", u2.getUsername(), password);
        Assert.assertFalse(subject.isPermitted("user:create"));
    }

}
