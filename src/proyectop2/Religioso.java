/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.util.Calendar;

public class Religioso extends Evento {
    private int personasConvertidas;

    public Religioso(String codigo, String titulo, String descripcion, Calendar fecha, double montoRenta) {
        super(codigo, titulo, descripcion, fecha, montoRenta);
        setCapacidadMaxima(30000);
        this.personasConvertidas = 0;
    }

    public void setPersonasConvertidas(int cantidad) {
        this.personasConvertidas = cantidad;
    }

    @Override
    public String getTipo() {
        return "RELIGIOSO";
    }

    @Override
    public double getMontoRenta() {
        // Añade seguro fijo de 2000 al monto base
        return montoRenta + 2000;
    }

    private void setCapacidadMaxima(int capacidad) {
        this.capacidadMaxima = capacidad;
    }
}