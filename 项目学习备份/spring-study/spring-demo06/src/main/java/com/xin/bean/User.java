package com.xin.bean;


import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.util.Date;


/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.bean
 * @Author: LI Renxin
 * @CreateTime: 2021-06-19 19:59
 * @ModificationHistory Who    When    What
 * @Description: 用户类
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @NotEmpty
    private String name;
    @NotNull
    private String username;
    private String password;
    @Email
    private String email;
    private int age;
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @NumberFormat(pattern = "#,###,###.##")
    private double salary;
    private float record;
    private Car car;

}
