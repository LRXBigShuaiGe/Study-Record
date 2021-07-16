package com.xin.service;

import com.xin.dao.BaseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.service
 * @Author: LI Renxin
 * @CreateTime: 2021-05-25 16:34
 * @ModificationHistory Who    When    What
 * @Description: 业务层
 */
@Service
public class BaseService {
    @Autowired
    private BaseDAO baseDAO;

    public void saveService(){
        System.out.println("saveService业务处理中....");
        baseDAO.save();
    }
}
