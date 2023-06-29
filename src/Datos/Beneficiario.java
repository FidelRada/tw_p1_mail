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

    public int id = 0;
    public int id_usuario = 0;
    public String nombre = "";
    public String situaion = "";
    public String fecha_nac = "";

    public Beneficiario(){}
    
    public Beneficiario(int id, int id_usuario, String nombre, String situaion, String fecha_nac) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.situaion = situaion;
        this.fecha_nac = fecha_nac;
    }

    public String toLISTARCLItable() {
        return "<tr>"
                + "<td>" + this.id + "</td>"
                + "<td>" + this.nombre + "</td>"
                + "<td>" + this.situaion + "</td>"
                + "<td>" + this.fecha_nac + "</td>"
                + "</tr>\n";
    }

}
