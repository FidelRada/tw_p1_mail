/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

/**
 *
 * @author fidel
 */

//import Datos.Actividad;
//import Datos.DActividad;
import Datos.DDocumento;
import Datos.Documento;
import Datos.Respuesta;
import java.util.ArrayList;

public class NDocumento {

    //LISTARDOC[ACTIVIDAD]
    public String listardoc(String params) {
        String msg = "";
        String[] values = params.split(",");
        if (values.length == 1) {
            int actividadParam = Integer.parseInt(values[0].trim());
            String msgErr = "";
            boolean ok = true;
            if (values[0].trim().length() <= 0) {
                ok = false;
                msgErr = "ACTIVIDAD invalida";
            }

            if (ok == true) {
                DDocumento duObj = new DDocumento();
                ArrayList<Documento> docsResult = duObj.listardoc(actividadParam);
                if (!docsResult.isEmpty()) {
                    String res = "<h2> Lista de Documentos </h2>\n"
                            + "<table border=1>\n"
                            + "<tr>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ID</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ID_ACTIVIDAD</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">NOMBRE</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">DESCRIPCION</td>"
                            + "</tr>\n";
                    for (Documento doc : docsResult) {
                        res += doc.toLISTARDOCtable();
                    }
                    res += "</table>";
                    msg
                            = "Content-Type:text/html;\r\n<html>"
                            + "<body>\n"
                            + "  <h2> COMANDO: LISTARDOC[ACTIVIDAD] </h2>\n"
                            + res
                            + "</body>"
                            + "</html>";
                    return msg;
                } else {
                    msg
                            = "Content-Type:text/html;\r\n<html>"
                            + "<body>\n"
                            + "  <h2> COMANDO: LISTARDOC[ACTIVIDAD] </h2>\n"
                            + "  <h4>No se encontro registros con los parametros proporcionados</h4>\n"
                            + "</body>"
                            + "</html>";
                }
            } else {
                msg
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "  <h1> EXCEPCION AL SELECCIONAR DOCUMENTOS </h1>\n"
                        + "  <h3>EXCEPCION: " + msgErr + "</h3>\n"
                        + "  <h2> COMANDO: LISTARDOC[PERIODO] </h2>\n"
                        + "  <p>Si desea obviar alguno introduzca '0' </p>\n"
                        + "  <h3>Ejemplos</h3>\n"
                        + "  <ul>\n"
                        + "      <li>LISTARDOC[0] retorna todos los documentos</li>\n"
                        + "      <li>LISTARDOC[1] retorna las documentos donde el id_actividad  =1</li>\n"
                        + "  </ul>\n"
                        + "</body>"
                        + "</html>";
            }
        } else {
            msg
                    = "Content-Type:text/html;\r\n<html>"
                    + "<body>\n"
                    + "  <h1> EXCEPCION AL SELECCIONAR DOCUMENTO </h1>\n"
                    + "  <h2> COMANDO: LISTARDOC[ACTIVIDAD] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros, si desea obviar alguno introduzca '0' </p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>LISTARDOC[0] retorna todos los documentos</li>\n"
                    + "      <li>LISTARDOC[1] retorna las documentos donde el id_actividad  =1</li>\n"
                    + "  </ul>\n"
                    + "</body>"
                    + "</html>";
        }
        return msg;
    }

