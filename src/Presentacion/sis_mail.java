package Presentacion;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author fidel
 */

//import Datos.DUsuario;
//import Datos.Usuario;
import java.util.ArrayList;

public class sis_mail {

    public static void main(String[] args) {
        Guia guiaObj = new Guia();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    guiaObj.listen();
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        System.out.println("Excepcion: " + e.getMessage());
                    }
                }
            }
        }).start();
    }

}
