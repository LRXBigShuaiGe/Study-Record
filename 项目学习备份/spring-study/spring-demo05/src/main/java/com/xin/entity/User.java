package com.xin.entity;

import lombok.Data;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.entity
 * @Author: LI Renxin
 * @CreateTime: 2021-06-06 17:06
 * @ModificationHistory Who    When    What
 * @Description: 用户类
 */
@Data
public class User {
    int id;
    String username;
    String password;
    String email;
}
