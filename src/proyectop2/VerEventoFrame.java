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

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class VerEventoFrame extends JFrame {
    private final Usuario usuario;

    public VerEventoFrame(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Ver Evento");
        setSize(520, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JTextField txtCodigo = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        JButton btnRegresar = new JButton("Regresar");
        JTextArea area = new JTextArea();
        area.setEditable(false);

        JPanel top = new JPanel(new GridLayout(1, 2, 10, 10));
        top.add(new JLabel("Código evento:"));
        top.add(txtCodigo);

        JPanel bottom = new JPanel(new GridLayout(1, 2, 10, 10));
        bottom.add(btnBuscar);
        bottom.add(btnRegresar);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(area), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        btnBuscar.addActionListener(e -> {
            Evento ev = DataStore.buscarEvento(txtCodigo.getText().trim());
            if (ev == null) { area.setText("Evento no encontrado"); return; }

            Calendar f = ev.getFecha();
            String fecha = f.get(Calendar.DAY_OF_MONTH) + "/" + (f.get(Calendar.MONTH) + 1) + "/" + f.get(Calendar.YEAR);

            StringBuilder sb = new StringBuilder();
            sb.append("Código: ").append(ev.getCodigo()).append("\n")
              .append("Título: ").append(ev.getTitulo()).append("\n")
              .append("Descripción: ").append(ev.getDescripcion()).append("\n")
              .append("Fecha: ").append(fecha).append("\n")
              .append("Monto: ").append(ev.getMontoRenta()).append("\n")
              .append("Tipo: ").append(ev.getTipo()).append("\n");

            if (ev.isCancelado()) sb.append("Estado: CANCELADO (Multa: ").append(ev.getMulta()).append(")\n");

            if (ev instanceof Deportivo dep) {
                sb.append("Equipo 1: ").append(dep.getEquipo1()).append(" ").append(dep.getJugadoresEquipo1()).append("\n")
                  .append("Equipo 2: ").append(dep.getEquipo2()).append(" ").append(dep.getJugadoresEquipo2()).append("\n")
                  .append("Deporte: ").append(dep.getTipoDeporte());
            } else if (ev instanceof Musical mus) {
                sb.append("Tipo música: ").append(mus.getTipoMusica()).append("\n")
                  .append("Equipo montaje: ").append(mus.getEquipoMontaje());
            } else if (ev instanceof Religioso rel) {
                sb.append("Personas convertidas: ").append(rel.getPersonasConvertidas());
            }

            area.setText(sb.toString());
        });

        btnRegresar.addActionListener(e -> { dispose(); new EventosFrame(usuario).setVisible(true); });

        setVisible(true);
    }
}