package com.xin.utils;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  

/** 
 * 数据库工具类(获取连接、释放资源) 
 */  
public class JdbcUtils {  


    private static String jdbcUrl = "jdbc:mysql://rds7wfp8tgk6a2723244.mysql.rds.aliyuncs.com/shyl";
    private static String jdbcUser = "shyladmin";
    private static String jdbcPwd = "Water17902139";

    // 只需要执行一次  
    static{  
        try {  
//            System.out.println("注册数据库驱动......");  
            Class.forName("com.mysql.jdbc.Driver");  
        } catch (ClassNotFoundException e) {  
            throw new RuntimeException("注册数据库驱动出现异常:"+e.getMessage());  
        }  
    }  


    /** 
     * 获取一个连接对象 
     * @return Connection
     */  
    public static Connection getConn() {  
        try {  
            return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPwd);  
        } catch (SQLException ex1) { 
            System.err.println("获取连接异常-->"+ex1.getMessage());
            throw new RuntimeException("无法获取连接,原因:"+ex1.getMessage());  
        }  
    }  


    /**
     * 释放资源
     */
    public static void free(ResultSet rs, Statement st, Connection conn) {  
        try {  
            if (null != rs) {  
                rs.close();  
            }  
        } catch (SQLException e) {  
            throw new RuntimeException("关闭ResultSet出现异常:" + e.getMessage());  
        } finally {  
            try {  
                if (null != st) {  
                    st.close();  
                }  
            } catch (SQLException e) {  
                throw new RuntimeException("关闭Statement出现异常:" + e.getMessage());  
            } finally {  
                if (null != conn) {  
                    try {  
                        conn.close();  
                    } catch (SQLException e) {  
                        throw new RuntimeException("关闭Connection出现异常:"  
                                + e.getMessage());  
                    }  
                }  
            }  
        }  

    }  

    /**
     * 释放资源
     */
    public static void free(ResultSet rs, PreparedStatement ps, Connection conn) {  
        try {  
            if (null != rs) {  
                rs.close();  
            }  
        } catch (SQLException e) {  
            throw new RuntimeException("关闭ResultSet出现异常:" + e.getMessage());  
        } finally {  
            try {  
                if (null != ps) {  
                	ps.close();  
                }  
            } catch (SQLException e) {  
                throw new RuntimeException("关闭Statement出现异常:" + e.getMessage());  
            } finally {  
                if (null != conn) {  
                    try {  
                        conn.close();  
                    } catch (SQLException e) {  
                        throw new RuntimeException("关闭Connection出现异常:"  
                                + e.getMessage());  
                    }  
                }  
            }  
        }  

    }  
}  