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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import jdk.nashorn.api.tree.BreakTree;

public class Guia {

    private int bandejaLength = 0;
    private POPService guiaPopObj;
    private SMTPService guiaSmtpObj;
    private Map<String, Object> lineaComand;
    //List<String> aux;

    //private String guiaCmdo = "";
    //private String guiaReceptor = "";
    //private String guiaParams = "";
    public Guia() {
        lineaComand = new HashMap<>();
        guiaPopObj = new POPService();
        bandejaLength = guiaPopObj.getBandejaLength();

        lineaComand.put("comando", null);
        lineaComand.put("receptor", null);
        lineaComand.put("params", null);//new HashMap<String, Object>());
        lineaComand.put("error", null);
    }

    public void listen() {
        guiaPopObj = new POPService();
        //bandejaLength = 346;
        if (guiaPopObj.getBandejaLength() > bandejaLength) {
            bandejaLength++;
            System.out.println("se detecto nuevo correo en la bandeja de entrada!");
            List<String> msg = guiaPopObj.getEmail(bandejaLength);
            //List<String> msg = aux;

            String[] returnPath = msg.get(1).split(":");
            String[] subject = msg.get(40).split(":");
            System.out.println(Arrays.toString(returnPath));
            System.out.println(Arrays.toString(subject));

            checkEmisor(returnPath[1].trim());

            checkSubject(subject[1].trim());

            System.out.println(lineaComand);
            executeCmdo();

        }
        guiaPopObj.close();
        ClearLineComando();
    }

    private void ClearLineComando() {
        lineaComand.put("comando", null);
        lineaComand.put("receptor", null);
        lineaComand.put("params", null);
        lineaComand.put("error", null);

    }

    public void checkComando(String cmd) {
        if (!Comandos.isComando(cmd)) {
            lineaComand.put("error", "Error de sintaxis, [" + cmd + "] no es un comando valido");
            return;
        }
        lineaComand.put("comando", cmd);
    }

    public void checkParams(String[] s, String cmd) {

        if (s.length > 1) {
            String[] paramsString = s[1].split("\\]");
            String[] params = paramsString[0].split(", ");

            Map<String, Object> paramsMap = new HashMap<>();

            try {
                if (cmd.startsWith("LIST")) {

                    if (params.length == 1 && params[0].equals("*")) {
                        lineaComand.put("params", "*");
                        return;
                    }

                    LinkedList<String> paramsList = new LinkedList<>();
                    for (String p : params) {
                        if (!p.contains("=")) {
                            paramsList.add(p);
                        } else {
                            lineaComand.put("error", "error de sintaxis, solo se permite LISTUSU[ci, name, ...] para mas info use el comando HELP");
                            return;
                        }
                    }
                    lineaComand.put("params", paramsList);
                    return;
                }

                for (String p : params) {
                    String[] keyValue = p.split("=");

                    String key = keyValue[0].trim();
                    String valueString = keyValue[1].trim();

                    // Quitar las comillas alrededor del valor si existen
                    String value = valueString.replaceAll("\"", "");

                    paramsMap.put(key, value.trim());
                }
                lineaComand.put("params", paramsMap);
            } catch (Exception e) {
                lineaComand.put("params", null);
                lineaComand.put("error", "Error en sintaxis, parametros mal escritos");
            }
        }
    }

    public void checkSubject(String emailMsg) {
        String[] s = emailMsg.split("\\[");
        String cmd = s[0].trim();
        checkComando(cmd);
        checkParams(s, cmd);
    }

    public void checkEmisor(String emailMsg) {
        int ini = emailMsg.indexOf("<");
        int fin = emailMsg.indexOf(">");
        String emisor = emailMsg.substring(ini + 1, fin);
        //System.out.println("Emisor: " + emisor);
        lineaComand.put("receptor", emisor);
        //HAY QUE HACER UNA VERIFICACION DE EMAIL
        if (lineaComand.get("receptor") == null) {
            lineaComand.put("error", "Usuario no valido");

        }
        //NUsuario nusuObj = new NUsuario();
        //return nusuObj.checkEmail(emisor);
    }

