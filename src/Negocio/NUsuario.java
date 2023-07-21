/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.Respuesta;
import Datos.Usuario;
import Datos.DUsuario;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author fidel
 */
public class NUsuario {

    LinkedList<String> params = new LinkedList<>();

    public NUsuario() {
        params.add("id");
        params.add("fullname");
        params.add("ci");
        params.add("estado");
        params.add("email");
        params.add("password");
    }

    public Usuario checkEmail(String email) {
        DUsuario dusuObj = new DUsuario();
        Usuario usu = dusuObj.checkEmail(email);
        return usu;
    }

    private boolean checkParams(LinkedList p) {
        return params.containsAll(p);
    }

    //LISTARUSU[EMAIL, CI, FULLNAME]
    public Map<String, Object> listusu(Map<String, Object> lineaComand) {
        DUsuario DU = new DUsuario();

        Map<String, Object> resp = new HashMap<>();

        if (lineaComand.get("params").equals("*")) {
            resp=DU.listarTodos();
            return resp;
        }

        if (lineaComand.get("params") instanceof LinkedList) {
            LinkedList<String> p = (LinkedList<String>) lineaComand.get("params");
            if (checkParams(p)) {
                resp=DU.listarusu(params);
                return resp;
            }
        }

        resp.put("error", "error de syntaxis, en nombre de variables ");
        return resp;

    }

    //REGUSU[ROL,EMAIL,PASSWORD,CI,FULLNAME]
    public Map<String, Object> regusu(Map<String, Object> lc) {
        Map<String, Object> resp = new HashMap<>();

        Map<String, Object> params = (Map) lc.get("params");

        if (((String) params.get("password")).length() < 8) {
            resp.put("error", "password tiene que tener mas de 8 caracteres");
            return resp;
        }

        if (!Generic.esEmailValido((String) params.get("email"))) {
            resp.put("error", "el email no es valido");
            return resp;

        }

        if (!Generic.esEntero((String) params.get("ci"))) {
            resp.put("error", "ci tiene que ser un numero");
            return resp;
        }

        if (((String) params.get("fullname")).length() < 8) {
            resp.put("error", "fullname demaciado corto");
            return resp;
        }

        DUsuario du = new DUsuario();
        LinkedList<String> p = new LinkedList<String>(params.keySet());
        if (checkParams(p)) {
            resp = du.regusu((Map<String, Object>) lc.get("params"));
            return resp;
        }

        resp.put("error", "error de sintaxis, parametro no identidicados");
        return resp;

    }

    public Map<String, Object> editusu(Map<String, Object> lc) {

        Map<String, Object> resp = new HashMap<>();
        Map<String, Object> params = (Map) lc.get("params");
        LinkedList<String> p = new LinkedList<String>(params.keySet());

        if (checkParams(p)) {

            if (params.get("id") == null || !Generic.esEntero((String) params.get("id"))) {
                resp.put("error", "id no valido, tiene que ser un numero");
                return resp;
            }

            if (params.get("password") != null && ((String) params.get("password")).length() < 8) {
                resp.put("error", "password tiene que tener mas de 8 caracteres");
                return resp;
            }

            if (params.get("email") != null && !Generic.esEmailValido((String) params.get("email"))) {
                resp.put("error", "el email no es valido");
                return resp;

            }

            if (params.get("ci") != null && !Generic.esEntero((String) params.get("ci"))) {
                resp.put("error", "ci tiene que ser un numero");
                return resp;
            }

            if (params.get("fullname") != null && ((String) params.get("fullname")).length() < 8) {
                resp.put("error", "fullname demaciado corto");
                return resp;
            }

            DUsuario du = new DUsuario();
            resp = du.editusu((Map<String, Object>) lc.get("params"));
            return resp;
        }

        resp.put("error", "error de sintaxis, parametro no identidicados");
        return resp;

    }

    public Map<String, Object> elimusu(Map<String, Object> lc) {
        Map<String, Object> resp = new HashMap<>();
        Map<String, Object> params = (Map) lc.get("params");

        if (params.get("id") == null || !Generic.esEntero((String) params.get("id"))) {
            resp.put("error", "id no valido, tiene que ser un numero y es obligatorio");
            return resp;
        }

        DUsuario du = new DUsuario();
        resp = du.elimusu(params);

        return resp;
    }
}
