/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CrearEventoFrame extends JFrame {
    private final Usuario usuario;
    private final boolean modoEdicion;
    private String codigoOriginal;

    public JComboBox<String> cTipo;
    public JTextField cCodigo, cTitulo, cMonto, cEq1, cEq2;
    public JTextArea cDescripcion;
    public JDateChooser cFecha;
    public JComboBox<TipoDeporte> cDeporte;
    public JComboBox<TipoMusica> cMusica;
    public JTextField cJugEq1, cJugEq2, cMontaje;
    public JTextField cConvertidos; 

    public CrearEventoFrame(Usuario usuario) {
        this(usuario, null);
    }

    public CrearEventoFrame(Usuario usuario, Evento existente) {
        this.usuario = usuario;
        this.modoEdicion = (existente != null);
        this.codigoOriginal = (existente != null ? existente.getCodigo() : null);

        setTitle(modoEdicion ? "Editar Evento" : "Crear Evento");
        setSize(520, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));

        cTipo = new JComboBox<>(new String[]{"DEPORTIVO", "MUSICAL", "RELIGIOSO"});
        cCodigo = new JTextField();
        cTitulo = new JTextField();
        cDescripcion = new JTextArea(3, 20);
        cMonto = new JTextField();
        cFecha = new JDateChooser();
        cFecha.setDateFormatString("dd/MM/yyyy");

        form.add(new JLabel("Tipo:"));
        form.add(cTipo);
        form.add(new JLabel("Código:"));
        form.add(cCodigo);
        form.add(new JLabel("Título:"));
        form.add(cTitulo);
        form.add(new JLabel("Descripción:"));
        form.add(new JScrollPane(cDescripcion));
        form.add(new JLabel("Monto renta:"));
        form.add(cMonto);
        form.add(new JLabel("Fecha:"));
        form.add(cFecha);

        cEq1 = new JTextField();
        cEq2 = new JTextField();
        cDeporte = new JComboBox<>(TipoDeporte.values());
        cJugEq1 = new JTextField();
        cJugEq2 = new JTextField();

        cMusica = new JComboBox<>(TipoMusica.values());
        cMontaje = new JTextField();

        cConvertidos = new JTextField();

        JPanel subForm = new JPanel(new GridLayout(0, 2, 10, 10));
        cTipo.addActionListener(e -> {
            subForm.removeAll();
            switch ((String) cTipo.getSelectedItem()) {
                case "DEPORTIVO" -> {
                    subForm.add(new JLabel("Equipo 1:"));
                    subForm.add(cEq1);
                    subForm.add(new JLabel("Jugadores Eq1 (coma):"));
                    subForm.add(cJugEq1);
                    subForm.add(new JLabel("Equipo 2:"));
                    subForm.add(cEq2);
                    subForm.add(new JLabel("Jugadores Eq2 (coma):"));
                    subForm.add(cJugEq2);
                    subForm.add(new JLabel("Deporte:"));
                    subForm.add(cDeporte);
                }
                case "MUSICAL" -> {
                    subForm.add(new JLabel("Tipo música:"));
                    subForm.add(cMusica);
                    subForm.add(new JLabel("Equipo montaje (coma):"));
                    subForm.add(cMontaje);
                }
                case "RELIGIOSO" -> {
                    if (modoEdicion) {
                        subForm.add(new JLabel("Personas convertidas:"));
                        subForm.add(cConvertidos);
                    }
                }
                default -> {
                }
            }
            subForm.revalidate();
            subForm.repaint();
        });
        cTipo.setSelectedIndex(0);

        if (modoEdicion) {
            cCodigo.setText(existente.getCodigo());
            cCodigo.setEditable(false);
            cTitulo.setText(existente.getTitulo());
            cDescripcion.setText(existente.getDescripcion());
            cMonto.setText(String.valueOf(existente.montoRenta));
            cFecha.setDate(existente.getFecha().getTime());

            if (existente instanceof Deportivo dep) {
                cTipo.setSelectedItem("DEPORTIVO");
                cEq1.setText(dep.getEquipo1());
                cEq2.setText(dep.getEquipo2());
                cDeporte.setSelectedItem(dep.getTipoDeporte());
                cJugEq1.setText(String.join(",", dep.getJugadoresEquipo1()));
                cJugEq2.setText(String.join(",", dep.getJugadoresEquipo2()));
            } else if (existente instanceof Musical mus) {
                cTipo.setSelectedItem("MUSICAL");
                cMusica.setSelectedItem(mus.getTipoMusica());
                cMontaje.setText(String.join(",", mus.getEquipoMontaje()));
            } else if (existente instanceof Religioso rel) {
                cTipo.setSelectedItem("RELIGIOSO");
                cConvertidos.setText(String.valueOf(rel.getPersonasConvertidas()));
            }
        }

        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.add(form);
        mainContentPanel.add(subForm);
        
        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton(modoEdicion ? "Guardar Cambios" : "Guardar Evento");
        JButton btnRegresar = new JButton("Regresar");

        bottom.add(btnGuardar);
        bottom.add(btnRegresar);
        add(bottom, BorderLayout.SOUTH);

        btnGuardar.addActionListener(e -> guardarEvento());
        btnRegresar.addActionListener(e -> {
            dispose();
            new EventosFrame(usuario).setVisible(true);
        });

        setVisible(true);
    }

    private void guardarEvento() {
        String tipo = (String) cTipo.getSelectedItem();
        String codigo = cCodigo.getText().trim();
        String titulo = cTitulo.getText().trim();
        String desc = cDescripcion.getText().trim();
        Date fechaDate = cFecha.getDate();
        

        String creador = usuario.getUsername();

        if (codigo.isEmpty() || titulo.isEmpty() || fechaDate == null) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.");
            return;
        }

        double monto;
        try {
            monto = Double.parseDouble(cMonto.getText().trim());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Monto inválido");
            return;
        }

        Calendar fecha = Calendar.getInstance();
        fecha.setTime(fechaDate);

        if (!modoEdicion || (modoEdicion && !DataStore.buscarEvento(codigo).getFecha().equals(fecha))) {
            if (DataStore.existeEventoEnFecha(fecha)) {
                JOptionPane.showMessageDialog(this, "Ya existe un evento en esa fecha.");
                return;
            }
        }

        Evento evento = null;
        switch (tipo) {
            case "DEPORTIVO" -> { 
                Deportivo d = new Deportivo(codigo, titulo, desc, fecha, monto,
                        cEq1.getText(), cEq2.getText(),
                        (TipoDeporte) cDeporte.getSelectedItem(), creador);

                int limite = d.getTipoDeporte().getLimiteJugadores();
                String[] eq1 = cJugEq1.getText().isBlank() ? new String[0] : cJugEq1.getText().split(",");
                String[] eq2 = cJugEq2.getText().isBlank() ? new String[0] : cJugEq2.getText().split(",");

                if (eq1.length > limite || eq2.length > limite) {
                    JOptionPane.showMessageDialog(this, "Límite de jugadores excedido: " + limite + " por equipo");
                    return;
                }

                for (String j : eq1) if (!j.isBlank()) d.agregarJugadorEquipo1(j.trim());
                for (String j : eq2) if (!j.isBlank()) d.agregarJugadorEquipo2(j.trim());
                evento = d;
            }
            case "MUSICAL" -> {
               
                Musical m = new Musical(codigo, titulo, desc, fecha, monto,
                        (TipoMusica) cMusica.getSelectedItem(), creador);
                List<String> mont = new ArrayList<>();
                if (!cMontaje.getText().isBlank()) {
                    for (String j : cMontaje.getText().split(",")) if (!j.isBlank()) mont.add(j.trim());
                }
                m.agregarMiembrosMontaje(mont);
                evento = m;
            }
            case "RELIGIOSO" -> {
                
                Religioso r = new Religioso(codigo, titulo, desc, fecha, monto, creador);
                try {
                    if (modoEdicion) {
                        int convertidos = Integer.parseInt(cConvertidos.getText().trim());
                        r.setPersonasConvertidas(convertidos);
                    }
                } catch (Exception ignored) {
                }
                evento = r;
            }
            default -> {
            }
        }

        boolean ok = false;
        if (modoEdicion) {
           
            if (DataStore.actualizarEvento(evento)) {
                ok = true;
            }
        } else {
            if (DataStore.buscarEvento(codigo) != null) {
                JOptionPane.showMessageDialog(this, "El código del evento ya existe.");
                return;
            }
            ok = DataStore.agregarEvento(evento);
            if (ok) usuario.agregarEvento(codigo);
        }

        if (ok) {
            JOptionPane.showMessageDialog(this, modoEdicion ? "Cambios guardados" : "Evento creado con éxito");
            dispose();
            new EventosFrame(usuario).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar (código duplicado o fecha ocupada).");
        }
    }
}






