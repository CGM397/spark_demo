package nju.cgm.sparkstreamingtest.dao.impl;

import java.io.Serializable;
import java.sql.*;

/**
 * @author: Bright Chan
 * @date: 2020/11/7 9:12
 * @description: BaseDao
 */
public class BaseDao implements Serializable {
    private static final long serialVersionUID = 1L;
    private static String driver="com.mysql.cj.jdbc.Driver";
    private static String url="jdbc:mysql://host:3306/spark_data?characterEncoding=UTF8&useSSL=false";
    private static String user="";
    private static String password="";
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        if(rs!=null) {
            rs.close();
        }
        if(stmt!=null) {
            stmt.close();
        }
        if(conn!=null) {
            conn.close();
        }
    }


    public int executeSQL(String preparedSql, Object[] param) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        /* 处理SQL,执行SQL */
        try {
            conn = getConnection(); // 得到数据库连接
            pstmt = conn.prepareStatement(preparedSql); // 得到PreparedStatement对象
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstmt.setObject(i + 1, param[i]); // 为预编译sql设置参数
                }
            }
            ResultSet num = pstmt.executeQuery(); // 执行SQL语句
        } catch (SQLException e) {
            e.printStackTrace(); // 处理SQLException异常
        } finally {
            try {
                BaseDao.closeAll(conn, pstmt, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
