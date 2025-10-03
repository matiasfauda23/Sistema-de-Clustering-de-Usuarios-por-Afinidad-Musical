package controlador;

import java.util.ArrayList;
import java.util.List;

import logica.Grafo;
import logica.Usuario;

public class ControladorAfinidad {
    private List<Usuario> usuarios;

    public ControladorAfinidad() {
        this.usuarios = new ArrayList<>();
    }

    public void agregarUsuario(String nombre, int t, int f, int r, int u) {
        Usuario nuevo = new Usuario(nombre, t, f, r, u);
        usuarios.add(nuevo);
    }

    public List<List<Usuario>> calcularGrupos(int k) {
        Grafo grafo = new Grafo(usuarios);
        return grafo.obtenerGrupos(k);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }
}
