package cn.cxy.shiro.ch16.web.controller;

import cn.cxy.shiro.ch16.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Function: TODO
 * Reason: TODO ADD REASON(可选).</br>
 * Date: 2017/6/27 17:14 </br>
 *
 * @author: cx.yang
 * @since: Thinkingbar Web Project 1.0
 */
@Controller
@RequestMapping(value = "/resource/")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "list",method = RequestMethod.POST)
    public String list(Model model){
        model.addAttribute("resourceList",resourceService.findAll());
        return "resource/list";
    }

}
