package com.xin.controller;

import com.xin.bean.User;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.controller
 * @Author: LI Renxin
 * @CreateTime: 2021-07-12 9:39
 * @ModificationHistory Who    When    What
 * @Description: hello
 */
@Controller
public class HelloController {
    @RequestMapping("/h3/hello01")
    public String hello01(){
        return "hello";
    }

    @RequestMapping("/hello02")
    public String hello02(@Valid User user, BindingResult bindingResult){
        System.out.println(user);
        if (bindingResult.getErrorCount() > 0){
            System.out.println("类型转换出错了");
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError e :
                    fieldErrors) {
                System.out.println(e.getField()+"-"+e.getDefaultMessage());
            }
        }
        return "hello";
    }

    @RequestMapping("/hello03")
    @ResponseBody
    public User hello03(@RequestBody String body,@Valid User user){
        System.out.println(user);
        System.out.println(body);
        return user;
    }

    @ResponseBody
    @RequestMapping("/hello04")
    public String hello04(@RequestBody String body){

        System.out.println(body);
        return "hello";
    }
    @ResponseBody  //@ResponseBody:是将内容或对象作为Http响应正文返回
    @RequestMapping("/testHttpMessageConverter")
//@RequestBody:是将Http请求正文插入方法中，修饰目标方法的入参
    public String testHttpMessageConverter(@RequestBody(required = false) User body){
        System.out.println("body="+body);
        return "Hello," + new Date();  //不再查找跳转的页面
    }

    /**
     * 文件下载简单案例
     * @param entity
     * @return
     */
    @RequestMapping("/hello05")
    public String hello05(HttpEntity<String> entity){
        System.out.println(entity.getHeaders().toString());
        System.out.println(entity.getBody().toString());
        return "hello";
    }
    @RequestMapping("/hello06")
    public ResponseEntity<byte[]> hello06() throws IOException {
        Resource resource = new ClassPathResource("/lb7.jpg");
        byte[] fileData = FileCopyUtils.copyToByteArray(resource.getInputStream());
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(fileData, HttpStatus.OK);
        return responseEntity;
    }

    /**
     * 文件上传
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/hello07")
    public String hello07(@RequestParam(value = "files") MultipartFile file){
        System.out.println("originalFilename:"+file.getOriginalFilename());

        try {
            file.transferTo(new File("D:\\Download\\"+file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    
}
