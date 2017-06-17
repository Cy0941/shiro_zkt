package cn.cxy.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Function: java 代码实现 shiro-formfilterlogin.ini 中的登录拦截器
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/17 14:39 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class FormLoginFilter extends PathMatchingFilter {

    private String loginUrl = "/login.jsp";
    private String successUrl = "/";
    private static final String POST = "post";

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            //已经登录过 - 继续过滤器链执行
            return true;
        }
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        //确认为登录请求
        if (isLoginRequest(servletRequest)) {
            //post 请求
            if (POST.equalsIgnoreCase(servletRequest.getMethod())) {
                boolean loginSuccess = login(servletRequest);
                if (loginSuccess) {
                    //登录成功后转发到成功URL
                    return redirectToSuccessUrl(servletRequest, servletResponse);
                }else {
                    saveRequestAndRedirectToLogin(servletRequest, servletResponse);
                }
            }
            //转发到其他过滤器链
            return true;
        } else {
            saveRequestAndRedirectToLogin(servletRequest, servletResponse);
            return false;
        }
    }

    private void saveRequestAndRedirectToLogin(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException {
        WebUtils.saveRequest(servletRequest);
        WebUtils.issueRedirect(servletRequest, servletResponse, loginUrl);
    }

    private boolean redirectToSuccessUrl(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException {
        WebUtils.redirectToSavedRequest(servletRequest, servletResponse, successUrl);
        return false;
    }

    private boolean login(HttpServletRequest servletRequest) {
        String username = servletRequest.getParameter("username");
        String password = servletRequest.getParameter("password");
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password));
        } catch (AuthenticationException e) {
            servletRequest.setAttribute("shiroLoginFailure", e.getClass());
            return false;
        }
        return true;
    }


    private boolean isLoginRequest(HttpServletRequest request) {
        return pathsMatch(loginUrl, WebUtils.getPathWithinApplication(request));
    }
}
