    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectop2;

import javax.swing.*;
import java.awt.*;

public class UsuariosFrame extends JFrame {
    private final Usuario usuario;

    public UsuariosFrame(Usuario usuario) {
        this.usuario = usuario;

        if (!(usuario instanceof Admin)) {
            JOptionPane.showMessageDialog(this,
                    "Acceso denegado. Solo un ADMINISTRADOR puede administrar usuarios.",
                    "Permiso denegado", JOptionPane.ERROR_MESSAGE);
            dispose();
            new MenuPrincipalFrame(usuario).setVisible(true);
            return;
        }

        setTitle("Administración de Usuarios");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnCrear = new JButton("Crear Usuario");
        JButton btnEditar = new JButton("Editar Usuario");
        JButton btnEliminar = new JButton("Eliminar Usuario");
        JButton btnRegresar = new JButton("Regresar");

        add(btnCrear);
        add(btnEditar);
        add(btnEliminar);
        add(btnRegresar);

        btnCrear.addActionListener(e -> crearUsuario());
        btnEditar.addActionListener(e -> editarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        
        btnRegresar.addActionListener(e -> {
            dispose();
            new MenuPrincipalFrame(usuario).setVisible(true);
        });
    }

    private void crearUsuario() {
        String[] tipos = {"ADMINISTRADOR", "CONTENIDOS", "LIMITADO"};
        String tipo = (String) JOptionPane.showInputDialog(this, "Seleccione tipo de usuario:", "Tipo",
                JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
        if (tipo == null) return;

        String nombre = JOptionPane.showInputDialog(this, "Nombre completo:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        String username = JOptionPane.showInputDialog(this, "Username:");
        if (username == null || username.trim().isEmpty()) return;
        if (DataStore.buscarUsuario(username) != null) {
            JOptionPane.showMessageDialog(this, "El username ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String password;
        while (true) {
            password = JOptionPane.showInputDialog(this, "Contraseña (mín 8 caracteres, letra, número y símbolo):");
            if (password == null) return;
            if (validarPassword(password)) break;
            JOptionPane.showMessageDialog(this, "Contraseña inválida.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        int edad;
        try {
            String edadStr = JOptionPane.showInputDialog(this, "Edad:");
            if (edadStr == null || edadStr.trim().isEmpty()) return; 
            edad = Integer.parseInt(edadStr);
            if (edad < 18) {
                JOptionPane.showMessageDialog(this, "La edad debe ser 18 o mayor.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Edad inválida. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevo;
        switch (tipo) {
            case "ADMINISTRADOR" -> nuevo = new Admin(nombre, username, password, edad);
            case "CONTENIDOS" -> nuevo = new Contenido(nombre, username, password, edad);
            default -> nuevo = new Limitado(nombre, username, password, edad);
        }

        if (DataStore.agregarUsuario(nuevo)) {
            JOptionPane.showMessageDialog(this, "Usuario creado con éxito.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al crear usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarUsuario() {
        String username = JOptionPane.showInputDialog(this, "Username del usuario a editar:");
        if (username == null || username.trim().isEmpty()) return; 

        Usuario u = DataStore.buscarUsuario(username);
        if (u == null) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", u.getNombre());
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            u.setNombre(nuevoNombre);
        }

        String nuevaPassword;
        while (true) {
            nuevaPassword = JOptionPane.showInputDialog(this, "Nueva contraseña (mín 8 caracteres, letra, número y símbolo):", u.getPassword());
            if (nuevaPassword == null) break;
            if (validarPassword(nuevaPassword)) {
                u.setPassword(nuevaPassword);
                break;
            }
            JOptionPane.showMessageDialog(this, "Contraseña inválida.", "Error", JOptionPane.ERROR_MESSAGE);
        }

       
        try {
            String nuevaEdadStr = JOptionPane.showInputDialog(this, "Nueva edad:", u.getEdad());
            if (nuevaEdadStr != null && !nuevaEdadStr.trim().isEmpty()) {
                int nuevaEdad = Integer.parseInt(nuevaEdadStr);
                if (nuevaEdad < 18) {
                    JOptionPane.showMessageDialog(this, "La edad debe ser 18 o mayor.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                u.setEdad(nuevaEdad);
            }
        } catch (NumberFormatException ignored) {
             JOptionPane.showMessageDialog(this, "Edad inválida. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        JOptionPane.showMessageDialog(this, "Usuario editado con éxito.");
    }

    private void eliminarUsuario() {
        String username = JOptionPane.showInputDialog(this, "Username del usuario a eliminar:");
        if (username == null) return;
        if (DataStore.eliminarUsuario(username)) {
            JOptionPane.showMessageDialog(this, "Usuario eliminado.");
        } else {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarPassword(String pass) {
        return pass.length() >= 8 &&
                pass.matches(".*[A-Za-z].*") &&
                pass.matches(".*\\d.*") &&
                pass.matches(".*[^A-Za-z0-9].*");
    }
}
