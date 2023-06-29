/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.DbConn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author fidel
 */
public class DBeneficiario {

    private DbConn con;

    public DBeneficiario() {
        this.con = DbConn.getInstance();
    }

    public ArrayList<Beneficiario> listarTodos() {
        ArrayList<Beneficiario> lista = new ArrayList<>();
        String sql = "SELECT * FROM beneficiario;";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                lista.add(new Beneficiario(
                        result.getInt(1),
                        result.getInt(5),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4)
                )
                );
            }
        } catch (Exception e) {
            System.out.println("Excepcion en listarTodos() Dbeneficiario: " + e.getMessage());
        }
        return lista;
    }

    public Beneficiario getBen(int id_ben_Param) {
        Beneficiario Ben = new Beneficiario();
        String sql;
        if (id_ben_Param == 0) {
            sql = "SELECT * FROM beneficiario";
        } else {
            sql = "SELECT * FROM beneficiario ";
            if (id_ben_Param > 0) {
                sql += "AND id =" + id_ben_Param;
            }
        }
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                Ben.id = result.getInt(1);
                Ben.id_usuario = result.getInt(5);
                Ben.nombre = result.getString(2);
                Ben.situaion = result.getString(3);
                Ben.fecha_nac = result.getString(4);
            }
        } catch (Exception e) {
            System.out.println("Excepcion en getBen() Dbeneficiario: " + e.getMessage());
        }
        return Ben;
    }

    public Respuesta regBeneficiario(String Nombre, String situacion, String fecha, int id_usuario) {
        String sql = "INSERT INTO beneficiario(nombre, situacion, fecha_nacimiento, id_usuario)"
                + "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            stmnt.setString(1, Nombre);
            stmnt.setString(2, situacion);
            stmnt.setString(3, fecha);
            stmnt.setInt(4, id_usuario);

            int result = stmnt.executeUpdate();
            con.desconectar();
            return new Respuesta("Beneficiario registrado exitosamente", true);
        } catch (Exception e) {
            System.out.println("Excepcion al regBeneficiario DBenficiario: " + e);
            return new Respuesta("Se ha registrado una ficha clinica con exito!", false);
        }
    }

    public Respuesta editBene(String id, String nombre, String situacion) {
        String sql = "";

        if (id.equals("") && nombre.equals("0") && situacion.equals("0")) {
            return new Respuesta("Los parametros que se pueden editar estan vacios", false);
        }

        if (!id.equals("0") && nombre.equals("0") && situacion.equals("0")) {
            sql = "UPDATE beneficiario SET nombre=? WHERE id =" + id;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, nombre);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DBeneficiario -> editBene -> edit_nombre_Beneficiario: " + e);
            }
        }

        if (nombre.equals("0") && !situacion.equals("0")) {
            sql = "UPDATE beneficiario SET situacion=? WHERE id =" + id;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, situacion);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DBeneficiario -> editbene -> edit_motivo: " + e);
            }
        }

        if (!id.equals("0") && !nombre.equals("0") && !situacion.equals("0")) {
            sql = "UPDATE beneficiario SET nombre=?, situacion=?" + id;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, nombre);
                stmnt.setString(2, situacion);

                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DBeneficiario -> editbene -> edit_todos: " + e);
            }
        }
        return new Respuesta("Se ha editado beneficiario con exito!", false);
    }

}
