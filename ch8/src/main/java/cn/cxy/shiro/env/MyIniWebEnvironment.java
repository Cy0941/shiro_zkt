package cn.cxy.shiro.env;

import org.apache.shiro.util.ClassUtils;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

import javax.servlet.Filter;

/**
 * Function: 根据 web.xml 中配置 org.apache.shiro.web.env.EnvironmentLoaderListener 追溯初始化 WebEnvironment 及对应的 FilterChainManager
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/17 11:56 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class MyIniWebEnvironment extends IniWebEnvironment {

    @Override
    protected FilterChainResolver createFilterChainResolver() {
        //1、创建 FilterChainResolver
        PathMatchingFilterChainResolver filterChainResolver = new PathMatchingFilterChainResolver();
        //2、创建 FilterChainManager
        DefaultFilterChainManager filterChainManager = new DefaultFilterChainManager();
        //3、注册 Filter
        for (DefaultFilter filter : DefaultFilter.values()) {
            filterChainManager.addFilter(filter.name(), (Filter) ClassUtils.newInstance(filter.getFilterClass()));
        }
        //4、注册 URL-Filter 的映射关系
        filterChainManager.addToChain("/login.jsp","authc");
        filterChainManager.addToChain("/unauthorized.jsp","anon");
        filterChainManager.addToChain("/**","authc");
        filterChainManager.addToChain("/**","roles","admin");
        //5、设置 Filter 属性
        FormAuthenticationFilter authcFilter = (FormAuthenticationFilter)filterChainManager.getFilter("authc");
        authcFilter.setLoginUrl("/login.jsp");
        RolesAuthorizationFilter rolesFilter = (RolesAuthorizationFilter)filterChainManager.getFilter("roles");
        rolesFilter.setUnauthorizedUrl("/unauthorized.jsp");
        filterChainResolver.setFilterChainManager(filterChainManager);
        return filterChainResolver;
    }
}
