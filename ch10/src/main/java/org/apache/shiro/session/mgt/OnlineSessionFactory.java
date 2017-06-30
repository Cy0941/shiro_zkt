package org.apache.shiro.session.mgt;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.mgt.WebSessionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Function: 自定义session创建工厂
 * 添加一些已定义的数据
 * 如 用户登录到系统的IP
 * 用户状态（在线 隐身 强制退出）
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/29 19:04 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class OnlineSessionFactory implements SessionFactory {

    public Session createSession(SessionContext initData) {
        OnlineSession onlineSession = new OnlineSession();
        if (null != initData && initData instanceof WebSessionContext) {
            WebSessionContext sessionContext = (WebSessionContext) initData;
            //cxy HttpServletRequest
            HttpServletRequest servletRequest = (HttpServletRequest) sessionContext.getServletRequest();
            if (null != servletRequest) {
                onlineSession.setHost(IpUtils.getIpAddr(servletRequest));
                onlineSession.setUserAgent(servletRequest.getHeader("User-Agent"));
                onlineSession.setSystemHost(servletRequest.getLocalAddr() + " : " + servletRequest.getLocalPort());
            }
        }
        return onlineSession;
    }
}
