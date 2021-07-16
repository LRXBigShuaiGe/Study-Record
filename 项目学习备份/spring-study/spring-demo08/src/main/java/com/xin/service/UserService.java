package com.xin.service;

import com.xin.dao.UserDAO;
import com.xin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.service
 * @Author: LI Renxin
 * @CreateTime: 2021-07-16 10:27
 * @ModificationHistory Who    When    What
 * @Description: userService
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public void hah(){
        System.out.println("haha");
    }

    public User getUserById(Integer id){
        System.out.println("userservice...");
        User user = userDAO.getUserById(id);
        System.out.println(user);
        return user;
    }
}
