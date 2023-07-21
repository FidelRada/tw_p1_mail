/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author fidel
 */
import Conexion.DbConn;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DUsuario {

    private DbConn con;

    public DUsuario() {
        this.con = DbConn.getInstance();
    }

    public Map<String, Object> listarusu(LinkedList params) {
        
        Map<String, Object> resp = new HashMap<>();
                
        ArrayList<Usuario> lista = new ArrayList<>();
        String p = params.toString().replace("[", "").replace("]", "");
        String sql = "SELECT " + p + " FROM Users;";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                /*for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                System.out.println(result.getObject(i) + "=>\t" + result.getObject(i).getClass().getName());
                }*/

                Usuario usuario = new Usuario();
                if (params.contains("id")) {
                    usuario.id = result.getLong("id");
                }
                if (params.contains("ci")) {
                    usuario.ci = result.getString("ci");
                }
                if (params.contains("fullname")) {
                    usuario.fullname = result.getString("fullname");
                }
                if (params.contains("email")) {
                    usuario.email = result.getString("email");
                }
                if (params.contains("estado")) {
                    usuario.estado = result.getString("estado");
                }

                lista.add(usuario);
            }
            resp.put("resp", lista);
        } catch (Exception e) {
            resp.put("error", "error en DUsuario.listarusu()");
            System.out.println("Excepcion al listarTodos DUsuario: " + e.getMessage());
        }
        return resp;
    }

    public Map<String, Object> listarTodos() {
        Map<String, Object> resp = new HashMap<>();
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, ci, fullname, email, estado FROM Users;";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                /*for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                System.out.println(result.getObject(i) + "=>\t" + result.getObject(i).getClass().getName());
                }*/

                lista.add(new Usuario(
                        result.getLong(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5)));
            }
            resp.put("resp", "lista");
        } catch (Exception e) {
            resp.put("error", "Error en DUsuario.listarTodos()");
            System.out.println("Excepcion al listarTodos DUsuario: " + e.getMessage());
        }
        return resp;
    }

    public Usuario checkEmail(String email) {
        String sql = "SELECT id, role_id, email, password, ci, fullname FROM Users  WHERE estado = 'ok' AND email = ? ;";
        Usuario u = new Usuario();
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            stmnt.setString(1, email);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {

                u.id = result.getInt(1);
                u.rol = 1;
                u.email = result.getString(3);
                u.pass = result.getString(4);
                u.ci = result.getString(5);
                u.fullname = result.getString(6);
            }
            System.out.println(u.toLISTARUSUtable());
        } catch (Exception e) {
            System.out.println("Excepcion al validar email DUsuario: " + e);
        }
        return u;
    }

    public boolean checkID(int id) {
        String sql = "SELECT * FROM Usuario WHERE usu_estado = ? AND usu_id = ? ;";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            stmnt.setInt(1, 1);
            stmnt.setInt(2, id);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Excepcion en checkID DUsuario: " + e);
        }
        return false;
    }

    public Map<String, Object> regusu(Map<String, Object> params) {

        Map<String, Object> resp = new HashMap<>();

        String sql = "INSERT INTO public.users(fullname, ci, estado, email, password, role_id ) "
                + "VALUES ( ?, ?, ?, ?, ?, ? );";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            stmnt.setString(1, (String) params.get("fullname"));
            stmnt.setString(2, (String) params.get("ci"));
            stmnt.setString(3, (String) params.get("estado"));
            stmnt.setString(4, (String) params.get("email"));
            stmnt.setString(5, (String) params.get("password"));
            stmnt.setInt(6, 1);

            int result = stmnt.executeUpdate();
            resp.put("resp", result);
            resp.put("error", null);
            con.desconectar();
        } catch (Exception e) {
            resp.put("error", "error al insertar en base de datos");
            return resp;
        }

        return resp;
       
    }

    public Map<String, Object> editusu(Map<String, Object> params) {
        Map<String, Object> resp = new HashMap<>();

        if (params.get("id") == null) {
            resp.put("error", "error de sintaxis, el valor id es obligatorio");
            return resp;
        }

        String sql = "UPDATE public.beneficiario "
                + "	SET ";

        if (params.get("id_encargado") != null) {
            sql += "id_usuario = " + params.get("id_encargado") + ", ";
        }

        if (params.get("nombre") != null) {
            sql += "nombre = \'" + params.get("nombre") + "\', ";
        }

        if (params.get("situacion") != null) {
            sql += "situacion = \'" + params.get("situacion") + "\', ";
        }

        if (params.get("fecha_nac") != null) {
            sql += "fecha_nacimiento = \'" + params.get("fecha_nac") + "\', ";
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

    public Map<String, Object> elimusu(Map<String, Object> params) {

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
            return resp;
        } catch (Exception e) {
            System.out.println("Excepcion al eliminar BENEFICIARIO de la base de datos: " + e);
            resp.put("error", "Excepcion al eliminar BENEFICIARIO de la base de datos");
            return resp;
        }


    }


}
