package com.xin.controller;

import com.xin.bean.Car;
import com.xin.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import java.util.Map;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.controller
 * @Author: LI Renxin
 * @CreateTime: 2021-06-19 19:57
 * @ModificationHistory Who    When    What
 * @Description: springMVC学习
 */

/**
 * @SessionAttributes可以指定哪些数据需要复制一份到session中
 */

//@SessionAttributes("username")      指定保存key为username的数据
@SessionAttributes(value = {"username","password"}) //保存多个数据
//@SessionAttributes(types = String.class)  指定类型为String的数据进行保存
//@SessionAttributes(types = {String.class,Integer.class})  指定多种类型的数据进行保存
@Controller
public class Hello2Controller {
    /**
     * User类为接受对象，自动匹配请求传过来的参数
     * 比如用URL访问
     * http://localhost:8080/springdemo/good01?name=%E5%B0%8F%E7%8E%8B&username=myheader&password=123456ad&age=18&record=19.9&car.type=qq&car.carName=小马&car.name=赫赫
     * 则相关参数会自动匹配User中的属性,car.xxxx则会匹配user属性car内部自己的属性，即级联属性
     * @param user
     * @return
     */
    @RequestMapping("/good01")
    public String helloRequest01(User user){
        System.out.println(user.toString());
        return "success";
    }

    /**
     * 使用servlet原生API不需要传参，会自动传递
     * 可以直接使用
     * @param request
     * @param response
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("/good02")
    public String helloRequest02(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();
        System.out.println();
        return "success";
    }

    @RequestMapping("/good03")
    public String helloRequest03(Model model){
        model.addAttribute("username","星星");
        System.out.println(model);
        return "success";
    }

    @RequestMapping("/good04")
    public String helloRequest04(Map model){
        model.put("username","月亮");
        System.out.println(model);
        return "success";
    }

    @RequestMapping("/good05")
    public String helloRequest05(ModelMap model){
        model.addAttribute("username","太阳");
        System.out.println(model.getClass());
        return "success";
    }

    @RequestMapping("/good06")
    public ModelAndView helloRequest06(){
        //可以在构造方法中指定viewname：跳转到的页面，也可以使用setViewName()方法指定，此次跳转到success.jsp页面
//        ModelAndView modelAndView = new ModelAndView("success");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        //使用addObject()方法添加数据，返回后会将数据保存到request域中
        modelAndView.addObject("username","大海sea");
        modelAndView.addObject("password","天空sky");

        System.out.println(modelAndView);
        return modelAndView;
    }

    @RequestMapping("/good07")
    public String helloRequest07(@RequestParam("author")String author,
                                 Map<String,Object> model,
                                 HttpServletRequest request,
                                 @ModelAttribute("haha")Car car){
        System.out.println(author);
        System.out.println(car.toString());
        Map<String,Object> o2 = model;
        Car car1 = car;
        Object haha = model.get("haha");
        System.out.println(model);
        System.out.println(haha);
        return "success";
    }


}
