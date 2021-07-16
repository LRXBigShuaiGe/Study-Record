package com.xin.dao;

import com.xin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.dao
 * @Author: LI Renxin
 * @CreateTime: 2021-06-05 15:38
 * @ModificationHistory Who    When    What
 * @Description: 书城的dao层
 */
@Repository
public class BookDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void updateBalance(String userName,int id){
        String sql = "UPDATE user SET username=? WHERE id=?";
        Object[] objects = new Object[]{userName,id};
        jdbcTemplate.update(sql,objects);
    }

    public void deleteUser(String username,int id){
        String sql = "DELETE FROM user WHERE id=? OR username=?";
        Object[] objects = new Object[]{username, id};
        jdbcTemplate.update(sql,objects);
    }

    public Object selectUser(int id){
        String sql = "SELECT * FROM user WHERE id=?";
        Object[] objects = new Object[]{id};
        User user = jdbcTemplate.queryForObject(sql,objects,(rs,rowMap)->{
            User user1 = new User();
            user1.setId(rs.getInt("id"));
            user1.setUsername(rs.getString("username"));
            user1.setPassword(rs.getString("password"));
            user1.setEmail(rs.getString("email"));
            return user1;
        });
        return user;
    }
}
