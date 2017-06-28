package cn.cxy.shiro.ch16.web.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/26 23:52 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String showLoginForm(HttpServletRequest request, Model model) {
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        String error ;
        if (UnknownAccountException.class.getName().equalsIgnoreCase(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (IncorrectCredentialsException.class.getName().equalsIgnoreCase(exceptionClassName)) {
            error = "用户名/密码错误";
        } else {
            error = "其他错误:" + exceptionClassName;
        }
        System.out.println("-----------login------------");
        model.addAttribute("error",error);
        return "login";
    }

}
