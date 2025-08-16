/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Mayra Bardales
 */

public class ReportesFrame extends JFrame {
    private Usuario usuario;

    public ReportesFrame(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Reportes");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 10, 10));

        JButton btnRealizados = new JButton("Listar eventos realizados");
        JButton btnFuturos = new JButton("Listar eventos futuros");
        JButton btnCancelados = new JButton("Listar eventos cancelados");
        JButton btnIngresosFecha = new JButton("Ingresos por fecha");
        JButton btnPerfil = new JButton("Ver perfil usuario");
        JButton btnRegresar = new JButton("Regresar");

        add(btnRealizados);
        add(btnFuturos);
        add(btnCancelados);
        add(btnIngresosFecha);
        add(btnPerfil);
        add(btnRegresar);

        btnRealizados.addActionListener(e -> listarRealizados());
        btnFuturos.addActionListener(e -> listarFuturos());
        btnCancelados.addActionListener(e -> listarCancelados());
        btnIngresosFecha.addActionListener(e -> ingresosPorFecha());
        btnPerfil.addActionListener(e -> verPerfilUsuario());
        btnRegresar.addActionListener(e -> dispose());
    }

    private void listarRealizados() {
        Calendar hoy = Calendar.getInstance();
        List<Evento> lista = new ArrayList<>();
        for (Evento e : DataStore.getEventos()) {
            if (e.getFecha().before(hoy) && !e.isCancelado()) lista.add(e);
        }
        mostrarListaEventos(lista, "Eventos Realizados");
    }

    private void listarFuturos() {
        Calendar hoy = Calendar.getInstance();
        List<Evento> lista = new ArrayList<>();
        for (Evento e : DataStore.getEventos()) {
            if (e.getFecha().after(hoy) && !e.isCancelado()) lista.add(e);
        }
        mostrarListaEventos(lista, "Eventos Futuros");
    }

    private void listarCancelados() {
        List<Evento> lista = new ArrayList<>();
        for (Evento e : DataStore.getEventos()) {
            if (e.isCancelado()) lista.add(e);
        }
        mostrarListaEventosCancelados(lista);
    }

    private void ingresosPorFecha() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String inicioStr = JOptionPane.showInputDialog(this, "Fecha inicio (dd/MM/yyyy):");
            String finStr = JOptionPane.showInputDialog(this, "Fecha fin (dd/MM/yyyy):");
            if (inicioStr == null || finStr == null) return;

            Date inicio = sdf.parse(inicioStr);
            Date fin = sdf.parse(finStr);

            List<Evento> lista = new ArrayList<>();
            for (Evento e : DataStore.getEventos()) {
                if (!e.getFecha().getTime().before(inicio) && !e.getFecha().getTime().after(fin)) {
                    lista.add(e);
                }
            }

            double total = DataStore.sumarMontos(lista, 0) + DataStore.sumarMultas(lista, 0);
            String detalle = generarEstadisticas(lista);
            JOptionPane.showMessageDialog(this, "Eventos en rango:\n" + detalle + "\nTotal ingresos: " + total);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Fechas inválidas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verPerfilUsuario() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(usuario.getNombreCompleto()).append("\n");
        sb.append("Username: ").append(usuario.getUsername()).append("\n");
        sb.append("Edad: ").append(usuario.getEdad()).append("\n");
        sb.append("Tipo: ").append(usuario.getTipoUsuario()).append("\n");
        sb.append("Eventos creados:\n");

        for (String id : usuario.getEventosCreados()) {
            Evento e = DataStore.buscarEvento(id);
            if (e != null) {
                sb.append(id).append(" - ").append(e.getTipo()).append(" - ").append(e.getTitulo())
                        .append(" - ").append(e.getMontoRenta()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void mostrarListaEventos(List<Evento> lista, String titulo) {
        lista.sort((a, b) -> b.getFecha().compareTo(a.getFecha()));
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Evento e : lista) {
            sb.append(e.getCodigo()).append(" - ").append(e.getTipo()).append(" - ")
              .append(e.getTitulo()).append(" - ").append(sdf.format(e.getFecha().getTime()))
              .append(" - ").append(e.getMontoRenta()).append("\n");
        }
        sb.append("\n").append(generarEstadisticas(lista));
        JOptionPane.showMessageDialog(this, sb.toString(), titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarListaEventosCancelados(List<Evento> lista) {
        lista.sort((a, b) -> b.getFecha().compareTo(a.getFecha()));
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Evento e : lista) {
            sb.append(e.getCodigo()).append(" - ").append(e.getTipo()).append(" - ")
              .append(e.getTitulo()).append(" - ").append(sdf.format(e.getFecha().getTime()))
              .append(" - Multa: ").append(e.getMulta()).append("\n");
        }
        sb.append("\n").append(generarEstadisticasCancelados(lista));
        JOptionPane.showMessageDialog(this, sb.toString(), "Eventos Cancelados", JOptionPane.INFORMATION_MESSAGE);
    }

    private String generarEstadisticas(List<Evento> lista) {
        int deportivos = DataStore.contarEventosPorTipo(lista, "DEPORTIVO", 0);
        int musicales = DataStore.contarEventosPorTipo(lista, "MUSICAL", 0);
        int religiosos = DataStore.contarEventosPorTipo(lista, "RELIGIOSO", 0);
        double totalMonto = DataStore.sumarMontos(lista, 0);
        return "Deportivos: " + deportivos + " | Musicales: " + musicales + " | Religiosos: " + religiosos +
                "\nTotal monto: " + totalMonto;
    }

    private String generarEstadisticasCancelados(List<Evento> lista) {
        int deportivos = DataStore.contarEventosPorTipo(lista, "DEPORTIVO", 0);
        int musicales = DataStore.contarEventosPorTipo(lista, "MUSICAL", 0);
        int religiosos = DataStore.contarEventosPorTipo(lista, "RELIGIOSO", 0);
        double totalMulta = DataStore.sumarMultas(lista, 0);
        return "Deportivos: " + deportivos + " | Musicales: " + musicales + " | Religiosos: " + religiosos +
                "\nTotal multa: " + totalMulta;
    }
}