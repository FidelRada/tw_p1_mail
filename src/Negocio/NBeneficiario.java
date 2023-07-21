/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.DBeneficiario;
import Datos.DUsuario;
import Datos.Usuario;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author fidel
 */
public class NBeneficiario {

    LinkedList<String> params = new LinkedList<>();

    public NBeneficiario() {
        params.add("id");
        params.add("nombre");
        params.add("situacion");
        params.add("fecha_nac");
        params.add("id_encargado");
    }

    private boolean checkParams(LinkedList p) {
        return params.containsAll(p);
    }

    public Map<String, Object> listben(Map<String, Object> lineaComand) {
        DBeneficiario DB = new DBeneficiario();

        Map<String, Object> resp = new HashMap<>();

        if (lineaComand.get("params").equals("*")) {
            resp = DB.listarTodos();
            return resp;
        }

        if (lineaComand.get("params") instanceof LinkedList) {
            LinkedList<String> p = (LinkedList<String>) lineaComand.get("params");
            if (checkParams(p)) {
                resp = DB.listarben(params);
                return resp;
            }
        }

        resp.put("error", "error de syntaxis, en nombre de variables ");
        return resp;

    }

    public Map<String, Object> regben(Map<String, Object> lc) {
        Map<String, Object> resp = new HashMap<>();

        Map<String, Object> params = (Map) lc.get("params");

        LinkedList<String> p = new LinkedList<String>(params.keySet());
        if (checkParams(p)) {

            if (((String) params.get("nombre")).length() < 3) {
                resp.put("error", "nombre demaciado corto");
                return resp;
            }

            if (!Generic.esFechaValida((String) params.get("fecha_nac"))) {
                resp.put("error", "fecha_nac tiene que ser una fecha valida");
                return resp;
            }

            if (params.get("id_encargado") == null || !Generic.esEntero((String) params.get("id_encargado"))) {
                resp.put("error", "id_encargado no valido, tiene que ser un numero y es obligatorio");
                return resp;
            }

            DBeneficiario db = new DBeneficiario();
            resp = db.regBeneficiario((Map<String, Object>) lc.get("params"));
            return resp;
        }

        resp.put("error", "error de sintaxis, parametro no identidicados");
        return resp;

    }

    public Map<String, Object> editben(Map<String, Object> lc) {

        Map<String, Object> resp = new HashMap<>();
        Map<String, Object> params = (Map) lc.get("params");
        LinkedList<String> p = new LinkedList<String>(params.keySet());

        if (checkParams(p)) {

            if (params.get("id") == null || !Generic.esEntero((String) params.get("id"))) {
                resp.put("error", "id no valido, tiene que ser un numero");
                return resp;
            }

            if (params.get("nombre") != null && ((String) params.get("nombre")).length() < 8) {
                resp.put("error", "nombre demaciado corto");
                return resp;
            }

            if (params.get("fecha_nac") != null && !Generic.esFechaValida((String) params.get("fecha_nac"))) {
                resp.put("error", "fecha_nac tiene que ser una fecha valida");
                return resp;
            }

            DUsuario du = new DUsuario();
            resp = du.editusu((Map<String, Object>) lc.get("params"));
            return resp;
        }

        resp.put("error", "error de sintaxis, parametro no identidicados");
        return resp;

    }

    public Map<String, Object> elimben(Map<String, Object> lc) {
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
