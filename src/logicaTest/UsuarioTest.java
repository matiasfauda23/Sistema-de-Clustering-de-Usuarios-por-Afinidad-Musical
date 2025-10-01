package logicaTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.*;

import logica.Usuario;


public class UsuarioTest {

    @Test
    public void crearUsuarioValido() {
        Usuario u = new Usuario("Ana", 3, 4, 5, 2);
        assertEquals("Ana", u.getNombre());
        assertEquals(3, u.getValorTango());
        assertEquals(4, u.getValorFolklore());
        assertEquals(5, u.getValorRock());
        assertEquals(2, u.getValorUrbano());
    }

    @Test
    public void crearUsuarioConValoresInvalidosLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario("Pepe", 0, 3, 4, 5));
        assertThrows(IllegalArgumentException.class,
                () -> new Usuario("Pepe", 6, 3, 4, 5));
    }
}

