/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mayra Bardales
 */
    
public class DataStore {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Evento> eventos = new ArrayList<>();

    // Inicialización con admin por defecto
    static {
        usuarios.add(new Admin("Administrador Principal", "admin", "supersecreto", 35));
    }

    // ================== USUARIOS ==================
    public static boolean agregarUsuario(Usuario usuario) {
        if (buscarUsuario(usuario.getUsername()) != null) {
            return false; // Ya existe
        }
        usuarios.add(usuario);
        return true;
    }

    public static Usuario buscarUsuario(String username) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    public static boolean eliminarUsuario(String username) {
        return usuarios.removeIf(u -> u.getUsername().equalsIgnoreCase(username));
    }

    public static List<Usuario> getUsuarios() {
        return usuarios;
    }

    // ================== EVENTOS ==================
    public static boolean agregarEvento(Evento evento) {
        if (buscarEvento(evento.getCodigo()) != null) {
            return false; // Ya existe
        }
        eventos.add(evento);
        return true;
    }

    public static Evento buscarEvento(String codigo) {
        for (Evento e : eventos) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) {
                return e;
            }
        }
        return null;
    }

    public static boolean eliminarEvento(String codigo) {
        return eventos.removeIf(e -> e.getCodigo().equalsIgnoreCase(codigo));
    }

    public static List<Evento> getEventos() {
        return eventos;
    }
}
