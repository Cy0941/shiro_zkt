package cn.cxy.shiro.filter;

import org.apache.shiro.web.servlet.AdviceFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Function: TODO
 * Reason: AdviceFilter提供了AOP风格的支持
 *    preHandler：类似于AOP中的前置增强；在拦截器链执行之前执行；如果返回true则继续拦截器链；否则中断后续的拦截器链的执行直接返回；进行预处理（如基于表单的身份验证、授权）
 *    postHandle：类似于AOP中的后置返回增强；在拦截器链执行完成后执行；进行后处理（如记录执行时间之类的）；
 *    afterCompletion：类似于AOP中的后置最终增强；即不管有没有异常都会执行；可以进行清理资源（如接触Subject与线程的绑定之类的）；
 * Date: 2017/6/17 14:11 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MyAdviceFilter extends AdviceFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("AdviceFilter=====预处理/前置处理---返回true，则继续后续拦截器链的处理，否则结束");
        return true;//返回false将中断后续拦截器链的执行
    }

    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("AdviceFilter========后处理/后置返回处理---进行后处理，如记录时间等");
    }

    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        System.out.println("AdviceFilter========完成处理/后置最终处理---不管有没有异常都会执行---清理资源等");
    }
}
