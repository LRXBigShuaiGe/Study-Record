package com.xin.dao;

import com.xin.entity.User;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.dao
 * @Author: LI Renxin
 * @CreateTime: 2021-07-16 10:31
 * @ModificationHistory Who    When    What
 * @Description: dao
 */
public interface UserDAO {
    public User getUserById(Integer id);
}
