/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

/**
 *
 * @author fidel
 */
import Datos.Respuesta;
import java.util.List;
import Negocio.NUsuario;
//import Negocio.NActividad;
import Negocio.NDocumento;
//import Negocio.NPeriodo;
//import Negocio.NTarea;
import Datos.Usuario;

public class Guia {

    private int bandejaLength = 0;
    private POPService guiaPopObj;
    private SMTPService guiaSmtpObj;
    private String guiaCmdo = "";
    private String guiaReceptor = "";
    private String guiaParams = "";

    public Guia() {
        guiaPopObj = new POPService();
        bandejaLength = guiaPopObj.getBandejaLength();
    }

    public void listen() {
        guiaPopObj = new POPService();
        boolean subjectValido;
        boolean correoValido = false;
        Respuesta tienePermiso;
        if (guiaPopObj.getBandejaLength() > bandejaLength) {
            bandejaLength++;
            System.out.println("se detecto nuevo correo en la bandeja de entrada!");
            List<String> msg = guiaPopObj.getEmail(bandejaLength);
            System.out.println(msg);
            Usuario userEmisor = checkEmisor(msg);
            if (userEmisor.usu_id != 0) {
                correoValido = true;
            }
            subjectValido = checkSubject(msg);

            if (correoValido == true && subjectValido) {
                tienePermiso = Comandos.tienePermiso(guiaCmdo, userEmisor.usu_rol);
                if (tienePermiso.ok) {
                    System.out.println("Comando: " + this.guiaCmdo);
                    if (!this.guiaParams.equals("")) {
                        System.out.println("Parametros: " + this.guiaParams);
                    }
                    executeCmdo(this.guiaCmdo);
                } else {
                    System.out.println("SIN PERMISO: " + tienePermiso.msg);
                    sendResponseEmail("INVALIDO", tienePermiso.msg);
                }
            } else {
                if (correoValido == false) {
                    System.out.println("Emisor no valido...cerrando conx");
                    sendResponseEmail("INVALIDO", "Emisor no valido");
                } else {
                    System.out.println("Subject no valido...cerrando conx");
                    sendResponseEmail("INVALIDO", "Comando no valido.");
                }
            }
        }
        guiaPopObj.close();
    }