    public void executeCmdo() {

        if (lineaComand.get("error") != null) {
            sendResponseEmail(lineaComand.get("receptor").toString(), lineaComand.get("error").toString());
            return;
        }

        String cmdo = (String) lineaComand.get("comando");
        switch (cmdo) {
            case Comandos.HELP:
                executeHELP();
                System.out.println("comando detectado: HELP");
                break;
            case Comandos.LISTUSU:
                executeLISTUSU();
                System.out.println("comando detectado: LISTUSU");
                break;
            case Comandos.REGUSU:
                //executeREGING_USUARIOS(this.guiaParams);
                System.out.println("comando detectado: REGUSU");
                break;
            case Comandos.EDITUSU:
                //executeEDITING_USUARIOS(this.guiaParams);
                System.out.println("comando detectado: EDITUSU");
                break;
            case Comandos.ELIMUSU:
                //executeDELITING_USUARIOS(this.guiaParams);
                System.out.println("comando detectado: ELIMUSU");
                break;
            case Comandos.LISTING:
                //executeLISTING_INGRESOS(this.guiaParams);
                System.out.println("comando detectado: LISTING");
                break;
            case Comandos.REGING:
                //executeREGING_INGRESOS(this.guiaParams);
                System.out.println("comando detectado: REGING");
                break;
            case Comandos.EDITING:
                //executeEDITING_INGRESOS(this.guiaParams);
                System.out.println("comando detectado: EDITING");
                break;
            case Comandos.LISTBEN:
                //executeLISTING_BENEFICIARIO(this.guiaParams);
                System.out.println("comando detectado: LISTBEN");
                break;
            case Comandos.REGBEN:
                //executeREGING_BENEFICIARIO(this.guiaParams);
                System.out.println("comando detectado: REGBEN");
                break;
            case Comandos.EDITBEN:
                //executeEDITING_BENEFICIARIO(this.guiaParams);
                System.out.println("comando detectado: EDITBEN");
                break;
            case Comandos.ELIMBEN:
                //executeDELITING_BENEFICIARIO(this.guiaParams);
                System.out.println("comando detectado: ELIMBEN");
                break;
            case Comandos.LISTINF:
                //executeLISTING_INFO(this.guiaParams);
                System.out.println("comando detectado: LISTINF");
                break;
            case Comandos.ELIMINF:
                //executeREGING_INFO(this.guiaParams);
                System.out.println("comando detectado: ELIMINF");
                break;
            case Comandos.LIST_INF_CLINICOS:
                //executeEDITING_INFO(this.guiaParams);
                System.out.println("comando detectado: LIST_INF_CLINICOS");
                break;
            case Comandos.ELIM_INF_CLINICOS:
                //executeDELITING_INFO(this.guiaParams);
                System.out.println("comando detectado: ELIM_INF_CLINICOS");
                break;
            case Comandos.LIST_INF_ACADEM:
                //executeLISTING_INFO_CLINICOS(this.guiaParams);
                System.out.println("comando detectado: LIST_INF_ACADEM");
                break;
            case Comandos.ELIM_INF_ACADEM:
                //executeREGING_INFO_CLINICOS(this.guiaParams)
                System.out.println("comando detectado: ELIM_INF_ACADEM");
                break;
            default:
                System.out.println("Comando no encontrado");
        }
    }

