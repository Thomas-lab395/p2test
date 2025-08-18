/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class Musical extends Evento {
    protected TipoMusica tipoMusica;
    protected List<String> equipoMontaje;

    public Musical(String codigo, String titulo, String descripcion, Calendar fecha, double montoRenta, TipoMusica tipoMusica, String creador) {
        super(codigo, titulo, descripcion, fecha, montoRenta, creador);
        this.tipoMusica = tipoMusica;
        this.capacidadMaxima = tipoMusica.getCapacidadMaxima();
        this.equipoMontaje = new ArrayList<>();
    }

   
    public TipoMusica getTipoMusica() {
        return tipoMusica;
    }

    public void setTipoMusica(TipoMusica tipoMusica) {
        this.tipoMusica = tipoMusica;
    }

    public List<String> getEquipoMontaje() {
        return equipoMontaje;
    }

    public void setEquipoMontaje(List<String> equipoMontaje) {
        this.equipoMontaje = equipoMontaje;
    }

    public void agregarMiembrosMontaje(List<String> miembros) {
        this.equipoMontaje.addAll(miembros);
    }

    @Override
    public String getTipo() {
       
        return "MUSICAL";
    }
}
