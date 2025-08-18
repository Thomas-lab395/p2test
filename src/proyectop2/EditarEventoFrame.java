/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

/**
 *
 * @author Mayra Bardales
 */
import javax.swing.*;
import java.awt.*;

public class EditarEventoFrame extends JFrame {
    private final Usuario usuario;

    public EditarEventoFrame(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Editar Evento");
        setSize(420, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JTextField txtCodigo = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        JButton btnRegresar = new JButton("Regresar");

        JPanel top = new JPanel(new GridLayout(1, 2, 10, 10));
        top.add(new JLabel("Código evento:"));
        top.add(txtCodigo);

        JPanel bottom = new JPanel(new GridLayout(1, 2, 10, 10));
        bottom.add(btnBuscar);
        bottom.add(btnRegresar);

        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> {
            String codigo = txtCodigo.getText().trim();
            Evento ev = DataStore.buscarEvento(codigo);
            if (ev == null) {
                JOptionPane.showMessageDialog(this, "Evento no encontrado");
                return;
            }
            dispose();
            new CrearEventoFrame(usuario, ev).setVisible(true); 
        });

        btnRegresar.addActionListener(e -> {
            dispose();
            new EventosFrame(usuario).setVisible(true);
        });

        setVisible(true);
    }
}