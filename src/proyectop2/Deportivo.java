/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class Deportivo extends Evento {
    protected String equipo1, equipo2;
    protected List<String> jugadoresEquipo1;
    protected List<String> jugadoresEquipo2;
    protected TipoDeporte tipoDeporte;

    public Deportivo(String codigo, String titulo, String descripcion, Calendar fecha, double montoRenta, String equipo1, String equipo2, TipoDeporte tipoDeporte, String creador) {
        super(codigo, titulo, descripcion, fecha, montoRenta, creador);
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.tipoDeporte = tipoDeporte;
        this.capacidadMaxima = tipoDeporte.getCapacidadMaxima();
        this.jugadoresEquipo1 = new ArrayList<>();
        this.jugadoresEquipo2 = new ArrayList<>();
    }

    // Getters y Setters
    public String getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(String equipo1) {
        this.equipo1 = equipo1;
    }

    public String getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(String equipo2) {
        this.equipo2 = equipo2;
    }

    public List<String> getJugadoresEquipo1() {
        return jugadoresEquipo1;
    }

    public void setJugadoresEquipo1(List<String> jugadoresEquipo1) {
        this.jugadoresEquipo1 = jugadoresEquipo1;
    }

    public List<String> getJugadoresEquipo2() {
        return jugadoresEquipo2;
    }

    public void setJugadoresEquipo2(List<String> jugadoresEquipo2) {
        this.jugadoresEquipo2 = jugadoresEquipo2;
    }

    public TipoDeporte getTipoDeporte() {
        return tipoDeporte;
    }

    public void setTipoDeporte(TipoDeporte tipoDeporte) {
        this.tipoDeporte = tipoDeporte;
    }

    public void agregarJugadorEquipo1(String jugador) {
        if (jugadoresEquipo1 == null) {
            jugadoresEquipo1 = new ArrayList<>();
        }
        this.jugadoresEquipo1.add(jugador);
    }

    public void agregarJugadorEquipo2(String jugador) {
        if (jugadoresEquipo2 == null) {
            jugadoresEquipo2 = new ArrayList<>();
        }
        this.jugadoresEquipo2.add(jugador);
    }

    @Override
    public String getTipo() {
       
        return "DEPORTIVO";
    }
}
