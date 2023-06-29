/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fidel
 */
public class DbConn {
    private final String HOST = "www.tecnoweb.org.bo";
    private final String PUERTO = "5432";
    private final String DB = "db_grupo01sa";
    private final String USER = "grupo01sa";
    private final String PASSWORD = "grup001grup001";

    private static DbConn instancia;
    private Connection conn;
    
    public DbConn(){
        this.conn = null;
    }
    
    public Connection conectar() {
        try {
            String url = "jdbc:postgresql://" + HOST + ":" + PUERTO + "/" + DB;
            System.out.println(url);
            this.conn = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("Conexion exitosa");
        } catch (Exception e) {
            System.out.println("Excepcion al conectar a postgresql DbConn: " + e);
        }
        return this.conn;
    }
    
    public void desconectar(){
        try{
            this.conn.close();
            System.out.println("DEsconectado!!");
        }catch(SQLException e){
            System.out.println("Excepcion al deconectar DbConn: " + e.getMessage());
        }
    }
    
    public static DbConn getInstance(){
        if(instancia == null){
            instancia = new DbConn();
            return instancia;
        }
        return instancia;
    }
    public static void main(String[] args) {
        try {
            DbConn db = new DbConn();
            String sql = "SELECT * FROM Documents;";
            PreparedStatement stmnt = db.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            while (result.next()) {                
                System.out.println(result.getArray(1));
            }
            db.desconectar();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    
}
