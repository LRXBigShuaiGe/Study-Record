package com.xin.bean;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.bean
 * @Author: LI Renxin
 * @CreateTime: 2021-07-13 17:05
 * @ModificationHistory Who    When    What
 * @Description: yo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

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


}
