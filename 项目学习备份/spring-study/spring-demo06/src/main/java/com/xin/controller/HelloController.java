package com.xin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.controller
 * @Author: LI Renxin
 * @CreateTime: 2021-06-15 21:45
 * @ModificationHistory Who    When    What
 * @Description: 首页接口登录UTF
 */
@Controller
public class HelloController {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String helloRequest(){
        System.out.println("请求接收，正在处理。。。");
        return "success";
    }

    @RequestMapping("/error")
    public String errorRequest(){
        System.out.println("请求接收，正在处理。。。");
        return "error";
    }

    @RequestMapping(value = "/hello2",method = RequestMethod.GET,params = {"username","!name","num=123","num2!=123"})
    public String helloRequest2(){
        System.out.println("请求接收，正在处理。。。");
        return "success";
    }


    //火狐的 User-Agent：Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0
    //谷歌的 User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36
    @RequestMapping(value = "/hello3",method = RequestMethod.GET,
            headers = "User-Agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0")
    public String helloRequest3(){
        System.out.println("请求接收，正在处理。。。");
        return "success";
    }


    /**
     * ?只匹配一个字符，0个或多个都不行
     *        当访问路径对应多个方法时，选择最精确的进行访问
     * @return
     */
    @RequestMapping(value = "/hello1?",method = RequestMethod.GET)
    public String helloRequest4(){
        System.out.println("请求接收    public String helloRequest4()，正在处理。。。");
        return "success";
    }
    /**
     *  * 匹配多个字符或一层路径
     * @return
     */
    @RequestMapping(value = "/hello1*",method = RequestMethod.GET)
    public String helloRequest5(){
        System.out.println("请求接收    public String helloRequest5()，正在处理。。。");
        return "success";
    }
    @RequestMapping(value = "/hello6/*/haha",method = RequestMethod.GET)
    public String helloRequest6(){
        System.out.println("请求接收    public String helloRequest6()，正在处理。。。");
        return "success";
    }

    /**
     *       ** 匹配多层路径
     * @return
     */
    @RequestMapping(value = "/hello1/**/haha",method = RequestMethod.GET)
    public String helloRequest7(){
        System.out.println("请求接收    public String helloRequest7()，正在处理。。。");
        return "success";
    }

    /**
     * 占位符的使用，在路径的某一层使用   {变量名}
     * 占位符只能占用一层路径
     * @param user
     * @return
     */
    @RequestMapping(value = "/hello8/{username}")
    public String helloRequest8(@PathVariable("username") String user){
        System.out.println("请求接受到的username："+user);
        return "success";
    }

    /**
     * @RequestParam可以指定参数名称，必然指定传入参数名称为username，并以自己定义的变量name接收
     * @RequesesParam默认必须传入，但可以设置required属性设置是否必须
     * 当没有传入指定参数时，则使用defaultvalue设置的默认值
     * @param name
     * @return
     */
    @RequestMapping(value = "/hello9")
    public String helloRequest9(@RequestParam(value = "username",required = false,defaultValue = "小明") String name){
        System.out.println("获取到的名字name："+name);
        return "success";
    }

    /**
     * @RequestHeader用于获取请求头中的值，比如获取请求头中的浏览器信息User-Agent，接受的编码方式accept-encoding
     * @RequesesHeaderr默认必须传入，但可以设置required属性设置是否必须
     * 当没有传入指定参数时，则使用defaultvalue设置的默认值
     *
     * @param useragent
     * @return
     */
    @RequestMapping(value = "/bye01")
    public String byeRequest01(@RequestHeader(value = "User-Agent",required = false,defaultValue = "hahah")String useragent,
                               @RequestHeader(value = "accept-encoding",required = false,defaultValue = "hahah")String encoding){
        System.out.println("获取到的名字name："+useragent);
        System.out.println("获取到的名字name："+encoding);
        return "success";
    }

    /**
     * (@CookieValue用于获取Cookie信息
     * (@CookieValue默认必须传入，但可以设置required属性设置是否必须
     * 当没有传入指定参数时，则使用defaultvalue设置的默认值
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/bye02")
    public String byeRequest02(@CookieValue(value = "JSESSIONID",required = false,defaultValue = "123456789")String ss){
        System.out.println("获取到的JESSIONID："+ss);
        return "success";
    }

}
