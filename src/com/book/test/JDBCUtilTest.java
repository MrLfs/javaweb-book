package com.book.test;

import com.book.utils.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author LVFASEN
 * @create 2021-08-15 16:25
 */
public class JDBCUtilTest {
    @Test
    public void test(){
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
    }
}
