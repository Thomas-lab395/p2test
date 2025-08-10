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
public class Deportivo extends Evento {
    private String equipo1;
    private String equipo2;
    private TipoDeporte tipoDeporte;
    private List<String> jugadoresEquipo1;
    private List<String> jugadoresEquipo2;

    public Deportivo(String codigo, String titulo, String descripcion, LocalDate fecha, double montoRenta,
                     String equipo1, String equipo2, TipoDeporte tipoDeporte) {
        super(codigo, titulo, descripcion, fecha, montoRenta);
        this.capacidadMaxima = 20000;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.tipoDeporte = tipoDeporte;
        this.jugadoresEquipo1 = new ArrayList<>();
        this.jugadoresEquipo2 = new ArrayList<>();
    }

    public void agregarJugadorEquipo1(String jugador) { jugadoresEquipo1.add(jugador); }
    public void agregarJugadorEquipo2(String jugador) { jugadoresEquipo2.add(jugador); }

    @Override
    public String getTipo() { return "DEPORTIVO"; }
}

