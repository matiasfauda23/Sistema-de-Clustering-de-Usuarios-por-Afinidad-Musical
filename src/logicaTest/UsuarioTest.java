package logicaTest;

import static org.junit.Assert.*;
import org.junit.Test;

import logica.Usuario;

public class UsuarioTest {

    @Test
    public void crearUsuarioValido() {
        Usuario u = new Usuario("Ana", 3, 4, 5, 1);

        assertEquals("Ana", u.getNombre());
        assertEquals(3, u.getValorTango());
        assertEquals(4, u.getValorFolklore());
        assertEquals(5, u.getValorRock());
        assertEquals(1, u.getValorUrbano());
    }

    @Test
    public void noPermiteValorMenorA1() {
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Luis", 0, 3, 3, 3)); // tango invalido
    }

    @Test
    public void noPermiteValorMayorA5() {
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Marta", 6, 3, 3, 3)); // tango invalido
        assertThrows(IllegalArgumentException.class,
            () -> new Usuario("Pedro", 3, 3, 3, 10)); // urbano invalido
    }

    @Test
    public void gettersDevuelvenValoresCorrectos() {
        Usuario u = new Usuario("Sofia", 5, 1, 4, 2);

        assertEquals("Sofia", u.getNombre());
        assertEquals(5, u.getValorTango());
        assertEquals(1, u.getValorFolklore());
        assertEquals(4, u.getValorRock());
        assertEquals(2, u.getValorUrbano());
    }
}