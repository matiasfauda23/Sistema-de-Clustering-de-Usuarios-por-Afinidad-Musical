package logicaTest;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

import logica.Usuario;
import logica.UnionFind;

public class UnionFindTest {

    @Test
    public void cadaUsuarioEsSuPropioConjuntoAlInicio() {
        Usuario u1 = new Usuario("Ana", 3, 3, 3, 3);
        Usuario u2 = new Usuario("Luis", 4, 4, 4, 4);
        List<Usuario> usuarios = Arrays.asList(u1, u2);

        UnionFind uf = new UnionFind(usuarios);

        assertEquals(u1, uf.encontrarRaiz(u1));
        assertEquals(u2, uf.encontrarRaiz(u2));
        assertNotEquals(uf.encontrarRaiz(u1), uf.encontrarRaiz(u2));
    }

    @Test
    public void unirHaceQueDosUsuariosEsténEnElMismoConjunto() {
        Usuario u1 = new Usuario("Ana", 3, 3, 3, 3);
        Usuario u2 = new Usuario("Luis", 4, 4, 4, 4);
        List<Usuario> usuarios = Arrays.asList(u1, u2);

        UnionFind uf = new UnionFind(usuarios);
        uf.unir(u1, u2);

        assertEquals(uf.encontrarRaiz(u1), uf.encontrarRaiz(u2));
        assertTrue(uf.estanConectados(u1, u2));
    }

    @Test
    public void unirVariosUsuariosLosConectaEnElMismoConjunto() {
        Usuario u1 = new Usuario("Ana", 3, 3, 3, 3);
        Usuario u2 = new Usuario("Luis", 4, 4, 4, 4);
        Usuario u3 = new Usuario("Marta", 5, 5, 5, 5);
        List<Usuario> usuarios = Arrays.asList(u1, u2, u3);

        UnionFind uf = new UnionFind(usuarios);

        uf.unir(u1, u2);
        uf.unir(u2, u3);

        assertEquals(uf.encontrarRaiz(u1), uf.encontrarRaiz(u3));
        assertTrue(uf.estanConectados(u1, u3));
    }

    @Test
    public void unionByRankMantieneEstructuraEquilibrada() {
        Usuario u1 = new Usuario("Ana", 3, 3, 3, 3);
        Usuario u2 = new Usuario("Luis", 4, 4, 4, 4);
        Usuario u3 = new Usuario("Marta", 5, 5, 5, 5);
        List<Usuario> usuarios = Arrays.asList(u1, u2, u3);

        UnionFind uf = new UnionFind(usuarios);

        uf.unir(u1, u2);
        Usuario raiz1 = uf.encontrarRaiz(u1);
        Usuario raiz2 = uf.encontrarRaiz(u2);

        // Despues de unir, u1 y u2 comparten la misma raiz
        assertEquals(raiz1, raiz2);

        uf.unir(u1, u3);
        Usuario raiz3 = uf.encontrarRaiz(u3);

        // Ahora los tres deben compartir raiz
        assertEquals(raiz1, raiz3);
    }

    @Test
    public void estanConectadosDetectaCorrectamente() {
        Usuario u1 = new Usuario("Ana", 3, 3, 3, 3);
        Usuario u2 = new Usuario("Luis", 4, 4, 4, 4);
        Usuario u3 = new Usuario("Marta", 5, 5, 5, 5);
        List<Usuario> usuarios = Arrays.asList(u1, u2, u3);

        UnionFind uf = new UnionFind(usuarios);

        uf.unir(u1, u2);

        assertTrue(uf.estanConectados(u1, u2));
        assertFalse(uf.estanConectados(u1, u3));
    }
}
