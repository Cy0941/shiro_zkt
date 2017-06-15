package cn.cxy.shiro.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Function: 纯 java 模拟 shiro.ini 配置根对象 SecurityManager
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/15 15:22 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class NonConfigurationCreateTest {

    @Test
    public void test() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //设置 authenticator - 默认AtLeastOneSuccessfulStrategy
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        securityManager.setAuthenticator(authenticator);
        //设置 authorizer - 默认ModularRealmAuthorizer
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthorizer(authorizer);
        //设置 realm
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        JdbcRealm realm = new JdbcRealm();
        realm.setDataSource(dataSource);
        //cxy 数据库中查询角色时是否关联查询对应权限【默认为 false】
        realm.setPermissionsLookupEnabled(true);
        securityManager.setRealm((Realm) Arrays.asList((realm)));

        //TODO 将 SecurityManager 设置到 SecurityUtils 方便全局使用
        SecurityUtils.setSecurityManager(securityManager);

        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
            subject.login(token);
            Assert.assertTrue(subject.isAuthenticated());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
