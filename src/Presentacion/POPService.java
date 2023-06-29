/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

/**
 *
 * @author fidel
 */

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class POPService {

    private final String HOST = "mail.tecnoweb.org.bo";
    private final int PORT = 110;

    private BufferedReader entrada;
    private DataOutputStream salida;
    private final String USER = "grupo01sa";
    private final String PASS = "grup001grup001";

    private Socket sckt;

    public POPService() {
        try {
            sckt = new Socket(HOST, PORT);
            entrada = new BufferedReader(new InputStreamReader(sckt.getInputStream()));
            salida = new DataOutputStream(sckt.getOutputStream());
            entrada.readLine();
            salida.writeBytes("USER " + USER + "\r\n");
            entrada.readLine();
            salida.writeBytes("PASS " + PASS + "\r\n");
            entrada.readLine();
        } catch (Exception e) {
            System.out.println("Excepcion al iniciar POPService: " + e);
        }
    }

    public int getBandejaLength() {
        int cant = 0;
        try {
            salida.writeBytes("LIST\r\n");
            String txt = entrada.readLine();
            String[] palabras = txt.split(" ");
            cant = Integer.valueOf(palabras[1]);
            getMultiline(entrada);
            System.out.println(txt);
        } catch (Exception e) {
            System.out.println("Excepcion al obtener bandeja length: " + e);
        }
        return cant;
    }

    private List<String> getMultiline(BufferedReader in) {
        List<String> lines = new ArrayList<String>();
        try {
            while (true) {
                String line = in.readLine();
                if (line == null) {
                }
                if (line.equals(".")) {
                    break;
                }
                if ((line.length() > 0) && (line.charAt(0) == '.')) {
                    line = line.substring(1);
                }
                lines.add(line);
            }
        } catch (Exception e) {
            System.out.println("Excepcion al leer con multiline: " + e);
        }
        return lines;
    }

    public void close() {
        try {
            salida.writeBytes("QUIT\r\n");
            System.out.println("S: " + entrada.readLine());
            entrada.close();
            salida.close();
            sckt.close();
        } catch (Exception e) {
            System.out.println("Excepcion al cerra conexion POPService: " + e);
        }
    }

    public List<String> getEmail(int nro) {
        List<String> lines = new ArrayList<String>();
        try {
            salida.writeBytes("RETR " + nro + "\r\n");
            lines = getMultiline(entrada);
        } catch (Exception e) {
            System.out.println("Excepcion al obtener correo POPService: " + e);
        }
        return lines;
    }
    public static void main(String[] args) {
        POPService pop = new POPService();
        pop.getBandejaLength();
    }
}
