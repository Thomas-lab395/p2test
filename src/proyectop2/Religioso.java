/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.time.LocalDate;

/**
 *
 * @author Mayra Bardales
 */

public class Religioso extends Evento {
    private int personasConvertidas;

    public Religioso(String codigo, String titulo, String descripcion, LocalDate fecha, double montoRenta) {
        super(codigo, titulo, descripcion, fecha, montoRenta);
        this.capacidadMaxima = 30000;
        this.montoRenta += 2000; // Seguro fijo
        this.personasConvertidas = 0;
    }

    public void setPersonasConvertidas(int cantidad) {
        this.personasConvertidas = cantidad;
    }

    @Override
    public String getTipo() { return "RELIGIOSO"; }
}