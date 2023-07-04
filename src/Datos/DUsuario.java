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
import java.util.Map;

public class DUsuario {

    private DbConn con;

    public DUsuario() {
        this.con = DbConn.getInstance();
    }

    public ArrayList<Usuario> listarTodos() {
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
        } catch (Exception e) {
            System.out.println("Excepcion al listarTodos DUsuario: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Rol> listarRoles() {
        ArrayList<Rol> lista = new ArrayList<>();
        String sql = "SELECT * FROM Rol WHERE rol_estado = 1";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                lista.add(new Rol(result.getInt(1),
                        result.getString(2),
                        result.getString(3)));
            }
        } catch (Exception e) {
            System.out.println("Excepcion al listarRoles DUsuario: " + e.getMessage());
        }
        return lista;
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

    public ArrayList<Usuario> listarusu(String email, String CI, String fullname) {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql;
        if (email.equals("0") && CI.equals("0") && fullname.equals("0")) {
            sql = "SELECT * FROM Usuario WHERE usu_estado = 1";
        } else {
            sql = "SELECT * FROM Usuario WHERE usu_estado = 1 ";
            if (!email.equals("0")) {
                sql += "AND usu_email like \'%" + email + "%\' ";
            }
            if (!CI.equals("0")) {
                sql += "AND usu_ci like \'%" + CI + "%\' ";
            }
            if (!fullname.equals("0")) {
                sql += "AND usu_fullname like \'%" + fullname + "%\' ";
            }
        }
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                /*lista.add(new Usuario(result.getInt(1),
                result.getInt(2),
                result.getString(3),
                result.getString(4),
                result.getString(5),
                result.getString(6)));*/
            }
        } catch (Exception e) {
            System.out.println("Excepcion en listarusu DUsuario: " + e.getMessage());
        }
        return lista;
    }

    public Respuesta regusu(String rolParam, String emailParam, String passwordParam, String ciParam, String fullnameParam) {
        ArrayList<Usuario> existentUsers = listarTodos();
        boolean nuevoUsr = true;
        for (Usuario usr : existentUsers) {
            if (usr.ci.equals(ciParam) || usr.email.equals(emailParam)) {
                nuevoUsr = false;
                break;
            }
        }
        if (nuevoUsr == false) {
            return new Respuesta("Usuario ya existe", false);
        }

        ArrayList<Rol> roles = listarRoles();
        boolean rolValido = false;
        for (Rol rol : roles) {
            if (rol.rol_id == Integer.valueOf(rolParam)) {
                rolValido = true;
                break;
            }
        }
        if (rolValido == false) {
            return new Respuesta("Rol no valido", false);
        }
        String sql = "INSERT INTO Usuario(usu_rol, usu_email, usu_pass, usu_ci, usu_fullname, usu_estado) "
                + "VALUES (?, ?, ?, ?, ?, 1)";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            stmnt.setInt(1, Integer.valueOf(rolParam));
            stmnt.setString(2, emailParam);
            stmnt.setString(3, passwordParam);
            stmnt.setString(4, ciParam);
            stmnt.setString(5, fullnameParam);
            int result = stmnt.executeUpdate();
            con.desconectar();
            return new Respuesta("Usuario registrado exitosamente", true);
        } catch (Exception e) {
            System.out.println("Excepcion al regusu DUsuario: " + e);
        }
        return new Respuesta("Excepcion en regusu DUsuario algo salio mal", false);
    }

    public Respuesta editusu(String idParam, String rolParam, String emailParam, String passwordParam, String ciParam, String fullnameParam) {
        boolean existeUsu = checkID(Integer.valueOf(idParam));
        if (existeUsu == false) {
            return new Respuesta("No existe usuario con ID: " + idParam, false);
        }

        String sql = "UPDATE Usuario SET ";
        boolean chgFullname = false;
        boolean chgrol = false;
        boolean chgEmail = false;
        boolean chgPwd = false;
        boolean chgci = false;
        Map<String, Integer> val = new HashMap<String, Integer>();
        val.put("id_rol", 0);
        val.put("email", 0);
        val.put("password", 0);
        val.put("ci", 0);
        val.put("fullname", 0);
        int nroParams = 0;
        if (!rolParam.equals("0")) {
            chgrol = true;
            sql += "id_rol = ? ";
            val.put("id_rol", 1);
            nroParams++;
        }
        if (!emailParam.equals("0")) {
            chgEmail = true;
            sql += sql.contains("=") == true ? ", email = ? " : "email = ? ";
            val.put("usu_email", nroParams + 1);
            nroParams++;
        }
        if (!passwordParam.equals("0")) {
            chgPwd = true;
            sql += sql.contains("=") == true ? ", password = ? " : "password = ? ";
            val.put("usu_password", nroParams + 1);
            nroParams++;
        }
        if (!ciParam.equals("0")) {
            chgci = true;
            sql += sql.contains("=") == true ? ", ci = ? " : "ci = ? ";
            val.put("usu_ci", nroParams + 1);
            nroParams++;
        }
        if (!fullnameParam.equals("0")) {
            chgFullname = true;
            sql += sql.contains("=") == true ? ", fullname = ? " : "fullname = ? ";
            val.put("usu_fullname", nroParams + 1);
            nroParams++;
        }

        boolean nuevoUsr = true;
        if (chgEmail == true || chgci == true) {
            ArrayList<Usuario> existentUsers = listarTodos();
            for (Usuario usr : existentUsers) {
                if ((usr.ci.equals(ciParam) || usr.email.equals(emailParam)) && (usr.id != Integer.valueOf(idParam))) {
                    nuevoUsr = false;
                    break;
                }
            }
            if (nuevoUsr == false) {
                return new Respuesta("Email o CI ya en uso", false);
            }
        }

        if (chgrol == true) {
            ArrayList<Rol> roles = listarRoles();
            boolean rolValido = false;
            for (Rol rol : roles) {
                if (rol.rol_id == Integer.valueOf(rolParam)) {
                    rolValido = true;
                    break;
                }
            }
            if (rolValido == false) {
                return new Respuesta("Rol no valido", false);
            }
        }
        if (sql.contains("=") == false) {
            return new Respuesta("EDITUSU ejecutado", true);
        }
        try {
            sql += "WHERE usu_id = ?";
            val.put("usu_id", nroParams + 1);
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            if (chgrol == true) {
                stmnt.setInt(val.get("id_rol"), Integer.valueOf(rolParam));
            }
            if (chgEmail == true) {
                stmnt.setString(val.get("email"), emailParam);
            }
            if (chgPwd == true) {
                stmnt.setString(val.get("password"), passwordParam);
            }
            if (chgci == true) {
                stmnt.setString(val.get("ci"), ciParam);
            }
            if (chgFullname == true) {
                stmnt.setString(val.get("fullname"), fullnameParam);
            }
            stmnt.setInt(val.get("id"), Integer.valueOf(idParam));
            int result = stmnt.executeUpdate();
            con.desconectar();
            return new Respuesta("Se edito el usuario con exito", true);
        } catch (Exception e) {
            System.out.println("Excepcion en editusu DUsuario: " + e);
        }
        return new Respuesta("Excepcion en editusu DUusario", false);
    }

    public Respuesta elimusu(String id) {
        boolean usrValido = false;
        ArrayList<Usuario> users = listarTodos();
        for (Usuario usr : users) {
            if (usr.id == Integer.valueOf(id)) {
                usrValido = true;
                break;
            }
        }
        if (usrValido == false) {
            return new Respuesta("No existe usuario con id: " + id, false);
        }
        String sql = "UPDATE Usuario SET usu_estado = 0 WHERE usu_id = ?";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            stmnt.setInt(1, Integer.valueOf(Integer.valueOf(id)));
            int result = stmnt.executeUpdate();
            con.desconectar();
            return new Respuesta("Usuario eliminado exitosamente", true);
        } catch (Exception e) {
            System.out.println("Excepcion al elimusu DUsuario: " + e);
        }
        return new Respuesta("Excepcion algo salio mal", false);
    }

    public static void main(String[] args) {
        DUsuario du = new DUsuario();
        ArrayList<Usuario> L = du.listarTodos();
        System.out.println(L);
    }

}
