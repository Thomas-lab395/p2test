/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public abstract class Evento implements Serializable {
    protected String codigo;
    protected String titulo;
    protected String descripcion;
    protected Calendar fecha;
    protected double montoRenta;
    protected int capacidadMaxima;
    protected boolean cancelado;
    protected double multa;
    protected String creador;

    public Evento(String codigo, String titulo, String descripcion, Calendar fecha, double montoRenta, String creador) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.montoRenta = montoRenta;
        this.cancelado = false;
        this.multa = 0;
        this.creador = creador;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Calendar getFecha() { return fecha; }
    public void setFecha(Calendar fecha) { this.fecha = fecha; }

    public double getMontoRenta() { return montoRenta; }
    public void setMontoRenta(double montoRenta) { this.montoRenta = montoRenta; }

    public int getCapacidadMaxima() { return capacidadMaxima; }
    protected void setCapacidadMaxima(int capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }

    public boolean isCancelado() { return cancelado; }
    public double getMulta() { return multa; }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }

    public String getCreador() { return creador; }
    public void setCreador(String creador) { this.creador = creador; }

    public abstract String getTipo();

    public void cancelarEvento(Calendar fechaCancelacion) {
        this.cancelado = true;
        this.multa = this.montoRenta * 0.50;
    }
    
    public final long diasRestantes() {
        long diff = this.fecha.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public String toString() {
        return "Evento{" + "codigo=" + codigo + ", titulo=" + titulo + ", descripcion=" + descripcion + ", fecha=" + fecha.getTime() + ", montoRenta=" + montoRenta + ", capacidadMaxima=" + capacidadMaxima + ", cancelado=" + cancelado + ", multa=" + multa + ", creador=" + creador + '}';
    }
}
