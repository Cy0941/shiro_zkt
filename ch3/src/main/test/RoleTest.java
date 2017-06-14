import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/14 17:55 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class RoleTest {

    private Subject subject;

    /**
     * checkPermission()/checkPermissions()  --  没有对应权限则抛出异常
     * isPermitted()/isPermittedAll()  --  没有对应权限不抛出异常
     */
    @Test(expected = UnauthorizedException.class)
    public void testCheckPermission () {
        login("shiro-permission.ini", "zhang", "123");
        //断言拥有权限：user:create
        subject.checkPermission("user:create");
        //断言拥有权限：user:delete and user:update
        subject.checkPermissions("user:delete", "user:update");
        //断言拥有权限：user:view 失败抛出异常
        subject.checkPermissions("user:view");
    }

    @Test
    public void testIsPermitted(){
        //fixme 何时加载对应的角色权限信息？？？  --  读取配置文件初始化
        login("shiro-permission.ini", "zhang", "123");
        //判断拥有权限 user:create
        Assert.assertTrue(subject.isPermitted("user:create"));
        //判断拥有权限集合
        Assert.assertTrue(subject.isPermittedAll("user:update","user:delete"));
        //判断没有权限
        Assert.assertFalse(subject.isPermitted("user:view"));
    }

    /**
     * checkRole()/checkRoles()  --  没有对应角色则抛出异常
     * hasRole()/hasRoles()  --  没有对应角色不抛出异常
     */
    @Test(expected = UnauthorizedException.class)
    public void testCheckRole() {
        login("shiro-role.ini", "zhang", "123");
        //断言拥有角色 role1
        subject.checkRole("role1");
        subject.checkRoles("role1", "role3");
    }

    @Test
    public void testHasRole() {
        login("shiro-role.ini", "zhang", "123");
        //判断拥有角色：role1
        Assert.assertTrue(subject.hasRole("role1"));
        //判断拥有角色：role1 and role2
        Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1", "role2")));
        //判断拥有角色：role1 and role2 and !role3
        boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));
        System.err.println(Arrays.toString(result));
        Assert.assertEquals(true, result[0]);
        Assert.assertEquals(true, result[1]);
        Assert.assertEquals(false, result[2]);
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
