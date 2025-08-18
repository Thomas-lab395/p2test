/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    protected String nombreCompleto;
    protected String username;
    protected String password;
    protected int edad;
    protected List<String> eventosCreados;

    public Usuario(String nombreCompleto, String username, String password, int edad) {
        this.nombreCompleto = nombreCompleto;
        this.username = username;
        this.password = password;
        this.edad = edad;
        this.eventosCreados = new ArrayList<>();
    }

    public String getNombre() { return nombreCompleto; }
    public void setNombre(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public List<String> getEventosCreados() { return eventosCreados; }
    public void agregarEvento(String codigo) { eventosCreados.add(codigo); }

    public abstract String getTipoUsuario();
}