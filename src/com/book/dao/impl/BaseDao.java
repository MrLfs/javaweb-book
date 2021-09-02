package com.book.dao.impl;

import com.book.utils.JDBCUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author LVFASEN
 * @create 2021-08-15 16:17
 */
public class BaseDao<T> {
    private Class<T> clazz = null;
    {
        Type genericSuperclass = this.getClass().getGenericSuperclass();

        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        clazz = (Class<T>) actualTypeArguments[0];
    }
    //使用 DbUtils 操作数据库
    private QueryRunner queryRunner = new QueryRunner();
    /**
     * update() 方法用来执行：Insert\Update\Delete 语句
     *
     * @return 如果返回-1,说明执行失败<br/>返回其他表示影响的行数
     */
    public int update(String sql, Object... args) {
        Connection connection = JDBCUtil.getConnection();
        try {
            return queryRunner.update(connection, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }

    }

    /**
     *
     *@param sql 执行的 sql 语句
     * @param args sql 对应的参数值
     * @return
     */
    public T queryForOne(String sql, Object... args) {
        Connection con = JDBCUtil.getConnection();
        try {
            return queryRunner.query(con, sql, new BeanHandler<T>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
    /**
     * 查询返回多个 javaBean 的 sql 语句
     *

     * @param sql 执行的 sql 语句
     * @param args sql 对应的参数值

     * @return
     */
    public  List<T> queryForList(String sql, Object... args) {
        Connection con = JDBCUtil.getConnection();
        try {
            return queryRunner.query(con, sql, new BeanListHandler<T>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
    /**
     * 执行返回一行一列的 sql 语句
     * @param sql 执行的 sql 语句
     * @param args sql 对应的参数值
     * @return
     */
    public Object queryForSingleValue(String sql, Object... args){
        Connection conn = JDBCUtil.getConnection();
        try {
            return queryRunner.query(conn, sql, new ScalarHandler(), args);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }


}
