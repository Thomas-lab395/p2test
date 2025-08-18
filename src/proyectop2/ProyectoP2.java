/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectop2;

import javax.swing.UIManager;
import javax.swing.SwingUtilities;

public class ProyectoP2 {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        
        Usuario adminPorDefecto = new Admin("Administrador", "admin", "supersecreto", 99);
        
        DataStore.agregarUsuario(adminPorDefecto);
        
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
