package cn.cxy.shiro.ch16.web.controller;

import cn.cxy.shiro.ch16.annotation.CurrentUser;
import cn.cxy.shiro.ch16.entity.Resource;
import cn.cxy.shiro.ch16.entity.User;
import cn.cxy.shiro.ch16.service.ResourceService;
import cn.cxy.shiro.ch16.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/26 23:47 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */

@Controller
public class IndexController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(@CurrentUser User user, Model model){
        Set<String> permissions = userService.findPermissions(user.getUsername());
        List<Resource> menus = resourceService.findMenus(permissions);
        model.addAttribute("menus",menus);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

}
