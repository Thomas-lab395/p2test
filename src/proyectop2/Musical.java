/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Musical extends Evento {
    private TipoMusica tipoMusica;
    private List<String> equipoMontaje;

    public Musical(String codigo, String titulo, String descripcion, Calendar fecha, double montoRenta,
                   TipoMusica tipoMusica) {
        super(codigo, titulo, descripcion, fecha, montoRenta);
        setCapacidadMaxima(25000);
        this.tipoMusica = tipoMusica;
        this.equipoMontaje = new ArrayList<>();
    }

    public void agregarMiembroMontaje(String nombre) {
        equipoMontaje.add(nombre);
    }

    // ✅ Nuevo: agregar varios miembros de una sola vez
    public void agregarMiembrosMontaje(List<String> nombres) {
        equipoMontaje.addAll(nombres);
    }

    public TipoMusica getTipoMusica() {
        return tipoMusica;
    }

    public List<String> getEquipoMontaje() {
        return equipoMontaje;
    }

    @Override
    public String getTipo() {
        return "MUSICAL";
    }

    @Override
    public double getMontoRenta() {
        // Se cobra un seguro por uso de grama del 30% sobre el valor acordado
        return montoRenta * 1.3;
    }

    private void setCapacidadMaxima(int capacidad) {
        this.capacidadMaxima = capacidad;
    }
}