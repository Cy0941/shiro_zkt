package cn.cxy.shiro.ch18.kickout;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/7/3 22:48 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
@Setter
@Getter
public class KickOutSessionFilter extends AccessControlFilter {

    private String kickOutUrl;//踢出后转到该地址
    private boolean kickOutAfter = false;//踢出之前登录/之后登录的用户 默认之前
    private int maxSession = 1;//同一账号大会话数

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            //没有登录或记住我登录，直接进入下一个filter
            return true;
        }
        Session session = subject.getSession();
        String username = (String) subject.getPrincipal();
        Serializable sessionId = session.getId();
        Deque<Serializable> deque = cache.get(username);
        if (null == deque) {
            deque = new LinkedList<Serializable>();
            cache.put(username, deque);
        }
        //如果队列里面没有此 sessionID，切用户没有被踢出，放入队列
        if (!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
            deque.push(sessionId);
        }
        return false;
    }
}
