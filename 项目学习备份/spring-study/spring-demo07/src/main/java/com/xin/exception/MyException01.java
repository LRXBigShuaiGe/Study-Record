package com.xin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.exception
 * @Author: LI Renxin
 * @CreateTime: 2021-07-15 11:30
 * @ModificationHistory Who    When    What
 * @Description: 一个自定义异常类
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "反正你就是出错了。。。")
public class MyException01 extends RuntimeException {

}
