package com.xin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.controller
 * @Author: LI Renxin
 * @CreateTime: 2021-07-15 17:29
 * @ModificationHistory Who    When    What
 * @Description: hello
 */
@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(){
        System.out.println("hello world");
        return "hello";
    }
}
