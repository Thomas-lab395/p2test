    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class EventosFrame extends JFrame {
    private final Usuario usuario;

    public EventosFrame(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Administración de Eventos");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnCrear = new JButton("Crear Evento");
        JButton btnEditar = new JButton("Editar Evento");
        JButton btnVer = new JButton("Ver Evento");
        JButton btnEliminar = new JButton("Eliminar Evento");
        JButton btnRegresar = new JButton("Regresar");

        add(btnCrear);
        add(btnEditar);
        add(btnVer);
        add(btnEliminar);
        add(btnRegresar);

        // ✅ Restricciones
        if (usuario instanceof Limitado) {
            btnCrear.setEnabled(false);
            btnEditar.setEnabled(false);
        }

        btnCrear.addActionListener(e -> { dispose(); new CrearEventoFrame(usuario).setVisible(true); });
        btnEditar.addActionListener(e -> { dispose(); new EditarEventoFrame(usuario).setVisible(true); });
        btnVer.addActionListener(e -> { dispose(); new VerEventoFrame(usuario).setVisible(true); });
        btnEliminar.addActionListener(e -> { dispose(); new EliminarEventoFrame(usuario).setVisible(true); });
        btnRegresar.addActionListener(e -> { dispose(); new MenuPrincipalFrame(usuario).setVisible(true); });

        setVisible(true);
    }
}