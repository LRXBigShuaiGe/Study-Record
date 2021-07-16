package com.xin.service;

import com.xin.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.service
 * @Author: LI Renxin
 * @CreateTime: 2021-06-06 16:45
 * @ModificationHistory Who    When    What
 * @Description: 书城业务层
 */
@Service
public class BookService {
    @Autowired
    BookDao dao;

    @Transactional(timeout = 3,noRollbackFor = {ArrayIndexOutOfBoundsException.class, FileNotFoundException.class},
            isolation = Isolation.REPEATABLE_READ,propagation = Propagation.REQUIRES_NEW)
    public void daoUser(String username,int id){
        dao.updateBalance(username,id);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o = dao.selectUser(id);
        System.out.println(o.toString());
    }
}
