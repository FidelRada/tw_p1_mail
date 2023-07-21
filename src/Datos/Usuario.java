/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author fidel
 */
public class Usuario {

    public long id = 0;
    public String fullname = "";
    public String ci = "";
    public String estado = "";
    public String email = "";
    public String pass = "";
    public int rol = 0;

    public Usuario() {
    }

    ;
    
    public Usuario(long id, String ci, String fullname, String email, String estado) {
        this.id = id;
        this.ci = ci;
        this.fullname = fullname;
        this.email = email;
        this.estado = estado;
    }

    public String toLISTARUSUtable() {
        return "<tr>"
                + "<td>" + this.id + "</td>"
                + "<td>" + this.rol + "</td>"
                + "<td>" + this.email + "</td>"
                + "<td>" + this.ci + "</td>"
                + "<td>" + this.fullname + "</td>"
                + "</tr>\n";
    }

    @Override
    public String toString() {
        //return "Usuario{" + "id=" + id + ", fullname=" + fullname + ", ci=" + ci + ", estado=" + estado + ", email=" + email + ", pass=" + pass + ", rol=" + rol + '}';
        return "[" + id + ", " + fullname + ", " + ci + ", " + estado + ", " + email + ", " + pass + ", " + rol + ']';
    }

    public String get(String i) {
        if (i.equals("id")) {
            return String.valueOf(this.id);
        }
        
        if (i.equals("fullname")) {
            return this.fullname;
        }
        
        if (i.equals("ci")) {
            return this.ci;
        }
        
        if (i.equals("email")) {
            return this.email;
        }
        
        if (i.equals("estado")) {
            return this.estado;
        }
        return null;
    }

}
