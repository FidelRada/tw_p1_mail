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

public class Comandos {
    //COMANDO HELP

    public static final String HELP = "HELP"; //CUALQUIERA
//CU1 GESTIONAR USUARIO
    public static final String LISTING_USUARIOS = "LISTING_USUARIOS"; //CUALQUIERA
    public static final String REGING_USUARIOS = "REGING_USUARIOS"; //AUTORIDAD
    public static final String EDITING_USUARIOS = "EDITING_USUARIOS"; //AUTORIDAD
    public static final String DELITING_USUARIOS = "DELITING_USUARIOS"; //AUTORIDAD

//CU2 GESTIONAR INGRESOS
    public static final String LISTING_INGRESOS = "LISTING_INGRESOS"; //CUALQUIERA
    public static final String REGING_INGRESOS = "REGING_INGRESOS"; //CUALQUIERA
    public static final String EDITING_INGRESOS = "EDITING_INGRESOS"; //CUALQUIERA

//CU3 GESTIONAR BENEFICIARIO
    public static final String LISTING_BENEFICIARIO = "LISTING_BENEFICIARIO"; //CUALQUIERA
    public static final String REGING_BENEFICIARIO = "REGING_BENEFICIARIO"; //AUTORIDAD
    public static final String EDITING_BENEFICIARIO = "EDITING_BENEFICIARIO"; //AUTORIDAD
    public static final String DELITING_BENEFICIARIO = "DELITING_BENEFICIARIO"; //AUTORIDAD

//CU4 GESTIONAR INFORMES DE EDUCADOR
    public static final String LISTING_INFO = "LISTING_INFO"; //AUTORIDAD
    public static final String REGING_INFO = "REGING_INFO"; //CUALQUIERA
    public static final String EDITING_INFO = "EDITING_INFO"; //CUALQUIERA
    public static final String DELITING_INFO = "DELITING_INFO"; //AUTORIDAD

//CU5 GESTIONAR INFORMES CLINICOS
    public static final String LISTING_INFO_CLINICOS = "LISTING_INFO_CLINICOS"; //CUALQUIERA
    public static final String REGING_INFO_CLINICOS = "REGING_INFO_CLINICOS"; //CUALQUIERA
    public static final String EDITING_INFO_CLINICOS = "EDITING_INFO_CLINICOS"; //CUALQUIERA
    public static final String DELITING_INFO_CLINICOS = "DELITING_INFO_CLINICOS"; //CUALQUIERA

//CU6 GESTIONAR INFORMES ACADEMICOS
    public static final String LISTING_INFO_ACADEMICOS = "LISTING_INFO_ACADEMICOS"; //CUALQUIERA
    public static final String REGING_INFO_ACADEMICOS = "REGING_INFO_ACADEMICOS"; //CUALQUIERA
    public static final String EDITING_INFO_ACADEMICOS = "EDITING_INFO_ACADEMICOS"; //CUALQUIERA
    public static final String DELITING_INFO_ACADEMICOS = "DELITING_INFO_ACADEMICOS"; //CUALQUIERA

//CU7 GESTIONAR ALERTAS
    public static final String GETING_ALERTAS = "GETING_ALERTAS"; // AUTORIDAD

//CU8 REPORTES Y ESTADISTICAS
    public static final String GETING_ESTADISTCAS = "GETING_ESTADISTCAS"; //AUTORIDAD
    public static final String GETING_REPORTES = "GETING_REPORTES"; //AUTORIDAD

    public static final String[] SOLO_AUTORIDAD = {REGING_USUARIOS, EDITING_USUARIOS, 
        DELITING_USUARIOS, REGING_BENEFICIARIO, 
        EDITING_BENEFICIARIO, DELITING_BENEFICIARIO, LISTING_INFO, 
        DELITING_INFO, GETING_ALERTAS, GETING_ESTADISTCAS, GETING_REPORTES};

    public static Respuesta tienePermiso(String cmd, int rol) {
        Respuesta resp = new Respuesta("exito cualquiera puede usar el comando", true);
        boolean soloAutoridad = false;
        for (String c : SOLO_AUTORIDAD) {
            if (c.equals(cmd)) {
                soloAutoridad = true;
                break;
            }
        }
        if (soloAutoridad) {
            if (rol == 1) {
                resp.ok = true;
                resp.msg = "Autoridad ok";
                return resp;
            }
            resp.ok = false;
            resp.msg = "Usuario no es autoridad";
            return resp;
        }

        return resp;
    }
}
