package com.xin.controller;

import com.xin.exception.MyException01;
import org.apache.tools.ant.taskdefs.Java;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.controller
 * @Author: LI Renxin
 * @CreateTime: 2021-07-15 11:17
 * @ModificationHistory Who    When    What
 * @Description: 异常处理测试
 */
@Controller
@RequestMapping("/e")
public class ExceptionHandlerController {

    @RequestMapping("/hello01")
    public String testException(@RequestParam("i") int i) {
        System.out.println("testException///");
        System.out.println(10 / i);
        return "hello";
    }

    @RequestMapping("/hello02")
    public String testException2(@RequestParam("i") int i) {
        System.out.println("testException///");
        if (i==0) {
            throw new MyException01();
        }
        return "hello";
    }
}
