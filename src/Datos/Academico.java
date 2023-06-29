/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author gstevenvalverde
 */
public class Academico {
    public int acad_id = 0;
    public String acad_grado_escolar = "";
    public String acad_nombre_colegio = "";
    public String acad_direccion_colegio = "";
    public String acad_desempenho = "";
    public String acad_created_at = "";
    public String acad_updated_at = "";
    
    public int acad_id_beneficiario = 0;

    
    public Academico(int id, String grado_escolar, String nombre_colegio, String direccion_colegio, 
            String desempenho, int id_beneficiario, String created_at, String updated_at) {
        this.acad_id = id;
        this.acad_grado_escolar = grado_escolar;
        this.acad_nombre_colegio = nombre_colegio;
        this.acad_direccion_colegio = direccion_colegio;
        this.acad_desempenho = desempenho;
        this.acad_created_at = created_at;
        this.acad_updated_at = updated_at;
        this.acad_id_beneficiario = id_beneficiario;
    }

    public String toLISTARACADtable() {
        return "<tr>"
                + "<td>" + this.acad_id + "</td>"
                + "<td>" + this.acad_grado_escolar + "</td>"
                + "<td>" + this.acad_nombre_colegio + "</td>"
                + "<td>" + this.acad_direccion_colegio + "</td>"
                + "<td>" + this.acad_desempenho + "</td>"
                + "<td>" + this.acad_created_at + "</td>"
                + "<td>" + this.acad_updated_at + "</td>"
                + "</tr>\n";
    }
    
}