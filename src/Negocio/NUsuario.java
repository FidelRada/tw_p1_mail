/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.Respuesta;
import Datos.Usuario;
import Datos.DUsuario;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author fidel
 */
public class NUsuario {
    
    public Usuario checkEmail(String email) {
        DUsuario dusuObj = new DUsuario();
        Usuario usu = dusuObj.checkEmail(email);
        return usu;
    }

    //LISTARUSU[EMAIL, CI, FULLNAME]
    public ArrayList<Usuario> listusu(Map<String, Object> lineaComand) {
        String msg = "Content-Type:text/html;\r\n<html>"
        + "<body>\n";
        DUsuario DU = new DUsuario();
        return DU.listarTodos();
        
        
        /*
        String[] values = params.split(",");
        if (values.length == 3) {
        String emailParam = values[0].trim();
        String ciParam = values[1].trim();
        String fullnameParam = values[2].trim();
        String msgErr = "";
        boolean ok = true;
        if (emailParam.length() <= 0 || emailParam.length() > 320) {
        ok = false;
        msgErr = "Email invalido";
        }
        if (ciParam.length() <= 0 || ciParam.length() > 10) {
        ok = false;
        msgErr = "CI invalido";
        }
        if (fullnameParam.length() <= 0 || fullnameParam.length() > 100) {
        ok = false;
        msgErr = "Nombre completo invalido";
        }
        if (ok == true) {
        DUsuario duObj = new DUsuario();
        ArrayList<Usuario> usrsResult = duObj.listarusu(emailParam, ciParam, fullnameParam);
        if (!usrsResult.isEmpty()) {
        String res = "<h2> Lista de Usuarios </h2>\n"
        + "<table border=1>\n"
        + "<tr>"
        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ID</td>"
        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">ROL</td>"
        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">EMAIL</td>"
        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">CI</td>"
        + "<td style=\"font-size: 16px; font-weight: 800; padding: 10px;\">NOMBRE COMP</td>"
        + "</tr>\n";
        for (Usuario usr : usrsResult) {
        res += usr.toLISTARUSUtable();
        }
        res += "</table>";
        msg += "  <h2> COMANDO: LISTARUSU[EMAIL, CI, FULLNAME] </h2>\n"
        + res;
        } else {
        msg += "  <h2> COMANDO: LISTARUSU[EMAIL, CI, FULLNAME] </h2>\n"
        + "  <h4>No se encontro registros con los parametros proporcionados</h4>\n";
        }
        } else {
        msg += "  <h1> EXCEPCION AL SELECCIONAR USUARIOS </h1>\n"
        + "  <h3>EXCEPCION: " + msgErr + "</h3>\n"
        + "  <h2> COMANDO: LISTARUSU[EMAIL, CI, FULLNAME] </h2>\n"
        + "  <p>Si desea obviar alguno introduzca '0' </p>\n"
        + "  <h3>Ejemplos</h3>\n"
        + "  <ul>\n"
        + "      <li>LISTARUSU[0, 0, 0] retorna todos los usuarios</li>\n"
        + "      <li>LISTARUSU[0, 0, juan] retorna usuarios con nombre 'juan'</li>\n"
        + "      <li>LISTARUSU[@outlook, 0, pedro] retorna usuarios con correo '@outlook' y nombre 'pedro'</li>\n"
        + "  </ul>\n";
        }
        } else {
        msg += "  <h1> EXCEPCION AL SELECCIONAR USUARIOS </h1>\n"
        + "  <h2> COMANDO: LISTARUSU[EMAIL, CI, FULLNAME] </h2>\n"
        + "  <p> Error en parametros, debe llenar todos los parametros, si desea obviar alguno introduzca '0' </p>\n"
        + "  <h3>Ejemplos</h3>\n"
        + "  <ul>\n"
        + "      <li>LISTARUSU[0, 0, 0] retorna todos los usuarios</li>\n"
        + "      <li>LISTARUSU[0, 0, juan] retorna usuarios con nombre 'juan'</li>\n"
        + "      <li>LISTARUSU[@outlook, 0, pedro] retorna usuarios con correo '@outlook' y nombre 'pedro'</li>\n"
        + "  </ul>\n";
        }
        msg += "</body>"
        + "</html>";*/
//        return msg;
    }