    private String head() {
        return "Content-Type:text/html;\r\n"
                + "<!doctype html>\n"
                + "<html lang=\"en\">\n"
                + "\n"
                + "<head>\n"
                + "    <meta charset=\"utf-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
                + "    <style>\n"
                + "        body {\n"
                + "            font-family: Arial, sans-serif;\n"
                + "            margin: 20px;\n"
                + "        }\n"
                + "\n"
                + "        h2 {\n"
                + "            text-align: center;\n"
                + "            color: #28a745;\n"
                + "            font-weight: bold;\n"
                + "        }\n"
                + "\n"
                + "        h4 {\n"
                + "            color: #28a745;\n"
                + "            margin-top: 20px;\n"
                + "        }\n"
                + "\n"
                + "        p {\n"
                + "            margin-bottom: 0;\n"
                + "        }\n"
                + "\n"
                + "        ul {\n"
                + "            list-style: none;\n"
                + "            padding-left: 0;\n"
                + "        }\n"
                + "\n"
                + "        li {\n"
                + "            margin-bottom: 20px;\n"
                + "        }\n"
                + "\n"
                + "        table {\n"
                + "            width: 100%;\n"
                + "            border-collapse: collapse;\n"
                + "            margin-top: 10px;\n"
                + "        }\n"
                + "\n"
                + "        th,\n"
                + "        td {\n"
                + "            padding: 8px;\n"
                + "            text-align: left;\n"
                + "            border-bottom: 1px solid #ddd;\n"
                + "        }\n"
                + "\n"
                + "        th {\n"
                + "            background-color: #343a40;\n"
                + "            color: white;\n"
                + "        }\n"
                + "\n"
                + "        .fw-bold {\n"
                + "            font-weight: bold;\n"
                + "        }\n"
                + "\n"
                + "        .text-info {\n"
                + "            color: #17a2b8;\n"
                + "        }\n"
                + "\n"
                + "        .container-sm {\n"
                + "            max-width: 576px;\n"
                + "            margin-left: auto;\n"
                + "            margin-right: auto;\n"
                + "        }\n"
                + "    </style>"
                + "</head>\n"
                + "\n";
    }

