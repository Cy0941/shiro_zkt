package cn.cxy.shiro.credentials;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Function: 继承自 HashedCredentialsMatcher 并且利用Ehcache（记录重试次数和超时时间）实现密码重试超时设置
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/16 10:40 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private static Ehcache passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher() {
        CacheManager manager = CacheManager.newInstance(CacheManager.class.getClassLoader().getResource("password-ehcache.xml"));
        passwordRetryCache = manager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        //cxy retry count+1
        Element element = passwordRetryCache.get(username);
        if (null == element) {
            element = new Element(username, new AtomicInteger(0));
            passwordRetryCache.put(element);
        }
        AtomicInteger retryCount = (AtomicInteger) element.getObjectValue();
        if (retryCount.incrementAndGet() > 5) {//超过5次抛出异常
            throw new ExcessiveAttemptsException("failed too much times,try it later");
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