    //REGDOC[ACTIVIDAD,NOMBRE,DESCRIPCION]
    public String regdoc(String params) {
        String msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 3) {

            int actividadParam = Integer.parseInt(values[0].trim());
            String nombreParam = values[1].trim();
            String descripcionParam = values[2].trim();
            boolean ok = true;
            String msgErr = "";

            if (values[0].trim().length() <= 0) {
                msgErr = "Actividad no valida esta vacio!";
                ok = false;
            }
            if (values[1].trim().length() <= 0) {
                msgErr = "Nombre no valido esta vacio!";
                ok = false;
            }
            if (values[2].trim().length() <= 0) {
                msgErr = "Descripcion no valida esta vacio!";
                ok = false;
            }

            if (ok == true) {
                DDocumento duObj = new DDocumento();
                Respuesta result = duObj.regdoc(actividadParam, nombreParam, descripcionParam);
                msg
                        += "<h1> REGDOC EJECUTADO </h1>\n"
                        + "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                        += "<h1> EXCEPCION AL REGISTRAR DOCUMENTO </h1>\n"
                        + "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                    += " <h1> EXCEPCION AL INSERTAR DOCUMENTO </h1>\n"
                    + "  <h2> COMANDO: REGDOC[ACTIVIDAD,NOMBRE,DESCRIPCION] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>REGDOC[1, ejemplo.pdf, esta es una descripcion de ejemplo ggg]</li>\n"
                    + "      <li>REGACT[2, ejemplo2.txt, esta es una descripcion de ejemplo2 gegege]</li>\n"
                    + "  </ul>\n";
        }
        msg
                += "</body>"
                + "</html>";
        return msg;
    }

    //EDITDOC[ID,NOMBRE,DESCRIPCION]
    public String editdoc(String params) {
        String msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 3) {

            int idParam = Integer.parseInt(values[0].trim());
            String nombreParam = values[1].trim();
            String descripcionParam = values[2].trim();
            boolean ok = true;
            String msgErr = "";

            if (values[0].trim().length() <= 0 || idParam <= 0) {
                msgErr = "Id no valida esta vacio! o id=0";
                ok = false;
            }
            if (values[1].trim().length() <= 0) {
                msgErr = "Nombre no valido esta vacio!";
                ok = false;
            }
            if (values[2].trim().length() <= 0) {
                msgErr = "Descripcion no valida esta vacio!";
                ok = false;
            }

            if (ok == true) {
                DDocumento duObj = new DDocumento();
                Respuesta result = duObj.editdoc(idParam, nombreParam, descripcionParam);
                msg
                        += "<h1> EDITDOC EJECUTADO </h1>\n"
                        + "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                        += "<h1> EXCEPCION AL EDITAR DOCUMENTO </h1>\n"
                        + "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                    += " <h1> EXCEPCION AL EDITAR DOCUMENTO </h1>\n"
                    + "  <h2> COMANDO: EDITDOC[ACTIVIDAD,NOMBRE,DESCRIPCION] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>EDITDOC[1, ejemplo.pdf, esta es una descripcion de ejemplo ggg]</li>\n"
                    + "      <li>EDITDOC[2, ejemplo2.txt, esta es una descripcion de ejemplo2 gegege]</li>\n"
                    + "  </ul>\n";
        }
        msg
                += "</body>"
                + "</html>";
        return msg;
    }

    //ELIMDOC[ID,NOMBRE,DESCRIPCION]
    public String elimdoc(String params) {
        String msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 1) {

            int idParam = Integer.parseInt(values[0].trim());
            boolean ok = true;
            String msgErr = "";

            if (values[0].trim().length() <= 0) {
                msgErr = "id no valido esta vacio!";
                ok = false;
            }

            if (ok == true) {
                DDocumento duObj = new DDocumento();
                Respuesta result = duObj.elimdoc(idParam);
                msg
                        += "<h1> ELIMDOC EJECUTADO </h1>\n"
                        + "<h3>RESPUEEDITDOCSTA: " + result.msg + "</h3>\n";
            } else {
                msg
                        += "<h1> EXCEPCION AL ELIMINAR DOCUMENTO </h1>\n"
                        + "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                    += " <h1> EXCEPCION AL ELIMINAR DOCUMENTO </h1>\n"
                    + "  <h2> COMANDO: ELIMDOC[ID] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>ELIMDOC[1] Elimina el Documento con el id=1</li>\n"
                    + "      <li>ELIMDOC[2] Elimina el Documento con el id=2</li>\n"
                    + "  </ul>\n";
        }
        msg
                += "</body>"
                + "</html>";
        return msg;
    }
    
    public static void main(String[] args) {
        NDocumento ND = new NDocumento();
        
    }

}
