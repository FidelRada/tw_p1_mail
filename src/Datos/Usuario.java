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

    public int usu_id = 0;
    public int usu_rol = 0;
    public String fullname = "";
    public String ci = "";
    public String estado= "";
    public String pass = "";
    public String email = "";
    
    
    public Usuario(){};
    
    public Usuario(int id, int rol, String email, String pass, String ci, String fullname) {
        this.usu_id = id;
        this.usu_rol = rol;
        this.email = email;
        this.pass = pass;
        this.ci = ci;
        this.fullname = fullname;
    }
    
    public String toLISTARUSUtable(){
        return "<tr>" + 
                "<td>" + this.usu_id + "</td>" +
                "<td>" + this.usu_rol + "</td>" + 
                "<td>" + this.email + "</td>" +
                "<td>" + this.ci + "</td>" +
                "<td>" + this.fullname + "</td>" +
                "</tr>\n";
    }

}
