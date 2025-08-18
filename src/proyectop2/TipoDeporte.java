/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

/**
 *
 * @author Mayra Bardales
 */
public enum TipoDeporte {
    FUTBOL(11, 20000),
    RUGBY(15, 20000),
    BASEBALL(9, 20000),
    TENIS(2, 20000);

    private final int limiteJugadores;
    private final int capacidadMaxima;

    TipoDeporte(int limiteJugadores, int capacidadMaxima) {
        this.limiteJugadores = limiteJugadores;
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getLimiteJugadores() {
        return limiteJugadores;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
}
