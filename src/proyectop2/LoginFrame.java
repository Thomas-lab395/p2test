/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

   public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("JAVA TICKET - Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField();
        JLabel lblPassword = new JLabel("Contraseña:");
        JPasswordField txtPassword = new JPasswordField();

        JButton btnLogin = new JButton("Ingresar");
        JButton btnSalir = new JButton("Salir"); 

        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(btnLogin);
        panel.add(btnSalir);

        add(panel);

        btnLogin.addActionListener(e -> {
            String username = txtUsuario.getText();
            String password = new String(txtPassword.getPassword());

            Usuario usuario = DataStore.buscarUsuario(username); 
            if (usuario != null && usuario.getPassword().equals(password)) {
                dispose();
                new MenuPrincipalFrame(usuario).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
            }
        });

        btnSalir.addActionListener(e -> System.exit(0)); 
    }
}