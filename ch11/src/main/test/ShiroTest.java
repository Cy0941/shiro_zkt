import cn.cxy.shiro.realm.UserRealm;
import cn.cxy.shiro.service.UserService;
import cn.cxy.shiro.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.junit.Test;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/20 19:02 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class ShiroTest extends BaseTest {

    private UserService userService = new UserServiceImpl();

    @Test
    public void testClearCacheAuthenticationInfo(){
        login("shiro.ini",u1.getUsername(),password);
        userService.changePassword(u1.getId(),password+"1");
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
        userRealm.clearCachedAuthenticationInfo(subject.getPrincipals());
        login("shiro.ini",u1.getUsername(),password+"1");
    }

    @Test
    public void testClearCachedAuthorizationInfo(){
        login("shiro.ini",u1.getUsername(),password);
        boolean hasRole = subject.hasRole(r1.getRole());
        System.err.println("-------------"+hasRole);
        userService.correlationRoles(u1.getId(),r2.getId());
        RealmSecurityManager securityManager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        UserRealm userRealm = (UserRealm)securityManager.getRealms().iterator().next();
        userRealm.clearCachedAuthenticationInfo(subject.getPrincipals());
        boolean b = subject.hasRole(r2.getRole());
        System.out.println("------------"+b);
    }

}
