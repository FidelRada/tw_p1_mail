/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author fidel
 */
public class Generic {

    static public boolean esEntero(String nro) {
        Pattern ptrn = Pattern.compile("^[0-9]$|^[1-9][0-9]*", Pattern.CASE_INSENSITIVE);

        Matcher mtch = ptrn.matcher(nro.trim());
        boolean result = mtch.find();
        if (result) {
            //System.out.println("Nro: " + pal + "  Resultado: SI MATCH!");
            return true;
        } else {
            //System.out.println("Nro: " + pal + "  Resultado: NO MATCH!");
            return false;
        }
    }

    static public boolean esEmailValido(String email) {
        Pattern ptrn = Pattern.compile("^[0-9a-z][0-9a-z._-]+@[a-z0-9]+(\\.[a-z0-9]+)+$", Pattern.CASE_INSENSITIVE);

        Matcher mtch = ptrn.matcher(email.trim());
        boolean result = mtch.find();
        if (result) {
            //System.out.println("Nro: " + pal + "  Resultado: SI MATCH!");
            return true;
        } else {
            //System.out.println("Nro: " + pal + "  Resultado: NO MATCH!");
            return false;
        }
    }

    //fechas formato: 2022-02-15 año-mes-dia
    static public boolean esFechaValida(String fecha) {
        String patronFecha = "^(0?[1-9]|[12][0-9]|3[01])[-/](0?[1-9]|1[0-2])[-/](19|20)\\d\\d$";
        Pattern pattern = Pattern.compile(patronFecha);
        Matcher matcher = pattern.matcher(fecha);
        return matcher.matches();
        /*Pattern p = Pattern.compile("^([1-9][0-9]{3})[-/]([0-9]{2})[-/]([0-9]{2})$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(fecha.trim());
        boolean res = m.find();
        if (res == true) {
            String year = m.group(1);
            String mes = m.group(2);
            String dia = m.group(3);
            if (mes.equals("00") || Integer.valueOf(mes) > 12) {
                return false;
            }
            if (dia.equals("00")) {
                return false;
            }
            if (mes.equals("02")) {
                if (esYearBisiesto(year)) {
                    if (Integer.valueOf(dia) > 29) {
                        return false;
                    }
                } else {
                    if (Integer.valueOf(dia) > 28) {
                        return false;
                    }
                }
            }
            if ((mes30(mes)) && Integer.valueOf(dia) > 30) {
                return false;
            }
            if (Integer.valueOf(dia) > 31) {
                return false;
            }
            return true;
        }
        return false;*/
    }

    static public boolean esYearBisiesto(String yearR) {
        int year = Integer.valueOf(yearR);
        boolean leap = false;

        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    leap = true;
                } else {
                    leap = false;
                }
            } else {
                leap = true;
            }
        } else {
            leap = false;
        }
        return leap;
    }

    static public boolean mes30(String mes) {
        switch (mes) {
            case "04":
                return true;
            case "06":
                return true;
            case "09":
                return true;
            case "11":
                return true;
            default:
                return false;
        }
    }

}
