package logicaTest;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

import logica.Usuario;
import logica.Grafo;
import logica.Arista;

public class GrafoTest {

    @Test
    public void grafoConDosUsuariosTieneUnaArista() {
        Usuario u1 = new Usuario("Ana", 5, 3, 2, 1);
        Usuario u2 = new Usuario("Luis", 4, 2, 3, 1);
        List<Usuario> usuarios = Arrays.asList(u1, u2);

        Grafo g = new Grafo(usuarios);

        assertEquals(2, g.getUsuarios().size());
        assertEquals(1, g.getAristas().size()); 
    }

    @Test
    public void grafoConTresUsuariosTieneTresAristas() {
        Usuario u1 = new Usuario("Ana", 5, 3, 2, 1);
        Usuario u2 = new Usuario("Luis", 4, 2, 3, 1);
        Usuario u3 = new Usuario("Marta", 1, 5, 4, 2);
        List<Usuario> usuarios = Arrays.asList(u1, u2, u3);

        Grafo g = new Grafo(usuarios);

        assertEquals(3, g.getAristas().size()); 
    }

    // MST de n nodos tiene n-1 aristas
    @Test
    public void mstDeCuatroUsuariosTieneTresAristas() {
        Usuario u1 = new Usuario("Ana", 5, 3, 2, 1);
        Usuario u2 = new Usuario("Luis", 4, 2, 3, 1);
        Usuario u3 = new Usuario("Marta", 1, 5, 4, 2);
        Usuario u4 = new Usuario("Pedro", 2, 4, 1, 4);
        List<Usuario> usuarios = Arrays.asList(u1, u2, u3, u4);

        Grafo g = new Grafo(usuarios);
        List<Arista> mst = g.kruskal();

        assertEquals(usuarios.size() - 1, mst.size()); 
    }

    @Test
    public void obtenerGruposDevuelveDosGrupos() {
        Usuario u1 = new Usuario("Ana", 5, 3, 2, 1);
        Usuario u2 = new Usuario("Luis", 5, 3, 2, 1); 
        Usuario u3 = new Usuario("Marta", 1, 5, 4, 2);
        Usuario u4 = new Usuario("Pedro", 1, 5, 4, 2); 
        List<Usuario> usuarios = Arrays.asList(u1, u2, u3, u4);

        Grafo g = new Grafo(usuarios);
        List<List<Usuario>> grupos = g.obtenerGrupos();

        assertEquals(2, grupos.size());
        int totalUsuarios = grupos.get(0).size() + grupos.get(1).size();
        assertEquals(usuarios.size(), totalUsuarios); // todos los usuarios están en algún grupo
    }

    @Test
    public void similaridadEntreUsuariosCorrecta() {
        Usuario u1 = new Usuario("Ana", 5, 3, 2, 1);
        Usuario u2 = new Usuario("Luis", 4, 2, 3, 1);
        List<Usuario> usuarios = Arrays.asList(u1, u2);

        Grafo g = new Grafo(usuarios);
        int pesoArista = g.getAristas().get(0).getPeso();

        // Similaridad manual: |5-4| + |3-2| + |2-3| + |1-1| = 1+1+1+0 = 3
        assertEquals(3, pesoArista);
    }
}
