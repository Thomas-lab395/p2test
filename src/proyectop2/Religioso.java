/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.util.Calendar;

public class Religioso extends Evento {
    protected int personasConvertidas;

    public Religioso(String codigo, String titulo, String descripcion, Calendar fecha, double montoRenta, String creador) {
        super(codigo, titulo, descripcion, fecha, montoRenta, creador);
        this.capacidadMaxima = 20000;
        this.personasConvertidas = 0;
    }

    public int getPersonasConvertidas() {
        return personasConvertidas;
    }

    public void setPersonasConvertidas(int personasConvertidas) {
        this.personasConvertidas = personasConvertidas;
    }

    @Override
    public String getTipo() {
        return "RELIGIOSO";
    }

    @Override
    public void cancelarEvento(Calendar fechaCancelacion) {
        setCancelado(true);

        this.setMulta(0);
        this.montoRenta += 2000; 
    }
}
