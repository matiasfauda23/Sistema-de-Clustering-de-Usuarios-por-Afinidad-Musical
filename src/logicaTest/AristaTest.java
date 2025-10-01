package logicaTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import logica.Arista;
import logica.Usuario;

public class AristaTest {

    @Test
    public void crearAristaValida() {
        Usuario u1 = new Usuario("Ana", 3, 3, 3, 3);
        Usuario u2 = new Usuario("Luis", 4, 4, 4, 4);
        Arista a = new Arista(u1, u2, 5);

        assertEquals(u1, a.getUsuario1());
        assertEquals(u2, a.getUsuario2());
        assertEquals(5, a.getPeso());
        assertTrue(a.toString().contains("Ana"));
    }

    @Test
    public void noSePuedeCrearAristaConUsuariosIguales() {
        Usuario u1 = new Usuario("Ana", 3, 3, 3, 3);
        assertThrows(IllegalArgumentException.class, () -> new Arista(u1, u1, 2));
    }

    @Test
    public void compareToOrdenaPorPeso() {
        Usuario u1 = new Usuario("Ana", 3, 3, 3, 3);
        Usuario u2 = new Usuario("Luis", 4, 4, 4, 4);
        Arista a1 = new Arista(u1, u2, 2);
        Arista a2 = new Arista(u1, u2, 5);

        assertTrue(a1.compareTo(a2) < 0);
    }
}
