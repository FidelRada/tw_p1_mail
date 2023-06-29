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
 * @author gstevenvalverde
 */
public class DClinico {
    private DbConn con;

    public DClinico() {
        this.con = DbConn.getInstance();
    }

    public ArrayList<Clinico> listarTodos() {
        ArrayList<Clinico> lista = new ArrayList<>();
        String sql = "SELECT * FROM ficha_clininca";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                lista.add(new Clinico(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getInt(6),
                        result.getString(7),
                        result.getString(8)
                    )
                );
            }
        } catch (Exception e) {
            System.out.println("Excepcion en listarTodos() DClinico: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Clinico> listarCli(int beneficiarioParam) {
        ArrayList<Clinico> lista = new ArrayList<>();
        String sql;
        if (beneficiarioParam == 0) {
            sql = "SELECT * FROM ficha_clinica";
        } else {
            sql = "SELECT * FROM ficha_clinica ";
            if (beneficiarioParam > 0) {
                sql += "AND id_beneficiario =" + beneficiarioParam;
            }
        }
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                lista.add(new Clinico(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5),
                        result.getInt(6),
                        result.getString(7),
                        result.getString(8)
                    )
                );
            }
        } catch (Exception e) {
            System.out.println("Excepcion en listarCli() DClinico: " + e.getMessage());
        }
        return lista;
    }

    public Respuesta regCli(int beneficiarioParam, String nombreDoctorParam, String motivoParam, 
            String prescripcionMedParam, String observacionesParam) {
        String sql = "INSERT INTO ficha_clinica(nombre_doctor, motivo, prescripcion_med, observaciones, id_beneficiario, created_at, updated_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            stmnt.setString(1, nombreDoctorParam);
            stmnt.setString(2, motivoParam);
            stmnt.setString(3, prescripcionMedParam);
            stmnt.setString(4, observacionesParam);
            stmnt.setInt(5, beneficiarioParam);
            stmnt.setString(6, " ");
            stmnt.setString(7, " ");

            int result = stmnt.executeUpdate();
            con.desconectar();
            return new Respuesta("Ficha Clinica registrada exitosamente", true);
        } catch (Exception e) {
            System.out.println("Excepcion al regCli DClinico: " + e);
        }
        return new Respuesta("Se ha registrado una ficha clinica con exito!", false);
    }

    public Respuesta editCli(int idCli, String nombreDoctorParam, String motivoParam, 
            String prescripcionMedParam, String observacionesParam) {
        String sql = "";

        if (nombreDoctorParam.equals("0") && motivoParam.equals("0") && prescripcionMedParam.equals("0") && observacionesParam.equals("0")) {
            return new Respuesta("Los parametros que se pueden editar estan vacios", false);
        }

        if (!nombreDoctorParam.equals("0") && motivoParam.equals("0") && prescripcionMedParam.equals("0") && observacionesParam.equals("0")) {
            sql = "UPDATE ficha_clinica SET nombre_doctor=? WHERE id =" + idCli;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, nombreDoctorParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DClinico -> editdoc -> edit_nombre_doctor: " + e);
            }
        }

        if (nombreDoctorParam.equals("0") && !motivoParam.equals("0") && prescripcionMedParam.equals("0") && observacionesParam.equals("0")) {
            sql = "UPDATE ficha_clinica SET motivo=? WHERE id =" + idCli;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, motivoParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DClinico -> editdoc -> edit_motivo: " + e);
            }
        }
        
        if (nombreDoctorParam.equals("0") && motivoParam.equals("0") && !prescripcionMedParam.equals("0") && observacionesParam.equals("0")) {
            sql = "UPDATE ficha_clinica SET prescripcion_med=? WHERE id =" + idCli;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, prescripcionMedParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DClinico -> editdoc -> edit_prescripcion_med: " + e);
            }
        }
        
        if (nombreDoctorParam.equals("0") && motivoParam.equals("0") && prescripcionMedParam.equals("0") && !observacionesParam.equals("0")) {
            sql = "UPDATE ficha_clinica SET observaciones=? WHERE id =" + idCli;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, observacionesParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DClinico -> editdoc -> edit_observaciones: " + e);
            }
        }

        if (!nombreDoctorParam.equals("0") && !motivoParam.equals("0") && !prescripcionMedParam.equals("0") && !observacionesParam.equals("0")) {
            sql = "UPDATE ficha_clinica SET nombre_doctor=?, motivo=?, prescripcion_med=?, observaciones=? WHERE doc_id =" + idCli;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, nombreDoctorParam);
                stmnt.setString(2, motivoParam);
                stmnt.setString(3, prescripcionMedParam);
                stmnt.setString(4, observacionesParam);

                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DClinico -> editdoc -> edit_todos: " + e);
            }
        }
        return new Respuesta("Se ha editado la ficha clinica con exito!", false);
    }
    
}