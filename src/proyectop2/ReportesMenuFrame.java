/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class ReportesMenuFrame extends JFrame {
    private final Usuario usuario;
    private final JTextArea areaReportes;

    public ReportesMenuFrame(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Menú de Reportes - JAVA TICKET");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 10, 10));
        JButton btnA = new JButton("a. Listar eventos realizados");
        JButton btnB = new JButton("b. Listar eventos futuros");
        JButton btnC = new JButton("c. Listar eventos cancelados");
        JButton btnD = new JButton("d. Ingreso generado por fecha");
        JButton btnE = new JButton("e. Ver Perfil del usuario");
        JButton btnF = new JButton("f. Regresar al Menú Principal");

        panelBotones.add(btnA);
        panelBotones.add(btnB);
        panelBotones.add(btnC);
        panelBotones.add(btnD);
        panelBotones.add(btnE);
        panelBotones.add(btnF);

        areaReportes = new JTextArea();
        areaReportes.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaReportes.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaReportes);

        add(panelBotones, BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);

        // ---------- ACCIONES ----------
        btnA.addActionListener(e -> listarEventosRealizados());
        btnB.addActionListener(e -> listarEventosFuturos());
        btnC.addActionListener(e -> listarEventosCancelados());
        btnD.addActionListener(e -> ingresoGeneradoPorFecha());
        btnE.addActionListener(e -> verPerfilUsuario());
        btnF.addActionListener(e -> {
            dispose();
            new MenuPrincipalFrame(usuario).setVisible(true);
        });

        setVisible(true);
    }
    private void listarEventosRealizados() {
        List<Evento> eventos = DataStore.getEventos();
        Calendar hoy = Calendar.getInstance();
        eventos.sort(Comparator.comparing(Evento::getFecha).reversed());

        StringBuilder sb = new StringBuilder();
        int dep = 0, rel = 0, mus = 0;
        double montoDep = 0, montoRel = 0, montoMus = 0;

        for (Evento ev : eventos) {
            if (ev.getFecha().before(hoy) && !ev.isCancelado()) {
                sb.append(formatEvento(ev)).append(" | Creador: ").append(ev.getCreador()).append("\n");
                switch (ev.getTipo()) {
                    case "DEPORTIVO" -> { dep++; montoDep += ev.getMontoRenta(); }
                    case "RELIGIOSO" -> { rel++; montoRel += ev.getMontoRenta(); }
                    case "MUSICAL" -> { mus++; montoMus += ev.getMontoRenta(); }
                }
            }
        }
        sb.append("\n--- Estadísticas ---\n")
            .append("Deportivos: ").append(dep).append(" | Total: ").append(montoDep).append("\n")
            .append("Religiosos: ").append(rel).append(" | Total: ").append(montoRel).append("\n")
            .append("Musicales: ").append(mus).append(" | Total: ").append(montoMus).append("\n");

        areaReportes.setText(sb.toString());
    }

    private void listarEventosFuturos() {
        List<Evento> eventos = DataStore.getEventos();
        Calendar hoy = Calendar.getInstance();
        eventos.sort(Comparator.comparing(Evento::getFecha));

        StringBuilder sb = new StringBuilder();
        int dep = 0, rel = 0, mus = 0;
        double montoDep = 0, montoRel = 0, montoMus = 0;

        for (Evento ev : eventos) {
            if (ev.getFecha().after(hoy) && !ev.isCancelado()) {
                sb.append(formatEvento(ev)).append(" | Creador: ").append(ev.getCreador()).append("\n"); 
                switch (ev.getTipo()) {
                    case "DEPORTIVO" -> { dep++; montoDep += ev.getMontoRenta(); }
                    case "RELIGIOSO" -> { rel++; montoRel += ev.getMontoRenta(); }
                    case "MUSICAL" -> { mus++; montoMus += ev.getMontoRenta(); }
                }
            }
        }
        sb.append("\n--- Estadísticas ---\n")
            .append("Deportivos: ").append(dep).append(" | Total: ").append(montoDep).append("\n")
            .append("Religiosos: ").append(rel).append(" | Total: ").append(montoRel).append("\n")
            .append("Musicales: ").append(mus).append(" | Total: ").append(montoMus).append("\n");

        areaReportes.setText(sb.toString());
    }

    private void listarEventosCancelados() {
        List<Evento> eventos = DataStore.getEventos();
        StringBuilder sb = new StringBuilder();
        int dep = 0, rel = 0, mus = 0;
        double multaTotal = 0;

        for (Evento ev : eventos) {
            if (ev.isCancelado()) {
                sb.append("Código: ").append(ev.getCodigo())
                    .append(" | Tipo: ").append(ev.getTipo())
                    .append(" | Título: ").append(ev.getTitulo())
                    .append(" | Fecha: ").append(formatFecha(ev.getFecha()))
                    .append(" | Multa: ").append(ev.getMulta())
                    .append(" | Creador: ").append(ev.getCreador()).append("\n");

                switch (ev.getTipo()) {
                    case "DEPORTIVO" -> dep++;
                    case "RELIGIOSO" -> rel++;
                    case "MUSICAL" -> mus++;
                }
                multaTotal += ev.getMulta();
            }
        }

        sb.append("\n--- Estadísticas ---\n")
            .append("Deportivos cancelados: ").append(dep).append("\n")
            .append("Religiosos cancelados: ").append(rel).append("\n")
            .append("Musicales cancelados: ").append(mus).append("\n")
            .append("Multa total: ").append(multaTotal).append("\n");

        areaReportes.setText(sb.toString());
    }

    private void ingresoGeneradoPorFecha() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JDateChooser desdeChooser = new JDateChooser();
        JDateChooser hastaChooser = new JDateChooser();
        panel.add(new JLabel("Desde:"));
        panel.add(desdeChooser);
        panel.add(new JLabel("Hasta:"));
        panel.add(hastaChooser);

        int opt = JOptionPane.showConfirmDialog(this, panel, "Seleccione rango de fechas",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opt != JOptionPane.OK_OPTION) return;

        Calendar desde = Calendar.getInstance();
        Calendar hasta = Calendar.getInstance();
        if (desdeChooser.getDate() == null || hastaChooser.getDate() == null) return;

        desde.setTime(desdeChooser.getDate());
        hasta.setTime(hastaChooser.getDate());

        List<Evento> eventos = DataStore.getEventos();
        int dep = 0, rel = 0, mus = 0;
        double total = 0;

        StringBuilder sb = new StringBuilder();

        for (Evento ev : eventos) {
            if (!ev.getFecha().before(desde) && !ev.getFecha().after(hasta)) {
                sb.append(formatEvento(ev))
                    .append(ev.isCancelado() ? " (Cancelado, Multa incluida)" : "")
                    .append(" | Creador: ").append(ev.getCreador()).append("\n"); 
                if (ev.isCancelado()) {
                    total += ev.getMulta();
                } else {
                    total += ev.getMontoRenta();
                }

                switch (ev.getTipo()) {
                    case "DEPORTIVO" -> dep++;
                    case "RELIGIOSO" -> rel++;
                    case "MUSICAL" -> mus++;
                }
            }
        }

        sb.append("\n--- Estadísticas ---\n")
            .append("Deportivos: ").append(dep).append("\n")
            .append("Religiosos: ").append(rel).append("\n")
            .append("Musicales: ").append(mus).append("\n")
            .append("Total generado (incluyendo multas): ").append(total).append("\n");

        areaReportes.setText(sb.toString());
    }


    private void verPerfilUsuario() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usuario Logueado:\n")
            .append("Nombre: ").append(usuario.getNombre()).append("\n")
            .append("Username: ").append(usuario.getUsername()).append("\n")
            .append("Edad: ").append(usuario.getEdad()).append("\n")
            .append("Tipo: ").append(usuario.getTipoUsuario()).append("\n\n")
            .append("Eventos creados:\n");

        for (String codigo : usuario.getEventosCreados()) {
            Evento ev = DataStore.buscarEvento(codigo);
            if (ev != null) {
                double montoMostrar = ev.getMontoRenta();
                
                sb.append(ev.getCodigo()).append(" - ")
                    .append(ev.getTipo()).append(" - ")
                    .append(ev.getTitulo()).append(" - ")
                    .append(montoMostrar).append("\n");
            }
        }
        areaReportes.setText(sb.toString());
    }

    private String formatEvento(Evento ev) {
        return "Código: " + ev.getCodigo() +
            " | Tipo: " + ev.getTipo() +
            " | Título: " + ev.getTitulo() +
            " | Fecha: " + formatFecha(ev.getFecha()) +
            " | Monto: " + ev.getMontoRenta();
    }

    private String formatFecha(Calendar fecha) {
        return new SimpleDateFormat("dd/MM/yyyy").format(fecha.getTime());
    }
}
