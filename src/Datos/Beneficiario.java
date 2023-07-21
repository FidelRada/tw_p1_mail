/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author fidel
 */
public class Beneficiario {

    public long id;
    public String nombre;
    public String situacion;
    public String fecha_nac;
    public long id_usuario;

    public Beneficiario() {
    }

    public Beneficiario(long id, String nombre, String situacion, String fecha_nac, long id_usuario) {
        this.id = id;
        this.nombre = nombre;
        this.situacion = situacion;
        this.fecha_nac = fecha_nac;
        this.id_usuario = id_usuario;
    }

    public String get(String i) {
        
        if (i.equals("id")) {
            return String.valueOf(this.id);
        }
        
        if (i.equals("id_usuario")) {
            return String.valueOf(this.id_usuario);
        }

        if (i.equals("nombre")) {
            return this.nombre;
        }

        if (i.equals("situacion")) {
            return this.situacion;
        }

        if (i.equals("fecha_nac")) {
            return this.fecha_nac;
        }

        return null;
    }

    public String toLISTARCLItable() {
        return "<tr>"
                + "<td>" + this.id + "</td>"
                + "<td>" + this.nombre + "</td>"
                + "<td>" + this.situacion + "</td>"
                + "<td>" + this.fecha_nac + "</td>"
                + "</tr>\n";
    }

}
