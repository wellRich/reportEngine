package cn.guoyka.simplermybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 1.0, 2018/4/30
 */
@Controller
@RequestMapping({"/redis/home", "/"})
public class BaseController {

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String home(){
        System.out.println("come in 99999");
        return "views/index";
    }


}
