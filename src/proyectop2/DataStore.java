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

    
    static {
        usuarios.add(new Admin("Administrador Principal", "admin", "supersecreto", 35));
    }

    
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

    
    public static boolean agregarEvento(Evento evento) {
        if (buscarEvento(evento.getCodigo()) != null) {
            return false; 
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
        Evento e = buscarEvento(codigo);
        if (e != null) {
            e.cancelar(e.getMulta()); 
            return true;
        }
        return false;
    }

    public static List<Evento> getEventos() {
        return eventos;
    }

    
    public static int contarEventosPorTipo(List<Evento> lista, String tipo, int index) {
        if (index >= lista.size()) return 0;
        int suma = lista.get(index).getTipo().equalsIgnoreCase(tipo) ? 1 : 0;
        return suma + contarEventosPorTipo(lista, tipo, index + 1);
    }

    public static double sumarMontos(List<Evento> lista, int index) {
        if (index >= lista.size()) return 0;
        return lista.get(index).getMontoRenta() + sumarMontos(lista, index + 1);
    }

    public static double sumarMultas(List<Evento> lista, int index) {
        if (index >= lista.size()) return 0;
        double multa = lista.get(index).isCancelado() ? lista.get(index).getMulta() : 0;
        return multa + sumarMultas(lista, index + 1);
    }
}