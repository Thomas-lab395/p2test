/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Mayra Bardales
 */


public class EventosFrame extends JFrame {
    private Usuario usuario;
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EventosFrame(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Administración de Eventos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnCrear = new JButton("Crear Evento");
        JButton btnEliminar = new JButton("Eliminar Evento");
        JButton btnEditar = new JButton("Editar Evento");
        JButton btnVer = new JButton("Ver Evento");
        JButton btnRegresar = new JButton("Regresar");

        add(btnCrear);
        add(btnEliminar);
        add(btnEditar);
        add(btnVer);
        add(btnRegresar);

        // Restricciones por tipo de usuario
        if (usuario instanceof Limitado) {
            btnCrear.setEnabled(false);
            btnEditar.setEnabled(false);
        }

        // Acción crear evento
        btnCrear.addActionListener(e -> crearEvento());

        // Acción eliminar evento
        btnEliminar.addActionListener(e -> eliminarEvento());

        // Acción editar evento
        btnEditar.addActionListener(e -> editarEvento());

        // Acción ver evento
        btnVer.addActionListener(e -> verEvento());

        // Regresar
        btnRegresar.addActionListener(e -> dispose());
    }

    private void crearEvento() {
        String[] tipos = {"DEPORTIVO", "MUSICAL", "RELIGIOSO"};
        String tipo = (String) JOptionPane.showInputDialog(this, "Seleccione tipo de evento", "Tipo",
                JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

        if (tipo == null) return;

        String codigo = JOptionPane.showInputDialog(this, "Código único del evento:");
        String titulo = JOptionPane.showInputDialog(this, "Título del evento:");
        String descripcion = JOptionPane.showInputDialog(this, "Descripción:");
        String fechaStr = JOptionPane.showInputDialog(this, "Fecha (dd/MM/yyyy):");
        double monto = Double.parseDouble(JOptionPane.showInputDialog(this, "Monto de renta:"));

        LocalDate fecha = LocalDate.parse(fechaStr, dateFormat);

        Evento evento = null;

        switch (tipo) {
            case "DEPORTIVO" -> {
                String eq1 = JOptionPane.showInputDialog(this, "Nombre equipo 1:");
                String eq2 = JOptionPane.showInputDialog(this, "Nombre equipo 2:");
                TipoDeporte deporte = (TipoDeporte) JOptionPane.showInputDialog(this, "Tipo de deporte:",
                        "Deporte", JOptionPane.QUESTION_MESSAGE, null, TipoDeporte.values(), TipoDeporte.FUTBOL);
                evento = new Deportivo(codigo, titulo, descripcion, fecha, monto, eq1, eq2, deporte);
            }
            case "MUSICAL" -> {
                TipoMusica musica = (TipoMusica) JOptionPane.showInputDialog(this, "Tipo de música:",
                        "Música", JOptionPane.QUESTION_MESSAGE, null, TipoMusica.values(), TipoMusica.POP);
                evento = new Musical(codigo, titulo, descripcion, fecha, monto, musica);
            }
            case "RELIGIOSO" -> {
                evento = new Religioso(codigo, titulo, descripcion, fecha, monto);
            }
        }

        if (DataStore.agregarEvento(evento)) {
            usuario.agregarEvento(codigo);
            JOptionPane.showMessageDialog(this, "Evento creado con éxito.");
        } else {
            JOptionPane.showMessageDialog(this, "El código ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEvento() {
        String codigo = JOptionPane.showInputDialog(this, "Código del evento a eliminar:");
        Evento e = DataStore.buscarEvento(codigo);

        if (e == null) {
            JOptionPane.showMessageDialog(this, "Evento no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!usuario.getEventosCreados().contains(codigo)) {
            JOptionPane.showMessageDialog(this, "Solo el creador puede eliminar este evento.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calcular multa según reglas
        if (LocalDate.now().plusDays(1).isAfter(e.getFecha()) && !(e instanceof Religioso)) {
            e.cancelar(e.getMontoRenta() * 0.50);
        } else {
            e.cancelar(0);
        }

        DataStore.eliminarEvento(codigo);
        JOptionPane.showMessageDialog(this, "Evento cancelado. Multa: " + e.getMulta());
    }

    private void editarEvento() {
        String codigo = JOptionPane.showInputDialog(this, "Código del evento a editar:");
        Evento e = DataStore.buscarEvento(codigo);

        if (e == null) {
            JOptionPane.showMessageDialog(this, "Evento no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nuevoTitulo = JOptionPane.showInputDialog(this, "Nuevo título:", e.getTitulo());
        String nuevaDescripcion = JOptionPane.showInputDialog(this, "Nueva descripción:", e.getDescripcion());

        e.titulo = nuevoTitulo;
        e.descripcion = nuevaDescripcion;

        if (e instanceof Deportivo dep) {
            String jugador = JOptionPane.showInputDialog(this, "Agregar jugador a equipo 1:");
            dep.agregarJugadorEquipo1(jugador);
        } else if (e instanceof Musical mus) {
            String miembro = JOptionPane.showInputDialog(this, "Agregar miembro al equipo de montaje:");
            mus.agregarMiembroMontaje(miembro);
        } else if (e instanceof Religioso rel) {
            int convertidos = Integer.parseInt(JOptionPane.showInputDialog(this, "Cantidad de personas convertidas:"));
            rel.setPersonasConvertidas(convertidos);
        }

        JOptionPane.showMessageDialog(this, "Evento editado con éxito.");
    }

    private void verEvento() {
        String codigo = JOptionPane.showInputDialog(this, "Código del evento:");
        Evento e = DataStore.buscarEvento(codigo);

        if (e == null) {
            JOptionPane.showMessageDialog(this, "Evento no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String info = "Código: " + e.getCodigo() +
                "\nTítulo: " + e.getTitulo() +
                "\nDescripción: " + e.getDescripcion() +
                "\nFecha: " + e.getFecha().format(dateFormat) +
                "\nMonto: " + e.getMontoRenta() +
                "\nTipo: " + e.getTipo() +
                (e.isCancelado() ? "\nEstado: Cancelado - Multa: " + e.getMulta() : "");

        JOptionPane.showMessageDialog(this, info);
    }
}
