/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author fidel
 */

/*
create table Documento(
	doc_id serial primary key,
	doc_actividad int not null,
	doc_nombre varchar(50) not null,
	doc_desc varchar(300) not null,
	doc_url varchar(150) not null,
	doc_estado int not null,
	
	foreign key(doc_actividad) references Actividad(act_id)
);
 */
public class Documento {

    public int doc_id = 0;
    public int doc_actividad = 0;
    public String doc_nombre = "";
    public String doc_desc = "";
    public String doc_url = "";

    public Documento(int id, int actividad, String nombre, String descripcion, String urlP) {
        this.doc_id = id;
        this.doc_actividad = actividad;
        this.doc_nombre = nombre;
        this.doc_desc = descripcion;
        this.doc_url = urlP;
    }

    public String toLISTARDOCtable() {
        return "<tr>"
                + "<td>" + this.doc_id + "</td>"
                + "<td>" + this.doc_actividad + "</td>"
                + "<td>" + this.doc_nombre + "</td>"
                + "<td>" + this.doc_desc + "</td>"
                + // "<td>" + this.doc_url + "</td>" +
                "</tr>\n";
    }
    @Override
    public String toString() {
        return doc_id + " " + doc_nombre + " " + doc_actividad + " " + doc_desc + " " + doc_url;
    }
    
}
