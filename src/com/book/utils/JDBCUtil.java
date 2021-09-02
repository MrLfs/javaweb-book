package com.book.utils;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author LVFASEN
 * @create 2021-08-15 16:17
 */
public class JDBCUtil {
    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();
    static {
        try {
            Properties properties = new Properties();
// 读取 jdbc.properties 属性配置文件
            InputStream inputStream =
                    JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
// 从流中加载数据
            properties.load(inputStream);
// 创建 数据库连接 池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取数据库连接池中的连接
     * @return 如果返回 null,说明获取连接失败<br/>有值就是获取连接成功
     */
    public static Connection getConnection(){

        Connection conn = conns.get();

        if(conn ==null){
            try {
                conn = dataSource.getConnection();//获取连接
                conns.set(conn);//保存到ThreadLocal对象中，供后面的使用
                conn.setAutoCommit(false);
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }

        return conn;
    }

    public static void commitAndClose(){
        Connection connection = conns.get();
        if(connection != null){//如果不为空，说明之前使用过连接操作过数据库
            try {
                connection.commit();//提交事务

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    connection.close();//关闭连接
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //一定要执行remove操作，否则就会出错，（因为Tomcat服务器底层使用了线程池技术）
        conns.remove();
    }
    public static void rollbackAndClose(){
        Connection connection = conns.get();
        if(connection != null){//如果不为空，说明之前使用过连接操作过数据库
            try {
                connection.rollback();//回滚事务

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    connection.close();//关闭连接
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //一定要执行remove操作，否则就会出错，（因为Tomcat服务器底层使用了线程池技术）
        conns.remove();
    }
    /**
     * 关闭连接，放回数据库连接池
     * @param conn
     */
//    public static void close(Connection conn){
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
