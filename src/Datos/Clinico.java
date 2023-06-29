/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author gstevenvalverde
 */
public class Clinico {
    
    public int cli_id = 0;
    public String cli_nombre_doctor = "";
    public String cli_motivo = "";
    public String cli_prescripcion_med = "";
    public String cli_observaciones = "";
    public String cli_created_at = "";
    public String cli_updated_at = "";
    
    public int cli_id_beneficiario = 0;

    
    public Clinico(int id, String nombre_doctor, String motivo, String prescripcion_med, 
            String observaciones, int id_beneficiario, String created_at, String updated_at) {
        this.cli_id = id;
        this.cli_nombre_doctor = nombre_doctor;
        this.cli_motivo = motivo;
        this.cli_prescripcion_med = prescripcion_med;
        this.cli_observaciones = observaciones;
        this.cli_created_at = created_at;
        this.cli_updated_at = updated_at;
        this.cli_id_beneficiario = id_beneficiario;
    }

    public String toLISTARCLItable() {
        return "<tr>"
                + "<td>" + this.cli_id + "</td>"
                + "<td>" + this.cli_nombre_doctor + "</td>"
                + "<td>" + this.cli_motivo + "</td>"
                + "<td>" + this.cli_prescripcion_med + "</td>"
                + "<td>" + this.cli_observaciones + "</td>"
                + "<td>" + this.cli_created_at + "</td>"
                + "<td>" + this.cli_updated_at + "</td>"
                + "</tr>\n";
    }
}