    //REGUSU[ROL,EMAIL,PASSWORD,CI,FULLNAME]
    public String regusu(String params) {
        String msg = "Content-Type:text/html;\r\n<html>"
                + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 5) {
            String rolParam = values[0].trim();
            String emailParam = values[1].trim();
            String passwordParam = values[2].trim();
            String ciParam = values[3].trim();
            String fullnameParam = values[4].trim();
            boolean ok = true;
            String msgErr = "";
            if (rolParam.length() <= 0 || Generic.esEntero(rolParam) == false) {
                msgErr = "Rol no valido";
                ok = false;
            }
            if (emailParam.length() <= 0 || Generic.esEmailValido(emailParam) == false) {
                msgErr = "Email no valido";
                ok = false;
            }
            if (passwordParam.length() < 5 || passwordParam.length() > 30) {
                msgErr = "Password no valida (muy larga o corta, almenos 5 caracteres maximo 30)";
                ok = false;
            }
            if (ciParam.length() <= 0 || Generic.esEntero(ciParam) == false || ciParam.length() > 10) {
                msgErr = "CI no valido";
                ok = false;
            }
            if (fullnameParam.length() < 5 || fullnameParam.length() > 100) {
                msgErr = "Nombre completo invalido (almenos 5 caracteres maximo 100)";
                ok = false;
            }
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                Respuesta result = duObj.regusu(rolParam, emailParam, passwordParam, ciParam, fullnameParam);
                msg += "<h1> REGUSU EJECUTADO </h1>\n"
                        + "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg += "<h1> EXCEPCION AL REGISTRAR USUARIO </h1>\n"
                        + "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg += " <h1> EXCEPCION AL INSERTAR USUARIO </h1>\n"
                    + "  <h2> COMANDO: REGUSU[ROL,EMAIL,PASSWORD,CI,FULLNAME] </h2>\n"
                    + "  <p> Error en parametros, debe llenar todos los parametros</p>\n"
                    + "  <h3>Ejemplos</h3>\n"
                    + "  <ul>\n"
                    + "      <li>REGUSU[1, pedro@gmail.com, pedro123, 443433, pedro camacho hernandez]</li>\n"
                    + "      <li>REGUSU[2, sara@onmicrosoft.com, saraTheGoat, 989918,sara gutierrez anillas]</li>\n"
                    + "  </ul>\n";
        }
        msg += "</body>"
                + "</html>";
        return msg;
    }

    //EDITUSU[ID,ROL,EMAIL,PASSWORD,CI,FULLNAME]
    public String editusu(String params) {
        String msg = "Content-Type:text/html;\r\n<html>"
                + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 6) {
            String idParam = values[0].trim();
            String rolParam = values[1].trim();
            String emailParam = values[2].trim();
            String passwordParam = values[3].trim();
            String ciParam = values[4].trim();
            String fullnameParam = values[5].trim();
            boolean ok = true;
            String msgErr = "";
            if (idParam.length() <= 0 || Generic.esEntero(idParam) == false) {
                msgErr = "ID no valido";
                ok = false;
            }
            if (rolParam.length() <= 0 || Generic.esEntero(rolParam) == false) {
                msgErr = "Rol no valido";
                ok = false;
            }
            if ((emailParam.length() <= 0 || Generic.esEmailValido(emailParam) == false) && !emailParam.equals("0")) {
                msgErr = "Email no valido";
                ok = false;
            }
            if ((passwordParam.length() < 5 || passwordParam.length() > 30) && !passwordParam.equals("0")) {
                msgErr = "Password no valida (muy larga o corta, almenos 5 caracteres maximo 30)";
                ok = false;
            }
            if (ciParam.length() <= 0 || Generic.esEntero(ciParam) == false || ciParam.length() > 10) {
                msgErr = "CI no valido";
                ok = false;
            }
            if ((fullnameParam.length() < 5 || fullnameParam.length() > 100) && !fullnameParam.equals("0")) {
                msgErr = "Nombre completo invalido (almenos 5 caracteres maximo 100)";
                ok = false;
            }
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                Respuesta result = duObj.editusu(idParam, rolParam, emailParam, passwordParam, ciParam, fullnameParam);
                msg += "<h1> EDITUSU EJECUTADO </h1>\n"
                        + "<h3>RESPUESTA: " + result.msg + "</h3>\n";
            } else {
                msg += "<h1> EXCEPCION AL EDITAR USUARIO </h1>\n"
                        + "<h3>EXCEPCION: " + msgErr + "</h3>\n";
            }
        } else {
            msg += "<h1> EXCEPCION AL EDITAR USUARIO < / h1 >\n"
                    + "<h3>COMANDO: EDITUSU[ID,ROL,EMAIL,PASSWORD,CI,FULLNAME] </h3>\n"
                    + "<p>Use el ID para identificar el usuario y rellene con los nuevos valores los campos que desea editar, si desea obviar un campo coloque '0' </p>\n"
                    + "<h3>Ejemplo: </h3>\n"
                    + "<ul>\n"
                    + "<li>EDITUSU[2, 0, 0, 0, 0, Jose Marco Moreno Zenteno]: edita el nombre del usuario 2</li>\n"
                    + "<li>EDITUSU[1, 0, 0, password123p, 0, 0]: edita la contraseña del usuario 1</li>\n"
                    + "<ul>\n";
        }
        msg += "</body></html>";
        return msg;
    }

    public String elimusu(String params) {
        String msg = "Content-Type:text/html;\r\n<html>"
                + "<body>\n";
        String[] values = params.split(",");
        if (values.length == 1) {
            String id = values[0].trim();
            boolean ok = true;
            String msgErr = "";
            if (id.length() <= 0 || Generic.esEntero(id) == false) {
                ok = false;
                msgErr = "Id no valido";
            }
            if (ok == true) {
                DUsuario duObj = new DUsuario();
                Respuesta resp = duObj.elimusu(id);
                msg += "<h1>ELIMUSU ejecutado</h1> \n"
                        + "<h4>Respuesta: " + resp.msg + " </h4> \n";
            } else {
                msg += "<h1>Excepcion al ejecutar ELIMUSU</h1> \n"
                        + "<h4>Excepcion: " + msgErr + " </h4> \n";
            }
        } else {
            msg += "<h1>Excepcion al ejecutar ELIMUSU</h1> \n"
                    + "<h4>Excepcion: Parametros invalidos, para eliminar un usuario debe ingresar el ID</h4> \n"
                    + "<h3>Ejemplo: </h3>\n"
                    + "<ul>\n"
                    + "<li>ELIMUSU[4]: Elimina el usuario con ID 4</li>\n"
                    + "<li>ELIMUSU[1]: Elimina el usuario con ID 1</li>\n"
                    + "<ul>\n";
        }
        msg += "</body></html>";
        return msg;
    }
}
