package com.book.dao.impl;

import com.book.dao.UserDao;
import com.book.pojo.User;

import java.util.List;

/**
 * @author LVFASEN
 * @create 2021-08-15 16:34
 */
public class UserDaoImpl extends BaseDao<User> implements UserDao {
        @Override
        public User queryUserByUsername(String username) {
            String sql = "select `id`,`username`,`password`,`email` from t_user where username = ?";
            return queryForOne(sql, username);
        }
        @Override
        public User queryUserByUsernameAndPassword(String username, String password) {
            String sql = "select `id`,`username`,`password`,`email` from t_user where username = ? and password = ?";
            return queryForOne( sql, username,password);
        }
        @Override
        public int saveUser(User user) {
            String sql = "insert into t_user(`username`,`password`,`email`) values(?,?,?)";
            return update(sql, user.getUsername(),user.getPassword(),user.getEmail());
        }

    @Override
    public List<User> getAll() {
            String sql = "select id,username,password,email from t_user";
           return  queryForList(sql);
    }
}
