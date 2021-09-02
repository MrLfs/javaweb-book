package com.book.test;

import com.book.dao.UserDao;
import com.book.dao.impl.UserDaoImpl;
import com.book.pojo.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author LVFASEN
 * @create 2021-08-15 16:38
 */
public class UserDaoTest {

    UserDao userDao = new UserDaoImpl();
    @Test
    public void queryUserByUsername() {
        if (userDao.queryUserByUsername("admin1234") == null ){
            System.out.println("用户名可用！");
        } else {
            System.out.println("用户名已存在！");
        }
    }
    @Test
    public void queryUserByUsernameAndPassword() {
        if ( userDao.queryUserByUsernameAndPassword("admin","admin") == null) {
            System.out.println("用户名或密码错误，登录失败");
        } else {
            System.out.println("查询成功");
        }
    }
    @Test
    public void saveUser() {
        User user = new User(null, "lfs123", "123456", "lfs@qq.com");
        int i = userDao.saveUser(user);
        System.out.println(i);
    }

    @Test
    public void get(){
        List<User> all = userDao.getAll();
        all.forEach(System.out::println);
    }
}