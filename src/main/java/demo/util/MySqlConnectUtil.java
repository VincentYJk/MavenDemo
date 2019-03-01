package demo.util;

import java.sql.*;

public class MySqlConnectUtil {
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://119.191.58.20:3308/website_yantai?useUnicode=true&characterEncoding=UTF-8&useSSL=false";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "website";
    static final String PASS = "websi173te";
    public static Connection getConnect()throws Exception{
        Connection conn = null;
        // 注册 JDBC 驱动
        Class.forName("com.mysql.jdbc.Driver");
       return  DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
