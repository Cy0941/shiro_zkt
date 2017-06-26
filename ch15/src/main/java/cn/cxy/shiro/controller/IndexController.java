package cn.cxy.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-12
 * <p>Version: 1.0
 */
@Controller
public class IndexController {

    @RequestMapping("/hello1")
    public String hello1() {
        SecurityUtils.getSubject().checkRole("admin");
        return "success";
    }

    @RequestMapping("/success")
    public String hello2() {
        return "success";
    }
}
