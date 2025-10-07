package logicaTest;



import static org.junit.Assert.*;
import org.junit.Test;

import controlador.ControladorAfinidad;


public class ControladorAfinidadTest {

    @Test
    public void testEsNumeroDeGruposValido() {
        ControladorAfinidad controlador = new ControladorAfinidad();

        // Agrego 3 usuarios
        controlador.agregarUsuario("Mateo", 5, 4, 3, 2);
        controlador.agregarUsuario("Matias", 3, 4, 3, 2);
        controlador.agregarUsuario("Tobias", 4, 3, 2, 1);

        // Casos válidos
        assertTrue(controlador.esNumeroDeGruposValido(1));
        assertTrue(controlador.esNumeroDeGruposValido(2));
        assertTrue(controlador.esNumeroDeGruposValido(3));

        // Casos inválidos
        assertFalse(controlador.esNumeroDeGruposValido(0));
        assertFalse(controlador.esNumeroDeGruposValido(4));
        assertFalse(controlador.esNumeroDeGruposValido(-1));
    }
}