    public boolean checkSubject(List<String> emailMsg) {
        int i = -1;
        for (String line : emailMsg) {
            i++;
            if (line.contains("Subject:")) {
                String cmdo = line.split(":")[1];
                cmdo = cmdo.substring(1);
                /*while (!emailMsg.get(i + 1).contains("To:")) {
                    cmdo += emailMsg.get(i + 1);
                    i++;
                }*/
                if ("HELP".equals(cmdo)) {
                    this.guiaCmdo = cmdo;
                    return true;
                } else {
                    String cmdoVerbo = cmdo.split("\\[")[0];
                    boolean cmdoValido = checkCmd(cmdoVerbo);
                    if (cmdoValido == true) {
                        this.guiaCmdo = cmdoVerbo;
                        String[] array = cmdo.split("\\[");
                        if (array.length < 2) {
                            return false;
                        }
                        String parametros = cmdo.split("\\[")[1];
                        parametros = parametros.split("\\]")[0];
                        this.guiaParams = parametros;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkCmd(String cmdo) {
        switch (cmdo) {
            case Comandos.HELP:
                return true;
            case Comandos.LISTING_USUARIOS:
                return true;
            case Comandos.REGING_USUARIOS:
                return true;
            case Comandos.EDITING_USUARIOS:
                return true;
            case Comandos.DELITING_USUARIOS:
                return true;
            case Comandos.LISTING_INGRESOS:
                return true;
            case Comandos.REGING_INGRESOS:
                return true;
            case Comandos.EDITING_INGRESOS:
                return true;
            case Comandos.LISTING_BENEFICIARIO:
                return true;
            case Comandos.REGING_BENEFICIARIO:
                return true;
            case Comandos.EDITING_BENEFICIARIO:
                return true;
            case Comandos.DELITING_BENEFICIARIO:
                return true;
            case Comandos.LISTING_INFO:
                return true;
            case Comandos.REGING_INFO:
                return true;
            case Comandos.EDITING_INFO:
                return true;
            case Comandos.DELITING_INFO:
                return true;
            case Comandos.LISTING_INFO_CLINICOS:
                return true;
            case Comandos.REGING_INFO_CLINICOS:
                return true;
            case Comandos.EDITING_INFO_CLINICOS:
                return true;
            case Comandos.DELITING_INFO_CLINICOS:
                return true;
            case Comandos.LISTING_INFO_ACADEMICOS:
                return true;
            case Comandos.REGING_INFO_ACADEMICOS:
                return true;
            case Comandos.EDITING_INFO_ACADEMICOS:
                return true;
            case Comandos.DELITING_INFO_ACADEMICOS:
                return true;
            case Comandos.GETING_ALERTAS:
                return true;
            case Comandos.GETING_ESTADISTCAS:
                return true;
            case Comandos.GETING_REPORTES:
                return true;
            default:
                return false;
        }

    }

    public Usuario checkEmisor(List<String> emailMsg) {
        for (String line : emailMsg) {
            if (line.contains("Return-Path:")) {
                //guardar correo emisor
                int ini = line.indexOf("<");
                int fin = line.indexOf(">");
                String emisor = line.substring(ini + 1, fin);
                System.out.println("Emisor: " + emisor);
                this.guiaReceptor = emisor;
                NUsuario nusuObj = new NUsuario();
                System.out.println(nusuObj);
                return nusuObj.checkEmail(emisor);
            }
        }
        Usuario userVacio = new Usuario();
        return userVacio;
    }

    public void executeCmdo(String cmdo) {
        switch (cmdo) {
            case Comandos.HELP:
                executeHELP();
                
            case Comandos.LISTING_USUARIOS:
                //executeLISTING_USUARIOS(this.guiaParams);
                
            case Comandos.REGING_USUARIOS:
                //executeREGING_USUARIOS(this.guiaParams);
                
            case Comandos.EDITING_USUARIOS:
                //executeEDITING_USUARIOS(this.guiaParams);
                
            case Comandos.DELITING_USUARIOS:
                //executeDELITING_USUARIOS(this.guiaParams);
                
            case Comandos.LISTING_INGRESOS:
                //executeLISTING_INGRESOS(this.guiaParams);
                
            case Comandos.REGING_INGRESOS:
                //executeREGING_INGRESOS(this.guiaParams);
                
            case Comandos.EDITING_INGRESOS:
                //executeEDITING_INGRESOS(this.guiaParams);
                
            case Comandos.LISTING_BENEFICIARIO:
                //executeLISTING_BENEFICIARIO(this.guiaParams);
                
            case Comandos.REGING_BENEFICIARIO:
                //executeREGING_BENEFICIARIO(this.guiaParams);
                
            case Comandos.EDITING_BENEFICIARIO:
                //executeEDITING_BENEFICIARIO(this.guiaParams);
                
            case Comandos.DELITING_BENEFICIARIO:
                //executeDELITING_BENEFICIARIO(this.guiaParams);
                
            case Comandos.LISTING_INFO:
                //executeLISTING_INFO(this.guiaParams);
                
            case Comandos.REGING_INFO:
                //executeREGING_INFO(this.guiaParams);
                
            case Comandos.EDITING_INFO:
                //executeEDITING_INFO(this.guiaParams);
                
            case Comandos.DELITING_INFO:
                //executeDELITING_INFO(this.guiaParams);
                
            case Comandos.LISTING_INFO_CLINICOS:
                //executeLISTING_INFO_CLINICOS(this.guiaParams);
                
            case Comandos.REGING_INFO_CLINICOS:
                //executeREGING_INFO_CLINICOS(this.guiaParams);
            
            case Comandos.EDITING_INFO_CLINICOS:
                //executeEDITING_INFO_CLINICOS(this.guiaParams);
            
            case Comandos.DELITING_INFO_CLINICOS:
                //executeDELITING_INFO_CLINICOS(this.guiaParams);
            
            case Comandos.LISTING_INFO_ACADEMICOS:
                //executeLISTING_INFO_ACADEMICOS(this.guiaParams);
            
            case Comandos.REGING_INFO_ACADEMICOS:
                //executeREGING_INFO_ACADEMICOS(this.guiaParams);
            
            case Comandos.EDITING_INFO_ACADEMICOS:
                //executeEDITING_INFO_ACADEMICOS(this.guiaParams);
            
            case Comandos.DELITING_INFO_ACADEMICOS:
                //executeDELITING_INFO_ACADEMICOS(this.guiaParams);
            
            case Comandos.GETING_ALERTAS:
                //executeGETING_ALERTAS(this.guiaParams);
            
            case Comandos.GETING_ESTADISTCAS:
                //executeGETING_ESTADISTCAS(this.guiaParams);
            
            case Comandos.GETING_REPORTES:
                //executeGETING_REPORTES(this.guiaParams);
            
            default:
                 System.out.println("Comando no encontrado");   
        }

    }

    public void executeHELP() {
        String msg = "Content-Type:text/html;\r\n<html>"
                + "<body>\n"
                + "  <h1> BIENVENIDO AL MENU DE COMANDOS PARA EL PROYECTO GESTION DE ACTIVIDADES CUP/PSA </h1>\n"
                + "  <h3> AYUDA: HELP </h3>\n"
                + "  <h3> Gestionar Usuario </h3>\n" //CU1
                + "  <ul>\n"
                + "      <li>LISTARUSU[EMAIL, CI, FULLNAME]</li>\n"
                + "      <li>REGUSU[ROL,EMAIL,PASSWORD,CI,FULLNAME]</li>\n"
                + "      <li>EDITUSU[ID,ROL,EMAIL,PASSWORD,CI,FULLNAME]</li>\n"
                + "      <li>ELIMUSU[ID]</li>\n"
                + "  </ul>\n"
                + "<h3> Gestionar Documento </h3>\n" //CU2
                + "  <ul>\n"
                + "      <li>LISTARDOC[ACTIVIDAD]</li>\n"
                + "      <li>REGDOC[ACTIVIDAD,NOMBRE,DESCRIPCION]</li>\n"
                + "      <li>EDITDOC[ID,NOMBRE,DESCRIPCION]</li>\n"
                + "      <li>ELIMDOC[ID]</li>\n"
                + "  </ul>\n"
                + "  <h3> Gestionar Periodo </h3>\n" //CU3
                + "  <ul>\n"
                + "      <li>LISTARPER[NOMBRE, FINICIO, FFIN, DESCRIPCION]</li>\n"
                + "      <li>REGPER[NOMBRE,FINICIO,FFIN,DESCRIPCION]</li>\n"
                + "      <li>EDITPER[IDPER,NOMBRE,DESCRIPCION]</li>\n"
                + "      <li>ELIMPER[IDPER]</li>\n"
                + "  </ul>\n"
                + "<h3> Gestionar Actividad </h3>\n" //CU4
                + "  <ul>\n"
                + "      <li>LISTARACT[USER, PERIODO]</li>\n"
                + "      <li>REGACT[PERIODO,NOMBRE,DESCRIPCION,FECHAINI,FECHAFIN,OBSERVACION,USER]</li>\n"
                + "      <li>EDITACT[ID,NOMBRE,DESCRIPCION,ESTADO_A,OBSERVACION]</li>\n"
                + "      <li>ELIMACT[ID]</li>\n"
                + "  </ul>\n"
                + "  <h3> Gestionar Tarea </h3>\n" //CU5
                + "  <ul>\n"
                + "      <li>LISTARTAR[IDACTarea]</li>\n"
                + "      <li>REGTAR[IDACTarea,NOMBRE,DESCRIPCION,ESTADOT]</li>\n"
                + "      <li>EDITTAR[IDACTarea,NOMBRE,DESCRIPCION,ESTADOT]</li>\n"
                + "      <li>ELIMTAR[IDTAREA]</li>\n"
                + "  </ul>\n"
                + "  <h3> Gestionar Seguimiento de actividades </h3>\n" //CU6
                + "  <ul>\n"
                + "      <li>SEGACT[ID]</li>\n"
                + "  </ul>\n"
                + "  <h3> Informe final</h3>\n" //CU7
                + "  <ul>\n"
                + "      <li>INFPER[ID]</li>\n"
                + "  </ul>\n"
                + "  <h3> Gestionar Reportes y estadisticas </h3>\n" //CU8
                + "  <ul>\n"
                + "      <li>REPACT[ESTADO, ASIGNADO, INICION, FIN, OBS]</li>\n"
                + "  </ul>\n"
                + "</body>"
                + "</html>";
        sendResponseEmail("Estos son los comandos disponibles", msg);
    }

    public void sendResponseEmail(String sbjct, String msg) {
        this.guiaSmtpObj = new SMTPService();
        this.guiaSmtpObj.sendMessage(sbjct, this.guiaReceptor, msg);
        this.guiaSmtpObj.close();
    }
//
    ///*==============================
    //===== CU1 GESTIONAR USUARIO ====
    //==============================*/
    //public void executeLISTARUSU(String params) {
    //    NUsuario nuObj = new NUsuario();
    //    String msg = nuObj.listarusu(params);
    //    sendResponseEmail("RESPUESTA A PETICION LISTARUSU", msg);
    //}
//
    //public void executeREGUSU(String params) {
    //    NUsuario nuObj = new NUsuario();
    //    String msg = nuObj.regusu(params);
    //    sendResponseEmail("RESPUESTA A PETICION REGUSU", msg);
    //}
//
    //public void executeEDITUSU(String params) {
    //    NUsuario nuObj = new NUsuario();
    //    String msg = nuObj.editusu(params);
    //    sendResponseEmail("RESPUESTA A PETICION EDITUSU", msg);
    //}
//
    //public void executeELIMUSU(String params) {
    //    NUsuario nuObj = new NUsuario();
    //    String msg = nuObj.elimusu(params);
    //    sendResponseEmail("RESPUESTA A PETICION ELIMUSU", msg);
    //}
//
    ///*==============================
    //===== FIN GESTIONAR USUARIO ====
    //==============================*/
//
 /*=//====================================
    //===== CU6 SEGUIMIENTO DE ACTIVIDAD ====
    //=====================================*/
    //public void executeSEGACT(String param) {
    //    NActividad naObj = new NActividad();
    //    String msg = naObj.segact(param);
    //    sendResponseEmail("RESPUESTA A SEGACT", msg);
    //}
//
    ///*=====================================
    //===== FIN SEGUIMIENTO DE ACTIVIDAD ====
    //=====================================*/
//
 /*=//====================================
    //===== CU7 GESTIONAR REPORTES Y ESTADISTICAS ====
    //=====================================*/
    //public void executeINFPER(String param) {
    //    NActividad naObj = new NActividad();
    //    String msg = naObj.infper(param);
    //    sendResponseEmail("RESPUESTA A INFPER", msg);
    //}
//
    ///*=====================================
    //===== FIN SEGUIMIENTO DE ACTIVIDAD ====
    //=====================================*/
//
 /*=//====================================
    //===== CU8 GESTIONAR REPORTES Y ESTADISTICAS ====
    //=====================================*/
    //public void executeREPACT(String param) {
    //    NActividad naObj = new NActividad();
    //    String msg = naObj.repact(param);
    //    sendResponseEmail("RESPUESTA A REPACT", msg);
    //}
//
    ///*=====================================
    //===== FIN SEGUIMIENTO DE ACTIVIDAD ====
    //=====================================*/
//
 /*=//===============================
    //===== CU2 GESTIONAR DOCUMENTO ====
    //==================================*/
    //public void executeLISTARDOC(String params) {
    //    NDocumento nuObj = new NDocumento();
    //    String msg = nuObj.listardoc(params);
    //    sendResponseEmail("RESPUESTA A PETICION LISTARDOC", msg);
    //}
//
    //public void executeREGDOC(String params) {
    //    NDocumento nuObj = new NDocumento();
    //    String msg = nuObj.regdoc(params);
    //    sendResponseEmail("RESPUESTA A PETICION REGDOC", msg);
    //}
//
    //public void executeEDITDOC(String params) {
    //    NDocumento nuObj = new NDocumento();
    //    String msg = nuObj.editdoc(params);
    //    sendResponseEmail("RESPUESTA A PETICION EDITDOC", msg);
    //}
//
    //public void executeELIMDOC(String params) {
    //    NDocumento nuObj = new NDocumento();
    //    String msg = nuObj.elimdoc(params);
    //    sendResponseEmail("RESPUESTA A PETICION ELIMDOC", msg);
    //}
//
    ///*=================================
    //===== FIN GESTIONAR DOCUMENTO ====
    //===================================*/
//
 /*=//===============================
    //===== CU4 GESTIONAR ACTIVIDAD ====
    //==================================*/
    //public void executeLISTARACT(String params) {
    //    NActividad nuObj = new NActividad();
    //    String msg = nuObj.listaract(params);
    //    sendResponseEmail("RESPUESTA A PETICION LISTARACT", msg);
    //}
//
    //public void executeREGACT(String params) {
    //    NActividad nuObj = new NActividad();
    //    String msg = nuObj.regact(params);
    //    sendResponseEmail("RESPUESTA A PETICION REGACT", msg);
    //}
//
    //public void executeEDITACT(String params) {
    //    NActividad nuObj = new NActividad();
    //    String msg = nuObj.editact(params);
    //    sendResponseEmail("RESPUESTA A PETICION EDITACT", msg);
    //}
//
    //public void executeELIMACT(String params) {
    //    NActividad nuObj = new NActividad();
    //    String msg = nuObj.elimact(params);
    //    sendResponseEmail("RESPUESTA A PETICION ELIMACT", msg);
    //}
//
    ///*=================================
    //===== FIN GESTIONAR ACTIIVIDAD ====
    //===================================*/
//
 /*=//=============================
    //===== CU3 GESTIONAR PERIODO ====
    //==============================*/
    //public void executeLISTARPER(String params) {
    //    NPeriodo nuObj = new NPeriodo();
    //    String msg = nuObj.listPeriodo(params);
    //    sendResponseEmail("RESPUESTA A PETICION LISTARPER", msg);
    //}
//
    //public void executeREGTPER(String params) {
    //    NPeriodo nuObj = new NPeriodo();
    //    String msg = nuObj.regperiodo(params);
    //    sendResponseEmail("RESPUESTA A PETICION REGTPER", msg);
    //}
//
    //public void executeEDITPER(String params) {
    //    NPeriodo nuObj = new NPeriodo();
    //    String msg = nuObj.editper(params);
    //    sendResponseEmail("RESPUESTA A PETICION EDITPER", msg);
    //}
//
    //public void executeELIMPER(String params) {
    //    NPeriodo nuObj = new NPeriodo();
    //    String msg = nuObj.elimper(params);
    //    sendResponseEmail("RESPUESTA A PETICION ELIMPER", msg);
    //}
//
    ///*==============================
    //===== FIN GESTIONAR PERIODO ====
    //==============================*/
//
 /*=//=============================
    //===== CU5 GESTIONAR TAREA ====
    //==============================*/
    //public void executeLISTARTAR(String params) { //solo con e id de la tarea lista
    //    NTarea nuObj = new NTarea();
    //    String msg = nuObj.listareas(params);
    //    sendResponseEmail("RESPUESTA A PETICION LISTARPER", msg);
    //}
//
    //public void executeREGTAR(String params) {
    //    NTarea nuObj = new NTarea();
    //    String msg = nuObj.regtarea(params);
    //    sendResponseEmail("RESPUESTA A PETICION REGTPER", msg);
    //}
//
    //public void executeEDITTAR(String params) {
    //    NTarea nuObj = new NTarea();
    //    String msg = nuObj.editarea(params);
    //    sendResponseEmail("RESPUESTA A PETICION EDITPER", msg);
    //}
//
    //public void executeELIMTAR(String params) {
    //    NTarea nuObj = new NTarea();
    //    String msg = nuObj.elimtarea(params);
    //    sendResponseEmail("RESPUESTA A PETICION ELIMPER", msg);
    //}

    /*==============================
    ===== FIN GESTIONAR TAREA ====
    ==============================*/
}
