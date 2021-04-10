/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lunasystems.liquidacion;

import javax.swing.JOptionPane;

/**
 *
 * @author Mariela
 */
public class Liquidacion {
    
    public static void main(String[] args) {
        try {
            /*JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
           // SubstanceLookAndFeel.setSkin("org.pushingpixels.substance.api.skin.BusinessBlackSteelSkin");*/
            frm_login formulario = new frm_login();
            formulario.setLocationRelativeTo(null);
            formulario.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
}
