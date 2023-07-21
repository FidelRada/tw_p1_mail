/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.DbConn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author fidel
 */
public class DBeneficiario {

    private DbConn con;

    public DBeneficiario() {
        this.con = DbConn.getInstance();
    }

    public Map<String, Object> listarTodos() {

        Map<String, Object> resp = new HashMap<>();
        ArrayList<Beneficiario> lista = new ArrayList<>();

        String sql = "SELECT id, nombre, situacion, fecha_nacimiento, id_usuario FROM beneficiario;";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                /*for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                System.out.println(result.getObject(i) + "=>\t" + result.getObject(i).getClass().getName());
                }*/

                lista.add(new Beneficiario(
                        result.getLong(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getLong(5)));
            }
            resp.put("resp", lista);
        } catch (Exception e) {
            resp.put("error", "Error en DBeneficiario.listarTodos()");
            System.out.println("Excepcion al listarTodos DBeneficiario: " + e.getMessage());
        }
        return resp;
    }

    public Map<String, Object> listarben(LinkedList params) {

        Map<String, Object> resp = new HashMap<>();

        ArrayList<Beneficiario> lista = new ArrayList<>();
        String p = params.toString().replace("[", "").replace("]", "");
        String sql = "SELECT " + p + " FROM Beneficiario;";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                /*for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                System.out.println(result.getObject(i) + "=>\t" + result.getObject(i).getClass().getName());
                }*/

                Beneficiario beneficiario = new Beneficiario();
                if (params.contains("id")) {
                    beneficiario.id = result.getLong("id");
                }
                if (params.contains("nombre")) {
                    beneficiario.nombre = result.getString("nombre");
                }
                if (params.contains("situacion")) {
                    beneficiario.situacion = result.getString("situacion");
                }
                if (params.contains("fecha_nac")) {
                    beneficiario.fecha_nac = result.getString("fecha_nac");
                }

                lista.add(beneficiario);
            }
            resp.put("resp", lista);
        } catch (Exception e) {
            resp.put("error", "error en DBeneficiario.listarben()");
            System.out.println("Excepcion al listarTodos DBeneficiario: " + e.getMessage());
        }
        return resp;
    }

/*    public Beneficiario getBen(int id_ben_Param) {
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
*/
    public Map<String, Object> regBeneficiario(Map<String, Object> params) {

        Map<String, Object> resp = new HashMap<>();

        String sql = "INSERT INTO public.beneficiario(nombre, situacion, fecha_nacimiento, id_usuario)"
                + "VALUES ( \'"+ params.get("nombre") +"\', \'"+ params.get("situacion") +"\',  \'"+ params.get("fecha_nac")+"\', "+ params.get("id_encargado") +");";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            //stmnt.setString(1, (String) params.get("nombre"));
            //stmnt.setString(2, (String) params.get("situacion"));
            //stmnt.setString(3, (String) params.get("fecha_nac"));
            //stmnt.setLong(4, (long) params.get("id_encargado"));

            int result = stmnt.executeUpdate();
            resp.put("resp", result);
            con.desconectar();
        } catch (Exception e) {
            resp.put("error", "error al insertar en base de datos");
            return resp;
        }

        return resp;
    }

    public Map<String, Object> editBene(Map<String, Object> params) {
        Map<String, Object> resp = new HashMap<>();

        if (params.get("id") == null) {
            resp.put("error", "error de sintaxis, el valor id es obligatorio");
            return resp;
        }

        String sql = "UPDATE public.beneficiario "
                + "	SET ";

        if (params.get("nombre") != null) {
            sql += "nombre = " + params.get("nombre") + ", ";
        }

        if (params.get("situacion") != null) {
            sql += "situacion = \'" + params.get("situacion") + "\', ";
        }

        if (params.get("fecha_nac") != null) {
            sql += "fecha_nac = \'" + params.get("fecha_nac") + "\', ";
        }

        sql = sql.substring(0, sql.length() - 2)
                + " WHERE id = " + params.get("id");

        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);

            int result = stmnt.executeUpdate();
            resp.put("resp", result);
            con.desconectar();
        } catch (Exception e) {
            resp.put("error", "error al insertar en base de datos");
            return resp;
        }

        return resp;
    }

    public Map<String, Object> elimben(Map<String, Object> params) {

        Map<String, Object> resp = new HashMap<>();

        //String sql = "SELECT COUNT(*) FROM public.users WHERE id = " + params.get("id")+";";
        String sql = "DELETE FROM public.beneficiario WHERE id = " + params.get("id") + ";";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            int filasEliminadas = stmnt.executeUpdate();
            con.desconectar();
            if (filasEliminadas < 1) {
                resp.put("error", "beneficiario no encontrado/no existe");
                return resp;
            }
            resp.put("resp", filasEliminadas);
        } catch (Exception e) {
            System.out.println("Excepcion al eliminar BENIFICIARIO de la base de datos: " + e);
            resp.put("error", "Excepcion al eliminar BENIFICIARIO de la base de datos");
            
        }
            return resp;

    }

}
