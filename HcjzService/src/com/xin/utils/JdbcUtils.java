package com.xin.utils;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  

/** 
 * ���ݿ⹤����(��ȡ���ӡ��ͷ���Դ) 
 */  
public class JdbcUtils {  


    private static String jdbcUrl = "jdbc:mysql://rds7wfp8tgk6a2723244.mysql.rds.aliyuncs.com/shyl";
    private static String jdbcUser = "shyladmin";
    private static String jdbcPwd = "Water17902139";

    // ֻ��Ҫִ��һ��  
    static{  
        try {  
//            System.out.println("ע�����ݿ�����......");  
            Class.forName("com.mysql.jdbc.Driver");  
        } catch (ClassNotFoundException e) {  
            throw new RuntimeException("ע�����ݿ����������쳣:"+e.getMessage());  
        }  
    }  


    /** 
     * ��ȡһ�����Ӷ��� 
     * @return Connection
     */  
    public static Connection getConn() {  
        try {  
            return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPwd);  
        } catch (SQLException ex1) { 
            System.err.println("��ȡ�����쳣-->"+ex1.getMessage());
            throw new RuntimeException("�޷���ȡ����,ԭ��:"+ex1.getMessage());  
        }  
    }  


    /**
     * �ͷ���Դ
     */
    public static void free(ResultSet rs, Statement st, Connection conn) {  
        try {  
            if (null != rs) {  
                rs.close();  
            }  
        } catch (SQLException e) {  
            throw new RuntimeException("�ر�ResultSet�����쳣:" + e.getMessage());  
        } finally {  
            try {  
                if (null != st) {  
                    st.close();  
                }  
            } catch (SQLException e) {  
                throw new RuntimeException("�ر�Statement�����쳣:" + e.getMessage());  
            } finally {  
                if (null != conn) {  
                    try {  
                        conn.close();  
                    } catch (SQLException e) {  
                        throw new RuntimeException("�ر�Connection�����쳣:"  
                                + e.getMessage());  
                    }  
                }  
            }  
        }  

    }  

    /**
     * �ͷ���Դ
     */
    public static void free(ResultSet rs, PreparedStatement ps, Connection conn) {  
        try {  
            if (null != rs) {  
                rs.close();  
            }  
        } catch (SQLException e) {  
            throw new RuntimeException("�ر�ResultSet�����쳣:" + e.getMessage());  
        } finally {  
            try {  
                if (null != ps) {  
                	ps.close();  
                }  
            } catch (SQLException e) {  
                throw new RuntimeException("�ر�Statement�����쳣:" + e.getMessage());  
            } finally {  
                if (null != conn) {  
                    try {  
                        conn.close();  
                    } catch (SQLException e) {  
                        throw new RuntimeException("�ر�Connection�����쳣:"  
                                + e.getMessage());  
                    }  
                }  
            }  
        }  

    }  
}  