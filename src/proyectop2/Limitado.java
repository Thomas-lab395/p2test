/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

public final class Limitado extends Usuario {

    public Limitado(String nombreCompleto, String username, String password, int edad) {
        super(nombreCompleto, username, password, edad);
    }

    @Override
    public String getTipoUsuario() {
        return "LIMITADO";
    }
}
