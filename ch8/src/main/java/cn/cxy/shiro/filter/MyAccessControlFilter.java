package cn.cxy.shiro.filter;

import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Function: TODO
 * Reason: AccessControlFilter提供了访问控制的基础功能；比如是否允许访问/当访问拒绝时如何处理等
 *      isAccessAllowed：表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
 *      onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
 * Date: 2017/6/17 14:27 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MyAccessControlFilter extends AccessControlFilter {

    /**
     * cxy mappedValue 就是 ini 文件中拦截器参数部分
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        System.out.println("AccessControlFilter======access allowed");
        return false;
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("AccessControlFilter======访问拒绝；返回false - 表示当前过滤器已经处理了，不再到达下一个过滤器；返回 true - 当前拦截器不处理且继续拦截器链执行");
        return false;
    }
}
