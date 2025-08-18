/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Mayra Bardales
 */


public class DataStore {
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final List<Evento> eventos = new ArrayList<>();

    static {
        usuarios.add(new Admin("Administrador Principal", "admin", "supersecreto", 35)); // ✅ usuario inicial
    }

    // ---- Usuarios ----
    public static boolean agregarUsuario(Usuario usuario) {
        if (buscarUsuario(usuario.getUsername()) != null) return false;
        usuarios.add(usuario);
        return true;
    }

    public static Usuario buscarUsuario(String username) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equalsIgnoreCase(username)) return u;
        }
        return null;
    }

    public static boolean eliminarUsuario(String username) {
        return usuarios.removeIf(u -> u.getUsername().equalsIgnoreCase(username));
    }

    public static List<Usuario> getUsuarios() { return usuarios; }

    // ---- Eventos ----
    public static boolean agregarEvento(Evento evento) {
        if (buscarEvento(evento.getCodigo()) != null) return false;
        if (existeEventoEnFecha(evento.getFecha())) return false;
        eventos.add(evento);
        return true;
    }

    public static Evento buscarEvento(String codigo) {
        for (Evento e : eventos) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) return e;
        }
        return null;
    }

    public static boolean eliminarEvento(String codigo) {
        return eventos.removeIf(e -> e.getCodigo().equalsIgnoreCase(codigo));
    }

    public static List<Evento> getEventos() { return eventos; }

    public static boolean existeEventoEnFecha(Calendar fecha) {
        for (Evento e : eventos) {
            if (mismoDia(e.getFecha(), fecha)) return true;
        }
        return false;
    }

    private static boolean mismoDia(Calendar a, Calendar b) {
        return a.get(Calendar.YEAR) == b.get(Calendar.YEAR) &&
               a.get(Calendar.DAY_OF_YEAR) == b.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean actualizarEvento(Evento eventoActualizado) {
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).getCodigo().equalsIgnoreCase(eventoActualizado.getCodigo())) {
                eventos.set(i, eventoActualizado);
                return true;
            }
        }
        return false;
    }
}