package cn.cxy.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Function: shiro 提供roles拦截器，其验证用户拥有所有角色，没有提供验证拥有拥有任意角色的拦截器
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/17 15:18 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class AnyRolesFilter extends AccessControlFilter {

    private String unAuthorizedUrl = "/unauthorized.jsp";
    private String loginUrl = "/login.jsp";

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String[] roles = (String[]) mappedValue;
        if (null == roles) {
            //如果没有设置角色参数，默认成功
            return true;
        }
        for (String role : roles) {
            if (getSubject(request, response).hasRole(role)) {
                return true;
            }
        }
        //返回 false 跳到 onAccessDenied 处理
        return false;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        if (null == subject.getPrincipal()) {//cxy 表示没有登录
            saveRequest(request);
            WebUtils.issueRedirect(request, response, loginUrl);
        } else {
            if (StringUtils.hasText(unAuthorizedUrl)) {//cxy 有未授权的页面
                WebUtils.issueRedirect(request, response, unAuthorizedUrl);
            } else {
                //返回 401 未授权状态码
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }
}
