/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cajero;

import Beans.Cuenta;
import Persistencia.JDBCMySQL;
import java.util.ArrayList; // import the ArrayList class
import java.util.Scanner;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class Cajero extends Application {
    
    public static ArrayList<Cuenta> cuentas = new ArrayList<>(); //Arraylist of Account class onjects
    private static final String SQL_SELECT = "SELECT idcuenta, saldo FROM TablaCuentas";
    
    public static List<Cuenta> select() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cuenta cuenta = null;
        
        try {
            conn = JDBCMySQL.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int idCuenta = rs.getInt(1);
                double saldo = rs.getDouble(2);
                cuenta = new Cuenta();
                cuenta.setId(idCuenta);
                cuenta.setBalance(saldo);
                cuentas.add(cuenta);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JDBCMySQL.close(rs);
                JDBCMySQL.close(conn);
                JDBCMySQL.close(stmt);
            }
            return cuentas;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Resources/Views/cajeroScene.fxml"));
        
        Scene scene = new Scene(root); 
        
        stage.setScene(scene);
        stage.show(); 
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            List<Cuenta> cuentas = select();
        } catch (Exception e) {
            Logger.getLogger(Cajero.class.getName()).log(Level.SEVERE, null, e);
        }
        for (Cuenta cuenta: cuentas) {
            System.out.println(cuenta.getId()); 
        }
        launch(args); 
    }     
}

