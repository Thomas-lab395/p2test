/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectop2;

import javax.swing.UIManager;

/**
 *
 * @author Mayra Bardales
 */
public class ProyectoP2 {

  
    public static void main(String[] args) {
        
    try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }        
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
          }
        );
    }
}