    public void executeHELP() {
        String msg = head()
                + "<body>\n"
                + "    <h2 class=\"text-center text-success fw-bold\"> BIENVENIDO AL MENU DE COMANDOS PARA EL PROYECTO GESTION DE INFORMACION DEL HOGAR MI RANCHO </h2>\n"
                + "    <br>\n"
                + "    <h4> AYUDA </h4>\n"
                + "    <ul>\n"
                + "        <li>\n"
                + "            <p><span class=\"fw-bold text-info\">HELP:</span> Comando para perdir el menu de comandos</p>\n"
                + "\n"
                + "        </li>\n"
                + "    </ul>\n"
                + "    <h4> Gestionar Usuario </h4>\n"
                + "    <ul>\n"
                + "        <li>\n"
                + "            <p><span class=\"fw-bold text-info\">LISTUSU[*]:</span> Listar usuarios mostrando todos los campos</p>\n"
                + "\n"
                + "        </li>\n"
                + "        <li>\n"
                + "            <p><span class=\"fw-bold text-info\">LISTUSU[CI, FullName, Email, .....] :</span>\n"
                + "                Listar usuarios mostrando campos especificos</p>\n"
                + "            <div class=\"container-sm\">\n"
                + "\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">id</td>\n"
                + "                            <td>identificador del usuario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">fullname</td>\n"
                + "                            <td>Nombre del usuario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">ci</td>\n"
                + "                            <td>carnet de identidad del usuario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">estado</td>\n"
                + "                            <td>estado del usuario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">email</td>\n"
                + "                            <td>correo electronico del usuario</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "        </li>\n"
                + "        <li>\n"
                + "            <p><span class=\"fw-bold text-info\">REGUSU[email=fidel.rada@ejemplo.com, password=contraceña, ci=9800011,\n"
                + "                    fullname= fidel rada rojas]</span>: este comando sirve para registrar un usuario nuevo, con sus\n"
                + "                respectivos campos</p>\n"
                + "            <div class=\"container-sm \">\n"
                + "\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" class=\"\" scope=\"row\">fullname</td>\n"
                + "                            <td>Nombre del usuario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">ci</td>\n"
                + "                            <td>carnet de identidad del usuario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">password</td>\n"
                + "                            <td>contraceña del usuario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">email</td>\n"
                + "                            <td>correo electronico del usuario</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "        </li>\n"
                + "        <li>\n"
                + "            <p><span class=\"fw-bold text-info\">EDITUSU[id=1, email=fidel.rada@ejemplo.com, password=contraceña,\n"
                + "                    ci=9800011, fullname= fidel rada rojas]</span>: comando para editar informacion de un usuario </p>\n"
                + "            <div class=\"container-sm \">\n"
                + "\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" class=\"\" scope=\"row\">id (OBLIGATORIO)</td>\n"
                + "                            <td>identificador del usuario, sin este campo no se encontrar el usuario a modificar</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" class=\"\" scope=\"row\">fullname</td>\n"
                + "                            <td>Nombre del usuario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">ci</td>\n"
                + "                            <td>carnet de identidad del usuario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">password</td>\n"
                + "                            <td>contraceña del usuario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">email</td>\n"
                + "                            <td>correo electronico del usuario</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "        </li>\n"
                + "        <li>\n"
                + "            <p><span class=\"fw-bold text-info\">ELIMUSU[ID=1]</span>: Comando para eliminar usuario</p>\n"
                + "            <div class=\"container-sm\">\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" class=\"\" scope=\"row\">id (OBLIGATORIO)</td>\n"
                + "                            <td>identificador del usuario, sin este campo no se encontrar el usuario a modificar</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "        </li>\n"
                + "    </ul>\n"
                + "    <h4> GESTIONAR INGRESOS </h4>\n"
                + "    <ul>\n"
                + "        <li>\n"
                + "            <p><span class=\"text-info fw-bold\">LISTING[*]</span>: Comando para listar los ingresos, con todos sus campos\n"
                + "            </p>\n"
                + "        </li>\n"
                + "        <li>\n"
                + "            <p><span class=\"text-info fw-bold\">REGING[ nombre = fidel, situacion=descripcion de la situacion en la que\n"
                + "                    ingreso]</span>: Comando para registar un nuevo Ingreso</p>\n"
                + "            <div class=\"container-sm\">\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" class=\"\" scope=\"row\">nombre</td>\n"
                + "                            <td>nombre del ingresado, este nombre puede ser tambien un apodo del niño</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" class=\"\" scope=\"row\">situacion</td>\n"
                + "                            <td>descripcion de la situacion en la que se encontro al niño</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" class=\"\" scope=\"row\">fecha nacimiento</td>\n"
                + "                            <td>fecha de nacimiento en la que se encontro, si no exite se puede obiar este campo</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "\n"
                + "        </li>\n"
                + "        <li>\n"
                + "            <p><span class=\"text-info fw-bold\">EDITING[id=1, nombre = fidel, situacion=descripcion de la situacion en la\n"
                + "                    que ingreso, fecha_nacimiento= 12/12/23]</span>: Comando para registar un nuevo Ingreso</p>\n"
                + "            <div class=\"container-sm\">\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" class=\"\" scope=\"row\">id (Campo obligatorio)</td>\n"
                + "                            <td>del ingresado</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" class=\"\" scope=\"row\">nombre</td>\n"
                + "                            <td>nombre del ingresado, este nombre puede ser tambien un apodo del niño</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" class=\"\" scope=\"row\">situacion</td>\n"
                + "                            <td>descripcion de la situacion en la que se encontro al niño</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" class=\"\" scope=\"row\">fecha_nacimiento</td>\n"
                + "                            <td>fecha de nacimiento en la que se encontro, si no exite se puede obiar este campo</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "\n"
                + "        </li>\n"
                + "    </ul>\n"
                + "    <h4> Gestionar Beneficiario </h4>\n"
                + "    <ul>\n"
                + "        <li>\n"
                + "            <p><span class=\"text-info fw-bold\">LISTARBEN[*]</span>:Comando para listar todos los beneficiarios con todos\n"
                + "                los campos</p>\n"
                + "\n"
                + "        </li>\n"
                + "        <li>\n"
                + "            <p><span class=\"text-info fw-bold\">LISTARBEN[id, nombre, ... ]</span>:Comando para listar beneficiarios\n"
                + "                mostrando campos especificos </p>\n"
                + "            <div class=\"container-sm\">\n"
                + "\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">id</td>\n"
                + "                            <td>identificador del Beneficiario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">nombre</td>\n"
                + "                            <td>Nombre del Beneficiario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">ci</td>\n"
                + "                            <td>carnet de identidad del Beneficiario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">estado</td>\n"
                + "                            <td>estado del Beneficiario</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "        </li>\n"
                + "        <li>\n"
                + "            <p><span class=\"text-info fw-bold\">REGBEN[nombre=fidel, estado=decripcion,\n"
                + "                    fecha_nacimiento=12/12/23]</span>:Comando para registrar unn beneficiario nuevo</p>\n"
                + "            <div class=\"container-sm\">\n"
                + "\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">nombre</td>\n"
                + "                            <td>Nombre del Beneficiario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">estado</td>\n"
                + "                            <td>estado del Beneficiario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">fecha_nacimiento</td>\n"
                + "                            <td>fecha de nacimiento del beneficiario</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "        </li>\n"
                + "        <li>\n"
                + "            <p><span class=\"text-info fw-bold\">EDITBEN[id=1, nombre=fidel, estado=decripcion,\n"
                + "                    fecha_nacimiento=12/12/23]</span>:Comando para </p>\n"
                + "            <div class=\"container-sm\">\n"
                + "\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">id (campo obligatorio)</td>\n"
                + "                            <td>id del beneficiario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">nombre</td>\n"
                + "                            <td>Nombre del Beneficiario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">estado</td>\n"
                + "                            <td>estado del Beneficiario</td>\n"
                + "                        </tr>\n"
                + "                        <tr class=\"table-secondary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">fecha_nacimiento</td>\n"
                + "                            <td>fecha de nacimiento del beneficiario</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "        </li>\n"
                + "        <li>\n"
                + "            <p><span class=\"text-info fw-bold\">ELIMBEN[id=1]</span>:Comando para </p>\n"
                + "            <div class=\"container-sm\">\n"
                + "\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">id (campo obligatorio)</td>\n"
                + "                            <td>id del beneficiario</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "        </li>\n"
                + "    </ul>\n"
                + "    <h4> Gestionar informes de educador </h4>\n"
                + "    <ul>\n"
                + "        <li><p><span class=\"fw-bold text-info\">LISTINF[*]</span>:Comando para listar todos los infomes</p></li>\n"
                + "        <li><p><span class=\"fw-bold text-info\">ELIMINF[id = 1]</span>:Comando para eliminar un informe</p>\n"
                + "            <div class=\"container-sm\">\n"
                + "\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">id (campo obligatorio)</td>\n"
                + "                            <td>id del Informe</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "        </li>\n"
                + "    </ul>\n"
                + "    <h4> Gestionar informe academico </h4>\n"
                + "    <ul>\n"
                + "        <li><p><span class=\"fw-bold text-info\">LISTINFA[*]</span>:Comando para  listar todos los infomes academico </p></li>\n"
                + "        <li><p><span class=\"fw-bold text-info\">ELIMINFA[id = 1]</span>:Comando para  eliminar un informe academico </p>\n"
                + "            <div class=\"container-sm\">\n"
                + "\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">id (campo obligatorio)</td>\n"
                + "                            <td>id del  Informe academico</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "        </li>\n"
                + "    </ul>\n"
                + "    <h4> Gestionar informes clinicos </h4>\n"
                + "    <ul>\n"
                + "        <li><p><span class=\"fw-bold text-info\">LISTINFC[*]</span>:Comando para  listar todos los infomes clinicos </p></li>\n"
                + "        <li><p><span class=\"fw-bold text-info\">ELIMINFC[id = 1]</span>:Comando para  eliminar un informe clinicos </p>\n"
                + "            <div class=\"container-sm\">\n"
                + "\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">Campo</th>\n"
                + "                            <th scope=\"col\">Descripcion</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n"
                + "                        <tr class=\"table-primary\">\n"
                + "                            <td class=\"fw-bold \" scope=\"row\">id (campo obligatorio)</td>\n"
                + "                            <td>id del Informe clinico</td>\n"
                + "                        </tr>\n"
                + "                    </tbody>\n"
                + "                </table>\n"
                + "            </div>\n"
                + "        </li>\n"
                + "\n"
                + "    </ul>\n"
                + "</body>\n"
                + "\n"
                + "</html>";
        //System.out.println(msg);
        sendResponseEmail("Estos son los comandos disponibles", msg);
    }

