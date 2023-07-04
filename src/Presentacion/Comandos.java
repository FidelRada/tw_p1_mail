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
import java.util.Arrays;
import java.util.List;

public class Comandos {
    //COMANDO HELP

    public static final String HELP = "HELP"; //CUALQUIERA
//CU1 GESTIONAR USUARIO
    // ACCTIONS: LISTAR, EDIT, ELIM, REG
    // ACCTION+3LETRAS  
    public static final String LISTUSU = "LISTUSU"; //CUALQUIERA [*]
    //public static final String LISTARUSUBYNAME = "LISTUSU"; //CUALQUIERA [NAME]

    public static final String REGUSU = "REGUSU"; //AUTORIDAD [...., ...]
    public static final String EDITUSU = "EDITUSU"; //AUTORIDAD [ID, NAME, ....]
    public static final String ELIMUSU = "ELIMUSU"; //AUTORIDAD [ID, NAME, STATE]

//CU2 GESTIONAR INGRESOS
    public static final String LISTING = "LISTING"; //CUALQUIERA[]
    public static final String REGING = "REGING"; //CUALQUIERA
    public static final String EDITING = "EDITING"; //CUALQUIERA

//CU3 GESTIONAR BENEFICIARIO
    public static final String LISTBEN = "LISTBEN"; //CUALQUIERA 
    public static final String REGBEN = "REGBEN"; //AUTORIDAD [LOSMISMOS DE USUARIO]
    public static final String EDITBEN = "EDITBEN"; //AUTORIDAD [ID, .....]
    public static final String ELIMBEN = "ELIMBEN"; //AUTORIDAD [ID]

//CU4 GESTIONAR INFORMES DE EDUCADOR
    public static final String LISTINF = "LISTINF"; //AUTORIDAD
    //public static final String REGINF = "REGINF"; //CUALQUIERA
    //public static final String EDITINF = "EDITINF"; //CUALQUIERA
    public static final String ELIMINF = "ELIMINF"; //AUTORIDAD

//CU5 GESTIONAR INFORMES CLINICOS
    public static final String LIST_INF_CLINICOS = "LIST_INF_CLINICOS"; //CUALQUIERA
    //public static final String REG_INF_CLINICOS = "REG_INF_CLINICOS"; //CUALQUIERA
    //public static final String EDIT_INF_CLINICOS = "EDIT_INF_CLINICOS"; //CUALQUIERA
    public static final String ELIM_INF_CLINICOS = "DELIT_INF_CLINICOS"; //CUALQUIERA

//CU6 GESTIONAR INFORMES ACADEMICOS
    public static final String LIST_INF_ACADEM = "LIST_INF_ACADEM"; //CUALQUIERA
    //public static final String REG_INF_ACADEM = "REG_INF_ACADEM"; //CUALQUIERA
    //public static final String EDIT_INF_ACADEM = "EDIT_INF_ACADEM"; //CUALQUIERA
    public static final String ELIM_INF_ACADEM = "ELIM_INF_ACADEM"; //CUALQUIERA

//CU7 GESTIONAR ALERTAS
    //**   public static final String GETING_ALERTAS = "GETING_ALERTAS"; // AUTORIDAD
//CU8 REPORTES Y ESTADISTICAS
//**    public static final String GETING_ESTADISTCAS = "GETING_ESTADISTCAS"; //AUTORIDAD
//**    public static final String GETING_REPORTES = "GETING_REPORTES"; //AUTORIDAD
    public static final String[] SOLO_AUTORIDAD
            = {REGUSU, EDITUSU, ELIMUSU, REGBEN, EDITBEN, ELIMBEN, LISTINF, ELIMINF};//, GETING_ALERTAS, GETING_ESTADISTCAS, GETING_REPORTES};

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

    public  static final boolean isComando(String cmdo) {
       switch (cmdo) {
            case Comandos.HELP:
                return true;
            case Comandos.LISTUSU:
                return true;
            case Comandos.REGUSU:
                return true;
            case Comandos.EDITUSU:
                return true;
            case Comandos.ELIMUSU:
                return true;
            case Comandos.LISTING:
                return true;
            case Comandos.REGING:
                return true;
            case Comandos.EDITING:
                return true;
            case Comandos.LISTBEN:
                return true;
            case Comandos.REGBEN:
                return true;
            case Comandos.EDITBEN:
                return true;
            case Comandos.ELIMBEN:
                return true;
            case Comandos.LISTINF:
                return true;
            case Comandos.ELIMINF:
                return true;
            case Comandos.LIST_INF_CLINICOS:
                return true;
            case Comandos.ELIM_INF_CLINICOS:
                return true;
            case Comandos.LIST_INF_ACADEM:
                return true;
            case Comandos.ELIM_INF_ACADEM:
                return true;
            default:
                return false;
       }
    }

    public static void main(String[] args) {

    }
}
