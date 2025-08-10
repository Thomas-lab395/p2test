/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Mayra Bardales
 */

public class MenuPrincipalFrame extends JFrame {
    private Usuario usuario;

    public MenuPrincipalFrame(Usuario usuario) {
        this.usuario = usuario;

        setTitle("JAVA TICKET - Menú Principal (" + usuario.getTipoUsuario() + ")");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnEventos = new JButton("Administración de Eventos");
        JButton btnUsuarios = new JButton("Administración de Usuarios");
        JButton btnReportes = new JButton("Reportes");
        JButton btnSalir = new JButton("Salir");

        add(btnEventos);
        add(btnUsuarios);
        add(btnReportes);
        add(btnSalir);

        // Permisos según tipo de usuario
        if (usuario instanceof Limitado) {
            btnEventos.setEnabled(true); // pero en submenú no podrá crear/editar
            btnUsuarios.setEnabled(false);
        } else if (usuario instanceof Contenido) {
            btnUsuarios.setEnabled(false);
        }

        // Acciones
        btnEventos.addActionListener(e -> new EventosFrame(usuario).setVisible(true));
        btnUsuarios.addActionListener(e -> new UsuariosFrame().setVisible(true));
        btnReportes.addActionListener(e -> new ReportesFrame().setVisible(true));
        btnSalir.addActionListener(e -> System.exit(0));
    }
}
