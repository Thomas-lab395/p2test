/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

/**
 *
 * @author Mayra Bardales
 */
 public enum TipoMusica {
    POP(25000),
    ROCK(25000),
    RAP(25000),
    CLASICA(25000),
    REGGAETON(25000),
    OTRO(25000);

    private final int capacidadMaxima;

    TipoMusica(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
}
