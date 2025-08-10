/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Mayra Bardales
 */

public class Musical extends Evento {
    private TipoMusica tipoMusica;
    private List<String> equipoMontaje;

    public Musical(String codigo, String titulo, String descripcion, LocalDate fecha, double montoRenta,
                   TipoMusica tipoMusica) {
        super(codigo, titulo, descripcion, fecha, montoRenta);
        this.capacidadMaxima = 25000;
        this.tipoMusica = tipoMusica;
        this.equipoMontaje = new ArrayList<>();
        this.montoRenta += montoRenta * 0.30; // Seguro 30%
    }

    public void agregarMiembroMontaje(String nombre) {
        equipoMontaje.add(nombre);
    }

    @Override
    public String getTipo() { return "MUSICAL"; }
}