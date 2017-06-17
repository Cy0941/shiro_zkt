package cn.cxy.shiro.filter;

import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Arrays;

/**
 * Function: TODO
 * Reason: PathMatchingFilter提供了基于Ant风格的请求路径匹配功能及拦截器参数解析的功能，如“roles[admin,user]”自动根据“，”分割解析到一个路径参数配置并绑定到相应的路径
 *      pathsMatch：该方法用于path与请求路径进行匹配的方法；如果匹配返回true；
 *      onPreHandle：在preHandle中，当pathsMatch匹配一个路径后，会调用opPreHandler方法并将路径绑定参数配置传给mappedValue；然后可以在这个方法中进行一些验证（如角色授权），如果验证失败可以返回false中断流程；默认返回true；也就是说子类可以只实现onPreHandle即可，无须实现preHandle。如果没有path与请求路径匹配，默认是通过的（即preHandle返回true）
 * Date: 2017/6/17 14:16 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MyPathMatchingFilter extends PathMatchingFilter {

    /**
     * cxy mappedValue 就是 shiro.ini 文件中 [urls] 部分调用拦截器的参数部分
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String[] mappedValueArray = (String[]) mappedValue;
        System.out.println("PathMatchingFilter====url matches,config is " + Arrays.toString(mappedValueArray));
        return true;
    }
}
