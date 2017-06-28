package cn.cxy.shiro.ch16.web.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * Function: 异常处理器
 * Reason: TODO 如果抛出UnauthorizedException，将被该异常处理器截获来显示没有权限信息
 * Date: 2017/6/27 18:28 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
public class DefaultExceptionHandler {

    /**
     * 未授权异常
     * @param request
     * @param e
     * @return
     * cxy 代替 org.apache.shiro.spring.web.ShiroFilterFactoryBean[shiroFilter] 配置中的unauthorizedUrl  ？？？
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);
        modelAndView.setViewName("unauthorized");
        return modelAndView;
    }

}
