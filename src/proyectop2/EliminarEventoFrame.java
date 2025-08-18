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
import java.util.Calendar;

public class EliminarEventoFrame extends JFrame {
    private final Usuario usuario;

    public EliminarEventoFrame(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Cancelar Evento");
        setSize(420, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextField txtCodigo = new JTextField();
        JButton btnCancelar = new JButton("Cancelar evento");
        JButton btnRegresar = new JButton("Regresar");

        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.add(new JLabel("Código evento:"));
        panel.add(txtCodigo);

        JPanel bottom = new JPanel(new FlowLayout());
        bottom.add(btnCancelar);
        bottom.add(btnRegresar);

        add(panel, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);

        btnCancelar.addActionListener(e -> {
            String codigo = txtCodigo.getText().trim();
            Evento ev = DataStore.buscarEvento(codigo);
            if (ev == null) {
                JOptionPane.showMessageDialog(this, "Evento no encontrado");
                return;
            }
            if (!usuario.getEventosCreados().contains(codigo)) {
                JOptionPane.showMessageDialog(this, "No puedes cancelar un evento que no creaste.");
                return;
            }
            int opt = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cancelar este evento?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                Calendar ahora = Calendar.getInstance();

                
                ev.cancelarEvento(ahora);

                JOptionPane.showMessageDialog(this, "Evento cancelado. Multa: " + ev.getMulta());
            }
        });

        btnRegresar.addActionListener(e -> {
            dispose();
            new EventosFrame(usuario).setVisible(true);
        });

        setVisible(true);
    }
}
