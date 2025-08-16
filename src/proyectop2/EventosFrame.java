    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class EventosFrame {
    private Usuario usuario;
    private JFrame frame;

    public EventosFrame(Usuario usuario) {
        this.usuario = usuario;

        frame = new JFrame("Administración de Eventos");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnCrear = new JButton("Crear Evento");
        JButton btnEliminar = new JButton("Eliminar Evento");
        JButton btnEditar = new JButton("Editar Evento");
        JButton btnVer = new JButton("Ver Evento");
        JButton btnRegresar = new JButton("Regresar");

        frame.add(btnCrear);
        frame.add(btnEliminar);
        frame.add(btnEditar);
        frame.add(btnVer);
        frame.add(btnRegresar);

        // Restricciones de permisos
        if (usuario instanceof Limitado) {
            btnCrear.setEnabled(false);
            btnEditar.setEnabled(false);
        }

        btnCrear.addActionListener(e -> crearEvento());
        btnEliminar.addActionListener(e -> eliminarEvento());
        btnEditar.addActionListener(e -> editarEvento());
        btnVer.addActionListener(e -> verEvento());
        btnRegresar.addActionListener(e -> frame.dispose());

        frame.setVisible(true);
    }

    private void crearEvento() {
        String[] tipos = {"DEPORTIVO", "MUSICAL", "RELIGIOSO"};
        String tipo = (String) JOptionPane.showInputDialog(frame, "Seleccione tipo de evento", "Tipo",
                JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);

        if (tipo == null) return;

        String codigo = JOptionPane.showInputDialog(frame, "Código único del evento:");
        if (codigo == null || codigo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Código inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String titulo = JOptionPane.showInputDialog(frame, "Título del evento:");
        if (titulo == null || titulo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Título inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String descripcion = JOptionPane.showInputDialog(frame, "Descripción:");
        if (descripcion == null) descripcion = "";

        double monto;
        try {
            monto = Double.parseDouble(JOptionPane.showInputDialog(frame, "Monto de renta:"));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Fecha
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        int option = JOptionPane.showConfirmDialog(frame, dateChooser, "Seleccione la fecha",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(frame, "Operación cancelada.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        java.util.Date fechaDate = dateChooser.getDate();
        if (fechaDate == null) {
            JOptionPane.showMessageDialog(frame, "Fecha inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Calendar fecha = Calendar.getInstance();
        fecha.setTime(fechaDate);

        // ✅ Validación: no permitir hoy ni fechas pasadas
        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR_OF_DAY, 0);
        hoy.set(Calendar.MINUTE, 0);
        hoy.set(Calendar.SECOND, 0);
        hoy.set(Calendar.MILLISECOND, 0);

        if (!fecha.after(hoy)) {
            JOptionPane.showMessageDialog(frame, "No se pueden crear eventos para hoy o fechas pasadas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Evento evento = null;

        switch (tipo) {
            case "DEPORTIVO" -> {
                String eq1 = JOptionPane.showInputDialog(frame, "Nombre equipo 1:");
                String eq2 = JOptionPane.showInputDialog(frame, "Nombre equipo 2:");
                TipoDeporte deporte = (TipoDeporte) JOptionPane.showInputDialog(frame, "Tipo de deporte:",
                        "Deporte", JOptionPane.QUESTION_MESSAGE, null, TipoDeporte.values(), TipoDeporte.FUTBOL);
                evento = new Deportivo(codigo, titulo, descripcion, fecha, monto, eq1, eq2, deporte);
            }
            case "MUSICAL" -> {
                TipoMusica musica = (TipoMusica) JOptionPane.showInputDialog(frame, "Tipo de música:",
                        "Música", JOptionPane.QUESTION_MESSAGE, null, TipoMusica.values(), TipoMusica.POP);
                evento = new Musical(codigo, titulo, descripcion, fecha, monto, musica);
            }
            case "RELIGIOSO" -> {
                evento = new Religioso(codigo, titulo, descripcion, fecha, monto);
            }
        }

        if (DataStore.agregarEvento(evento)) {
            usuario.agregarEvento(codigo);
            JOptionPane.showMessageDialog(frame, "Evento creado con éxito.");
        } else {
            JOptionPane.showMessageDialog(frame, "El código ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEvento() {
        String codigo = JOptionPane.showInputDialog(frame, "Código del evento a eliminar:");
        Evento e = DataStore.buscarEvento(codigo);

        if (e == null) {
            JOptionPane.showMessageDialog(frame, "Evento no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!usuario.getEventosCreados().contains(codigo)) {
            JOptionPane.showMessageDialog(frame, "Solo el creador puede eliminar este evento.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Calendar ahora = Calendar.getInstance();
        Calendar fechaEvento = e.getFecha();
        Calendar fechaLimite = (Calendar) fechaEvento.clone();
        fechaLimite.add(Calendar.DAY_OF_MONTH, -1);

        if (ahora.after(fechaLimite) && !(e instanceof Religioso)) {
            e.cancelar(e.getMontoRenta() * 0.50);
        } else {
            e.cancelar(0);
        }

        DataStore.eliminarEvento(codigo);
        JOptionPane.showMessageDialog(frame, "Evento cancelado. Multa: " + e.getMulta());
    }

    private void editarEvento() {
        String codigo = JOptionPane.showInputDialog(frame, "Código del evento a editar:");
        Evento e = DataStore.buscarEvento(codigo);

        if (e == null) {
            JOptionPane.showMessageDialog(frame, "Evento no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nuevoTitulo = JOptionPane.showInputDialog(frame, "Nuevo título:", e.getTitulo());
        String nuevaDescripcion = JOptionPane.showInputDialog(frame, "Nueva descripción:", e.getDescripcion());

        e.titulo = nuevoTitulo;
        e.descripcion = nuevaDescripcion;

        if (e instanceof Deportivo dep) {
            String jugador = JOptionPane.showInputDialog(frame, "Agregar jugador a equipo 1:");
            if (jugador != null && !jugador.trim().isEmpty()) dep.agregarJugadorEquipo1(jugador);
        } else if (e instanceof Musical mus) {
            String miembro = JOptionPane.showInputDialog(frame, "Agregar miembro al equipo de montaje:");
            if (miembro != null && !miembro.trim().isEmpty()) mus.agregarMiembroMontaje(miembro);
        } else if (e instanceof Religioso rel) {
            try {
                int convertidos = Integer.parseInt(JOptionPane.showInputDialog(frame, "Cantidad de personas convertidas:"));
                rel.setPersonasConvertidas(convertidos);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Número inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        JOptionPane.showMessageDialog(frame, "Evento editado con éxito.");
    }

    private void verEvento() {
        String codigo = JOptionPane.showInputDialog(frame, "Código del evento:");
        Evento e = DataStore.buscarEvento(codigo);

        if (e == null) {
            JOptionPane.showMessageDialog(frame, "Evento no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Calendar fechaEvento = e.getFecha();
        String fechaFormateada = String.format("%02d/%02d/%04d",
                fechaEvento.get(Calendar.DAY_OF_MONTH),
                fechaEvento.get(Calendar.MONTH) + 1,
                fechaEvento.get(Calendar.YEAR));

        StringBuilder info = new StringBuilder();
        info.append("Código: ").append(e.getCodigo())
            .append("\nTítulo: ").append(e.getTitulo())
            .append("\nDescripción: ").append(e.getDescripcion())
            .append("\nFecha: ").append(fechaFormateada)
            .append("\nMonto: ").append(e.getMontoRenta())
            .append("\nTipo: ").append(e.getTipo())
            .append("\nCapacidad: ").append(e.getCapacidadMaxima())
            .append(e.isCancelado() ? "\nEstado: Cancelado - Multa: " + e.getMulta() : "");

        // Información extra por tipo
       if (e instanceof Deportivo) {
        Deportivo dep = (Deportivo) e;
             info.append("\nEquipo 1: ").append(dep.getEquipo1())
            .append("\nEquipo 2: ").append(dep.getEquipo2())
            .append("\nDeporte: ").append(dep.getTipoDeporte())
            .append("\nJugadores Equipo 1: ").append(dep.getJugadoresEquipo1())
            .append("\nJugadores Equipo 2: ").append(dep.getJugadoresEquipo2());
        } else if (e instanceof Musical) {
        Musical mus = (Musical) e;
        info.append("\nTipo de Música: ").append(mus.getTipoMusica())
        .append("\nEquipo de Montaje: ").append(mus.getEquipoMontaje());
        } else if (e instanceof Religioso) {
    Religioso rel = (Religioso) e;
    info.append("\nPersonas Convertidas: ").append(rel.getPersonasConvertidas());
           }
    }
}