package cn.cxy.shiro.filter;

import org.apache.shiro.web.servlet.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Function: TODO
 * Reason: OncePerRequestFilter用于防止多次执行Filter的；也就是说一次请求只会走一次拦截器链；另外提供enabled属性，表示是否开启该拦截器实例，默认enabled=true表示开启，如果不想让某个拦截器工作，可以设置为false即可
 * Date: 2017/6/17 14:02 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MyOncePerRequestFilter extends OncePerRequestFilter {

    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        /**
         * true - 所有请求都需要经过此过滤器
         * false - 请求仅需经过此过滤器一次，以后都不再过滤
         */
        this.setEnabled(true);
        System.out.println("OncePerRequestFilter===========once per request filter");
        chain.doFilter(request,response);
    }
}
