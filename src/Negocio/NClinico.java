/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.Clinico;
import Datos.DClinico;
import java.util.ArrayList;
import Datos.Respuesta;

/**
 *
 * @author gstevenvalverde
 */
public class NClinico {
    
    public String listarcli(String params) {
        String msg = "";
        String[] values = params.split(",");
        if (values.length == 1) {
            int beneficiarioParam = Integer.parseInt(values[0].trim());
            String msgErr = "";
            boolean ok = true;
            if (values[0].trim().length() <= 0) {
                ok = false;
                msgErr = "ficha Clinnica invalida";
            }

            if (ok == true) {
                DClinico duObj = new DClinico();
                ArrayList<Clinico> docsResult = duObj.listarCli(beneficiarioParam);
                if (!docsResult.isEmpty()) {
                    String res = "<h2> Lista de Documentos </h2>\n"
                            + "<table border=1>\n"
                            + "<tr>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ID</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ID_ACTIVIDAD</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">NOMBRE</td>"
                            + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">DESCRIPCION</td>"
                            + "</tr>\n";
                    for (Clinico doc : docsResult) {
                        res += doc.toLISTARCLItable();
                    }
                    res += "</table>";
                    msg
                            = "Content-Type:text/html;\r\n<html>"
                            + "<body>\n"
                            + "  <h2> COMANDO: LISTARCLI[BENEFICIARIO] </h2>\n"
                            + res
                            + "</body>"
                            + "</html>";
                    return msg;
                } else {
                    msg
                            = "Content-Type:text/html;\r\n<html>"
                            + "<body>\n"
                            + "  <h2> COMANDO: LISTARCLI[BENEFICIARIO] </h2>\n"
                            + "  <h4>No se encontro registros con los parametros proporcionados</h4>\n"
                            + "</body>"
                            + "</html>";
                }
            } else {
                msg
                        = "Content-Type:text/html;\r\n<html>"
                        + "<body>\n"
                        + "  <h1> EXCEPCION AL SELECCIONAR FICHAS CLINICAS </h1>\n"
                        + "  <h3>EXCEPCION: " + msgErr + "</h3>\n"
                        + "  <h2> COMANDO: LISTARCLI[BENEFICIARIO] </h2>\n"
                        + "  <p>Si desea obviar alguno introduzca '0' </p>\n"
                        + "  <h3>Ejemplos</h3>\n"
                        + "  <ul>\n"
                        + "      <li>LISTARCLI[0] retorna todos las fichas clinicas</li>\n"
                        + "      <li>LISTARCLI[1] retorna las fichas clinicas donde el id_beneficiario  =1</li>\n"
                        + "  </ul>\n"
                        + "</body>"
                        + "</html>";
            }
        } else {
            msg
                    = "Content-Type:text/html;\r\n<html>"
                    + "<body>\n"
                    + "  <h1> EXCEPCION AL SELECCIONAR FICHA CLINICA </h1>\n"
                    + "  <h2> COMANDO: LISTARCLI[BENEFICIARIO] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros, si desea obviar alguno introduzca '0' </p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>LISTARCLI[0] retorna todos las fichas clinicas</li>\n"
                    + "      <li>LISTARCLI[1] retorna las fichas clinicas donde el id_beneficiario  =1</li>\n"
                    + "  </ul>\n"
                    + "</body>"
                    + "</html>";
        }
        return msg;
    }

    
    public String regcli(String params) {
        String msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 5) {

            int beneficiarioParam = Integer.parseInt(values[0].trim());
            String nombreDoctorParam = values[1].trim();
            String motivoParam = values[2].trim();
            String prescripcionMedParam = values[3].trim();
            String observacionesParam = values[4].trim();
            boolean ok = true;
            String msgErr = "";

            if (values[0].trim().length() <= 0) {
                msgErr = "Beneficiario no valido, esta vacio!";
                ok = false;
            }
            if (values[1].trim().length() <= 0) {
                msgErr = "Nombre de Doctor no valido, esta vacio!";
                ok = false;
            }
            if (values[2].trim().length() <= 0) {
                msgErr = "Motivo no valido, esta vacio!";
                ok = false;
            }
            if (values[3].trim().length() <= 0) {
                msgErr = "Prescripcion Medica no valida, esta vacio!";
                ok = false;
            }
            if (values[4].trim().length() <= 0) {
                msgErr = "Observaciones no validas, esta vacio!";
                ok = false;
            }

            if (ok == true) {
                DClinico duObj = new DClinico();
                Respuesta result = duObj.regCli(beneficiarioParam, nombreDoctorParam, motivoParam, prescripcionMedParam, observacionesParam);
                msg
                        += "<h1> REGCLI EJECUTADO </h1>\n"
                        + "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                        += "<h1> EXCEPCION AL REGISTRAR FICHA CLINICA </h1>\n"
                        + "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                    += " <h1> EXCEPCION AL INSERTAR FICHA CLINICA </h1>\n"
                    + "  <h2> COMANDO: REGCLI[BENEFICIARIO,NOMBRE_DOCTOR,MOTIVO,PRESCRIPCION_MED,OBSERVACIONES] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>REGCLI[1, Irren Ma, dolencia de muela, tabletas, cepillarse los dientes 3 veces al dia]</li>\n"
                    + "  </ul>\n";
        }
        msg
                += "</body>"
                + "</html>";
        return msg;
    }

    
    public String editcli(String params) {
        String msg
                = "Content-Type:text/html;\r\n<html>"
                + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 5) {

            int beneficiarioParam = Integer.parseInt(values[0].trim());
            String nombreDoctorParam = values[1].trim();
            String motivoParam = values[2].trim();
            String prescripcionMedParam = values[3].trim();
            String observacionesParam = values[4].trim();
            boolean ok = true;
            String msgErr = "";

            if (values[0].trim().length() <= 0) {
                msgErr = "Beneficiario no valido, esta vacio!";
                ok = false;
            }
            if (values[1].trim().length() <= 0) {
                msgErr = "Nombre de Doctor no valido, esta vacio!";
                ok = false;
            }
            if (values[2].trim().length() <= 0) {
                msgErr = "Motivo no valido, esta vacio!";
                ok = false;
            }
            if (values[3].trim().length() <= 0) {
                msgErr = "Prescripcion Medica no valida, esta vacio!";
                ok = false;
            }
            if (values[4].trim().length() <= 0) {
                msgErr = "Observaciones no validas, esta vacio!";
                ok = false;
            }

            if (ok == true) {
                DClinico duObj = new DClinico();
                Respuesta result = duObj.regCli(beneficiarioParam, nombreDoctorParam, motivoParam, prescripcionMedParam, observacionesParam);
                msg
                        += "<h1> EDITCLI EJECUTADO </h1>\n"
                        + "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg
                        += "<h1> EXCEPCION AL EDITAR FICHA CLINICA </h1>\n"
                        + "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg
                    += " <h1> EXCEPCION AL EDITAR FICHA CLINICA </h1>\n"
                    + "  <h2> COMANDO: EDITCLI[BENEFICIARIO,NOMBRE_DOCTOR,MOTIVO,PRESCRIPCION_MED,OBSERVACIONES] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>EDITCLI[1, Irren Ma, dolencia de muela, tabletas, cepillarse los dientes 3 veces al dia]</li>\n"
                    + "  </ul>\n";
        }
        msg
                += "</body>"
                + "</html>";
        return msg;
    }
    
}
