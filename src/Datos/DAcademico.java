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
public class DAcademico {
    private DbConn con;

    public DAcademico() {
        this.con = DbConn.getInstance();
    }

    public ArrayList<Academico> listarTodos() {
        ArrayList<Academico> lista = new ArrayList<>();
        String sql = "SELECT * FROM informe_academico";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                lista.add(new Academico(
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
            System.out.println("Excepcion en listarTodos() DAcadmico: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Academico> listarCli(int beneficiarioParam) {
        ArrayList<Academico> lista = new ArrayList<>();
        String sql;
        if (beneficiarioParam == 0) {
            sql = "SELECT * FROM informe_academico";
        } else {
            sql = "SELECT * FROM informe_academico ";
            if (beneficiarioParam > 0) {
                sql += "AND id_beneficiario =" + beneficiarioParam;
            }
        }
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                lista.add(new Academico(
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
            System.out.println("Excepcion en listarAcad() DAcademico: " + e.getMessage());
        }
        return lista;
    }

    public Respuesta regAcad(int beneficiarioParam, String gradoEscolarParam, String nombreColegioParam, 
            String direccionColegioParam, String desempenhoParam) {
        String sql = "INSERT INTO informe_academico(gradoEscolarParam, nombreColegioParam, direccionColegioParam, desempenhoParam, id_beneficiario, created_at, updated_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            stmnt.setString(1, gradoEscolarParam);
            stmnt.setString(2, nombreColegioParam);
            stmnt.setString(3, direccionColegioParam);
            stmnt.setString(4, desempenhoParam);
            stmnt.setInt(5, beneficiarioParam);
            stmnt.setString(6, " ");
            stmnt.setString(7, " ");

            int result = stmnt.executeUpdate();
            con.desconectar();
            return new Respuesta("Informe academico registrado exitosamente", true);
        } catch (Exception e) {
            System.out.println("Excepcion al regAcad DAcademico: " + e);
        }
        return new Respuesta("Se ha registrado un informe academico con exito!", false);
    }

    public Respuesta editacad(int idCli, String gradoEscolarParam, String nombreColegioParam, 
            String direccionColegioParam, String desempenhoParam) {
        String sql = "";

        if (gradoEscolarParam.equals("0") && nombreColegioParam.equals("0") && direccionColegioParam.equals("0") && desempenhoParam.equals("0")) {
            return new Respuesta("Los parametros que se pueden editar estan vacios", false);
        }

        if (!gradoEscolarParam.equals("0") && nombreColegioParam.equals("0") && direccionColegioParam.equals("0") && desempenhoParam.equals("0")) {
            sql = "UPDATE informe_academico SET grado=? WHERE id =" + idCli;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, gradoEscolarParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DAcademico -> editacad -> edit_grado_escolar: " + e);
            }
        }

        if (gradoEscolarParam.equals("0") && !nombreColegioParam.equals("0") && direccionColegioParam.equals("0") && desempenhoParam.equals("0")) {
            sql = "UPDATE informe_academico SET nombre_colegio=? WHERE id =" + idCli;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, nombreColegioParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DAcademico -> editacad -> edit_nombre_colegio: " + e);
            }
        }
        
        if (gradoEscolarParam.equals("0") && nombreColegioParam.equals("0") && !direccionColegioParam.equals("0") && desempenhoParam.equals("0")) {
            sql = "UPDATE informe_academico SET direccion_colegio=? WHERE id =" + idCli;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, direccionColegioParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DAcademico -> editacad -> edit_direccion_colegio: " + e);
            }
        }
        
        if (gradoEscolarParam.equals("0") && nombreColegioParam.equals("0") && direccionColegioParam.equals("0") && !desempenhoParam.equals("0")) {
            sql = "UPDATE informe_academico SET desempeño=? WHERE id =" + idCli;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, desempenhoParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DAcademico -> editacad -> edit_desempenho: " + e);
            }
        }

        if (!gradoEscolarParam.equals("0") && !nombreColegioParam.equals("0") && !direccionColegioParam.equals("0") && !desempenhoParam.equals("0")) {
            sql = "UPDATE informe_academico SET grado=?, motivo=?, nombre_colegio=?, direccion_colegio=? WHERE desempeño =" + idCli;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, gradoEscolarParam);
                stmnt.setString(2, nombreColegioParam);
                stmnt.setString(3, direccionColegioParam);
                stmnt.setString(4, desempenhoParam);

                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DAcademico -> editacad -> edit_todos: " + e);
            }
        }
        return new Respuesta("Se ha editado un informe academico con exito!", false);
    }
    
}