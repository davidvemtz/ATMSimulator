/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;
import java.sql.*;

/**
 *
 * @author David
 */
public class JDBCMySQL {
    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String JDBC_URL = "jdbc:mysql://localhost/atm?useSSL=false";
    private static String JDBC_USER = "root";
    private static String JDBC_PASS = "admin";
    private static Driver driver = null;
    
    public static synchronized Connection getConnection()
            throws SQLException {
        if (driver == null) {
            try {
                Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
                driver = (Driver) jdbcDriverClass.newInstance();
                DriverManager.registerDriver(driver);
            } catch (Exception e) {
                System.out.println("Fallo en cargar el driver JDBC");
                e.printStackTrace();
            }
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS); 
    }
    
    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            } 
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
    public static void close(PreparedStatement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            } 
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
    // Cierra la conexión
    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }           
    }
             
}