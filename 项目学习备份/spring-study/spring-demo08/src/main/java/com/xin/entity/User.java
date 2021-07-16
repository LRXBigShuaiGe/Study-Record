package com.xin.entity;

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
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String email;

}
