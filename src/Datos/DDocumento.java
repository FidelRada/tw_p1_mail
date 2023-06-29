package Datos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author fidel
 */
import Conexion.DbConn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DDocumento {

    private DbConn con;

    public DDocumento() {
        this.con = DbConn.getInstance();
    }

    public ArrayList<Documento> listarTodos() {
        ArrayList<Documento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Documents WHERE estado = 1";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            System.out.println(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
//                System.out.println(result.getInt(1) + " " + result.getString(2) + " " + result.getString(3) + " " + result.getInt(4) + " " + result.getInt(5));

                lista.add(new Documento(
                        result.getInt(1),
                        result.getInt(5),
                        result.getString(2),
                        "DescripcionNN",
                        result.getString(3)       
                ));
            }
        } catch (Exception e) {
            System.out.println("Excepcion al obtener usuarios DActividad: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Documento> listardoc(int doc_actividadParam) {
        ArrayList<Documento> lista = new ArrayList<>();
        String sql;
        if (doc_actividadParam == 0) {
            sql = "SELECT * FROM Documento WHERE doc_estado = 1";
        } else {
            sql = "SELECT * FROM Documento WHERE doc_estado = 1 ";
            if (doc_actividadParam > 0) {
                sql += "AND doc_actividad =" + doc_actividadParam;
            }
        }
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            ResultSet result = stmnt.executeQuery();
            con.desconectar();
            while (result.next()) {
                lista.add(new Documento(result.getInt(1),
                        result.getInt(2),
                        result.getString(3),
                        result.getString(4),
                        result.getString(5)));
            }
        } catch (Exception e) {
            System.out.println("Excepcion en listaract Ddocumento: " + e.getMessage());
        }
        return lista;
    }

    public Respuesta regdoc(int actividadParam, String nombreParam, String descripcionParam) {
        ArrayList<Documento> existenDocs = listarTodos();
        boolean nuevoDoc = true;
        for (Documento doc : existenDocs) {
            if (doc.doc_nombre.equals(nombreParam)) {
                nuevoDoc = false;
                break;
            }
        }
        if (nuevoDoc == false) {
            return new Respuesta("Documento ya existe", false);
        }
        String sql = "INSERT INTO Documento(doc_actividad, doc_nombre, doc_desc, doc_url, doc_estado) "
                + "VALUES (?, ?, ?,  ' ', 1)";
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            stmnt.setInt(1, actividadParam);
            stmnt.setString(2, nombreParam);
            stmnt.setString(3, descripcionParam);

            int result = stmnt.executeUpdate();
            con.desconectar();
            return new Respuesta("Documento registrado exitosamente", true);
        } catch (Exception e) {
            System.out.println("Excepcion al regdoc DDocumento: " + e);
        }
        return new Respuesta("Se ah registrado un documento con exito!", false);
    }

    public Respuesta editdoc(int idParam, String nombreParam, String descripcionParam) {
        //"UPDATE public.actividads SET inicio=?, fin=?, foto=?, servicio_id=?, updated_at=now() WHERE actividads.id = ?"
        String sql = "";

        if (nombreParam.equals("0") && descripcionParam.equals("0")) {
            return new Respuesta("Los 2 parametros que se puede editar estan vacios", false);
        }

        if (nombreParam.equals("0") && !descripcionParam.equals("0")) {
            sql = "UPDATE Documento SET doc_desc=? WHERE doc_id =" + idParam;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, descripcionParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DDocumento editar: " + e);
            }
        }

        if (!nombreParam.equals("0") && descripcionParam.equals("0")) {
            sql = "UPDATE Documento SET doc_nombre=? WHERE doc_id =" + idParam;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, nombreParam);
                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DDocumento editar: " + e);
            }
        }

        if (!nombreParam.equals("0") && !descripcionParam.equals("0")) {
            sql = "UPDATE Documento SET doc_nombre=?, doc_desc=? WHERE doc_id =" + idParam;
            try {
                PreparedStatement stmnt = con.conectar().prepareStatement(sql);
                stmnt.setString(1, nombreParam);
                stmnt.setString(2, descripcionParam);

                int res = stmnt.executeUpdate();
                con.desconectar();
            } catch (Exception e) {
                System.out.println("Error DDocumento editar: " + e);
            }
        }
        return new Respuesta("Se ah editado el documento con exito!", false); //puede que haya que modificar el mensaje de salida o respuesta
    }

    public Respuesta elimdoc(int idParam) {
        //"UPDATE public.actividads SET inicio=?, fin=?, foto=?, servicio_id=?, updated_at=now() WHERE actividads.id = ?"
        String sql = "UPDATE Documento SET doc_estado =0 WHERE doc_id =" + idParam;
        try {
            PreparedStatement stmnt = con.conectar().prepareStatement(sql);
            int res = stmnt.executeUpdate();
            con.desconectar();
        } catch (Exception e) {
            System.out.println("Error DDocumento eliiminar: " + e);
        }
        return new Respuesta("Se ah eliminado el documento con exito!", false); //puede que haya que modificar el mensaje de salida o respuesta
    }

    public static void main(String[] args) {
        DDocumento DD = new DDocumento();
        ArrayList<Documento> list = DD.listarTodos();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
