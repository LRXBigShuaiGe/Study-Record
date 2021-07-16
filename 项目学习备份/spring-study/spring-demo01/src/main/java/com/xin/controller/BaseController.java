package com.xin.controller;

import com.xin.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.controller
 * @Author: LI Renxin
 * @CreateTime: 2021-05-25 16:34
 * @ModificationHistory Who    When    What
 * @Description: 控制层
 */
@Controller
public class BaseController {
    @Autowired
    private BaseService baseService;

    public void save(){
        System.out.println("呵呵呵........");
        baseService.saveService();
    }
}
