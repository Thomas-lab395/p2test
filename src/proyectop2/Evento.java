/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.util.Calendar;

public abstract class Evento {
    protected String codigo;
    protected String titulo;
    protected String descripcion;
    protected Calendar fecha;
    protected double montoRenta;
    protected int capacidadMaxima;
    protected boolean cancelado;
    protected double multa;

    public Evento(String codigo, String titulo, String descripcion, Calendar fecha, double montoRenta) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.montoRenta = montoRenta;
        this.cancelado = false;
        this.multa = 0;
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Calendar getFecha() { return fecha; }
    public double getMontoRenta() { return montoRenta; }
    public int getCapacidadMaxima() { return capacidadMaxima; }
    public boolean isCancelado() { return cancelado; }
    public double getMulta() { return multa; }

    public void cancelar(double multa) {
        this.cancelado = true;
        this.multa = multa;
    }

    public abstract String getTipo();
}