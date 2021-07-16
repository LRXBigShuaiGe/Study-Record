package com.xin.controller;

import com.xin.entity.User;
import com.xin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.controller
 * @Author: LI Renxin
 * @CreateTime: 2021-07-16 10:56
 * @ModificationHistory Who    When    What
 * @Description: 用户控制器
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/user")
    @ResponseBody
    public User getUser(int id){

        System.out.println("id = "+id);
        return userService.getUserById(id);
    }

}
