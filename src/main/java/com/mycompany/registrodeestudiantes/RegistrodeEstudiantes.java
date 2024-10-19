/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.registrodeestudiantes;

import Vista.VistaEstudiante;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author USUARIO
 */
public class RegistrodeEstudiantes {

    public static void main(String[] args) {
        try {
            
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");     

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.err.println("Error al establecer Look and Feel: " + e.getMessage());
        }

        
        VistaEstudiante vista = new VistaEstudiante();
        vista.setVisible(true);
    }
}