    public void sendResponseEmail(String sbjct, String msg) {
        this.guiaSmtpObj = new SMTPService();
        this.guiaSmtpObj.sendMessage(sbjct, (String) lineaComand.get("receptor"), msg);
        this.guiaSmtpObj.close();
    }
    
    private String table(){
        return "";
    }

    /*==============================
    //===== CU1 GESTIONAR USUARIO ====
    //==============================*/
    public void executeLISTUSU() {
        NUsuario nuObj = new NUsuario();
        ArrayList<Usuario> ListUSU = nuObj.listusu(lineaComand);
        String res = head()
                + "<div class=\"container-sm\">\n"
                + "\n"
                + "                <table class=\"table table-hover table-sm\">\n"
                + "\n"
                + "                    <thead>\n"
                + "                        <tr class=\"table-dark\">\n"
                + "                            <th scope=\"col\">id</th>\n"
                + "                            <th scope=\"col\">ci</th>\n"
                + "                            <th scope=\"col\">nombre</th>\n"
                + "                            <th scope=\"col\">email</th>\n"
                + "                            <th scope=\"col\">estado</th>\n"
                + "                        </tr>\n"
                + "                    </thead>\n"
                + "                    <tbody>\n";
        for (Usuario usuario : ListUSU) {
            res += "                        <tr class=\"table-primary\">\n"
                    + "                            <td class=\"fw-bold \" scope=\"row\">"+usuario.id+"</td>\n"
                    + "                            <td>"+usuario.ci+"</td>\n"
                    + "                            <td>"+usuario.fullname+"</td>\n"
                    + "                            <td>"+usuario.email+"</td>\n"
                    + "                            <td>"+usuario.estado+"</td>\n";
        }
        res += "                        </tr>\n"
                    + "                    </tbody>\n"
                    + "                </table>\n"
                    + "            </div>\n";
        sendResponseEmail("RESPUESTA A PETICION LISTUSU", res);
    }

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
    public static void main(String[] args) {
        //String input = "LISTUSU[   id  =   1  , name=\"    Fidel\"]";
        /**
        Guia g = new Guia();
        List<String> s = new ArrayList<>();
        //s.add("Subject:LISTUSU[*]");
        s.add("   Return-Path: <rada.andres@ficct.uagrm.edu.bo>");
        s.add("Received: from gateway20.websitewelcome.com (gateway20.websitewelcome.com [192.185.67.41])\n"
                + "	by www.tecnoweb.org.bo (8.17.1/8.17.1) with ESMTPS id 35UDvJPp784193\n"
                + "	(version=TLSv1.2 cipher=ECDHE-RSA-AES256-GCM-SHA384 bits=256 verify=NOT)\n"
                + "	for <grupo01sa@tecnoweb.org.bo>; Fri, 30 Jun 2023 09:57:20 -0400");
        s.add("Received: from atl1wswcm02.websitewelcome.com (unknown [50.6.129.163])\n"
                + "	by atl1wswob01.websitewelcome.com (Postfix) with ESMTP id D6038400DF5CB\n"
                + "	for <grupo01sa@tecnoweb.org.bo>; Fri, 30 Jun 2023 13:57:18 +0000 (UTC)");
        s.add("Received: from 162-215-240-81.unifiedlayer.com ([208.91.198.170])\n"
                + "	by cmsmtp with ESMTP\n"
                + "	id FEcgqs7T6Je9QFEcgqRQ01; Fri, 30 Jun 2023 13:57:18 +0000");
        s.add("X-Authority-Reason: nr=8");
        s.add("DKIM-Signature: v=1; a=rsa-sha256; q=dns/txt; c=relaxed/relaxed;\n"
                + "	d=ficct.uagrm.edu.bo; s=default; h=Content-Transfer-Encoding:Content-Type:\n"
                + "	Subject:From:To:MIME-Version:Date:Message-ID:Sender:Reply-To:Cc:Content-ID:\n"
                + "	Content-Description:Resent-Date:Resent-From:Resent-Sender:Resent-To:Resent-Cc\n"
                + "	:Resent-Message-ID:In-Reply-To:References:List-Id:List-Help:List-Unsubscribe:\n"
                + "	List-Subscribe:List-Post:List-Owner:List-Archive;\n"
                + "	bh=47DEQpj8HBSa+/TImW+5JCeuQeRkm5NMpJWZG3hSuFU=; b=bquRo6qZWfQ6BLCGg7bdwD9B+o\n"
                + "	f5Hx1r7aokbCFnHprb3IoQImr84yn7nYX/Md+Ucn6ReY1jdqYg1M9Eb+fqmQEyKRsYGKlGy75/Uk6\n"
                + "	jOvfafJE5/LECqRMHgHo7saWkq5qQHdWD4Qri/MKIBelF8XPeR7vWtaDiX+ble8VQG8FBhcEyZcCX\n"
                + "	vliPWIkotmAOaYFXrwjdKlpD+GipZ/0U8Yipe6xkTPR+4hVIFXlYVWjLh2L1onSiWJwxHaHFNZGdT\n"
                + "	/Yl5lKkiSItLFxcFuLxT37gpfhsLfCjzESawJw6c9ILp0UTwORj0mWzs6OTHSM0jRlPf5AIdeHIgx\n"
                + "	z2SgIBJg==;");
        s.add("Received: from [177.222.111.245] (port=16702 helo=[192.168.0.12])\n"
                + "	by cp-20.webhostbox.net with esmtpsa  (TLS1.2) tls TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384\n"
                + "	(Exim 4.96)\n"
                + "	(envelope-from <rada.andres@ficct.uagrm.edu.bo>)\n"
                + "	id 1qFEcf-002LwC-36\n"
                + "	for grupo01sa@tecnoweb.org.bo;\n"
                + "	Fri, 30 Jun 2023 19:27:18 +0530");
        s.add("Message-ID: <7dee7779-9ebe-793e-3f7c-a580e4c53017@ficct.uagrm.edu.bo>");
        s.add("Date: Fri, 30 Jun 2023 09:57:15 -0400");
        s.add("MIME-Version: 1.0");
        s.add("User-Agent: Mozilla/5.0 (X11; Linux x86_64; rv:102.0) Gecko/20100101\n"
                + " Thunderbird/102.12.0");
        s.add("To: grupo01sa@tecnoweb.org.bo");
        s.add("Content-Language: en-US");
        s.add("From: fide rada <rada.andres@ficct.uagrm.edu.bo>");
        //s.add("Subject: LISTUSU[*]");
        s.add("Subject: HELP");
        s.add("Content-Type: text/plain; charset=UTF-8; format=flowed");
        s.add("Content-Transfer-Encoding: 7bit");
        s.add("X-AntiAbuse: This header was added to track abuse, please include it with any abuse report");

        //g.checkSubject(s);
        //g.aux = s;
        g.listen();
         */
        
        int yolo = 12323;
        String yolo2 = "";
        System.out.println(yolo2.getClass().getName());
        
        
        
    }
}
