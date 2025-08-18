/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class DataStore implements Serializable {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Evento> eventos = new ArrayList<>();

    public static boolean agregarUsuario(Usuario nuevo) {
        if (buscarUsuario(nuevo.getUsername()) == null) {
            return usuarios.add(nuevo);
        }
        return false;
    }

    public static Usuario buscarUsuario(String username) {
        for (Usuario u : usuarios) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public static boolean eliminarUsuario(String username) {
        Usuario u = buscarUsuario(username);
        return usuarios.remove(u);
    }
    
    public static boolean agregarEvento(Evento nuevo) {
        if (buscarEvento(nuevo.getCodigo()) == null) {
            return eventos.add(nuevo);
        }
        return false;
    }
    
    public static Evento buscarEvento(String codigo) {
        for(Evento e : eventos) {
            if(e.getCodigo().equals(codigo)) return e;
        }
        return null;
    }

    public static List<Evento> getEventos() {
        return eventos;
    }

    public static Evento buscarEventoRecursivo(List<Evento> lista, String codigo) {
        if (lista.isEmpty()) {
            return null;
        }
        
        Evento primerEvento = lista.get(0);
        
        if (primerEvento.getCodigo().equals(codigo)) {
            return primerEvento;
        }
        
        return buscarEventoRecursivo(lista.subList(1, lista.size()), codigo);
    }

    public static int contarEventosPorTipoRecursivo(List<Evento> lista, String tipo) {
        if (lista.isEmpty()) {
            return 0;
        }
        
        Evento primerEvento = lista.get(0);
        int contador = 0;
        
        if (primerEvento.getTipo().equals(tipo)) {
            contador = 1;
        }
        
        return contador + contarEventosPorTipoRecursivo(lista.subList(1, lista.size()), tipo);
    }

    public static int calcularCapacidadTotalRecursivo(List<Evento> lista) {
        if (lista.isEmpty()) {
            return 0;
        }
        
        Evento primerEvento = lista.get(0);
        
        return primerEvento.getCapacidadMaxima() + calcularCapacidadTotalRecursivo(lista.subList(1, lista.size()));
    }

    public static boolean existeEventoEnFecha(Calendar fecha) {
        for (Evento e : eventos) {
            if (e.getFecha().get(Calendar.YEAR) == fecha.get(Calendar.YEAR) &&
                e.getFecha().get(Calendar.MONTH) == fecha.get(Calendar.MONTH) &&
                e.getFecha().get(Calendar.DAY_OF_MONTH) == fecha.get(Calendar.DAY_OF_MONTH)) {
                return true;
            }
        }
        return false;
    }

    public static boolean actualizarEvento(Evento evento) {
        for (int i = 0; i < eventos.size(); i++) {
            if (eventos.get(i).getCodigo().equals(evento.getCodigo())) {
                eventos.set(i, evento);
                return true;
            }
        }
        return false;
    }
}

