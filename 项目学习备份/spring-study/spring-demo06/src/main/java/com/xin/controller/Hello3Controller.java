package com.xin.controller;

import com.xin.bean.Car;
import com.xin.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.controller
 * @Author: LI Renxin
 * @CreateTime: 2021-07-11 15:38
 * @ModificationHistory Who    When    What
 * @Description: 控制器
 */
@Controller
@RequestMapping("/h3")
public class Hello3Controller {

    @RequestMapping("/hello01")
    public String hello01(){
        System.out.println("/h3/hello01");
        return "../../hello";
    }

    @RequestMapping("/hello02")
    public String hello02(){
        return "forward:/hello.jsp";
    }

    @RequestMapping("/hello03")
    public String hello03(){
        System.out.println("/h3/hello03");
        return "forward:/h3/hello01";
    }

    @RequestMapping("/hello04")
    public String hello04(){
        return "redirect:/hello.jsp";
    }
    @RequestMapping("/hello05")
    public String hello05(){
        return "redirect:/hello";
    }

    @RequestMapping("/hello06")
    public String hello06(@RequestParam("carList") Car car){
        System.out.println(car);
        return "redirect:/hello";
    }

    @RequestMapping("/hello07")
    public String hello07(@Valid User user, BindingResult bindingResult){
        System.out.println(user);
        if (bindingResult.getErrorCount() > 0){
            System.out.println("类型转换出错了");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError e :
                    fieldErrors) {
                System.out.println(e.getField()+"-"+e.getDefaultMessage());
            }
        }
        return "success";
    }

}





