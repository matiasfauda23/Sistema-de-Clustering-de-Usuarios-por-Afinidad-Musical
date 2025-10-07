package logicaTest;



import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import controlador.ControladorAfinidad;
import logica.Usuario;


public class ControladorAfinidadTest {


    @Test
    public void testAgregarUsuario() {
        ControladorAfinidad controlador = new ControladorAfinidad();
        controlador.agregarUsuario("Juan", 5, 3, 4, 2);

        List<Usuario> usuarios = controlador.getUsuarios();
        assertEquals(1, usuarios.size());
        assertEquals("Juan", usuarios.get(0).getNombre());
        assertEquals(5, usuarios.get(0).getValorTango());
    }

    @Test
    public void testAgregarUsuarioDesdeJSON() {
        ControladorAfinidad controlador = new ControladorAfinidad();
        Usuario u = new Usuario("Ana", 4, 4, 3, 5);
        controlador.agregarUsuarioDesdeJSON(u);

        List<Usuario> usuarios = controlador.getUsuarios();
        assertEquals(1, usuarios.size());
        assertEquals("Ana", usuarios.get(0).getNombre());
    }

    @Test
    public void testEsNumeroDeGruposValido() {
        ControladorAfinidad controlador = new ControladorAfinidad();

        // Agrego 3 usuarios
        controlador.agregarUsuario("Mateo", 5, 4, 3, 2);
        controlador.agregarUsuario("Matias", 3, 4, 3, 2);
        controlador.agregarUsuario("Tobias", 4, 3, 2, 1);

        // Casos válidos
        assertTrue(controlador.esNumeroDeGruposValido(2));
        assertTrue(controlador.esNumeroDeGruposValido(3));

        // Casos inválidos
        assertFalse(controlador.esNumeroDeGruposValido(0));
        assertFalse(controlador.esNumeroDeGruposValido(1)); // no se permite un solo grupo
        assertFalse(controlador.esNumeroDeGruposValido(4));
        assertFalse(controlador.esNumeroDeGruposValido(-1));
    }

    @Test
    public void testValidarCantidadGrupos() {
        ControladorAfinidad controlador = new ControladorAfinidad();
        controlador.agregarUsuario("Juan", 1, 1, 1, 1);
        controlador.agregarUsuario("Ana", 2, 2, 2, 2);

        assertFalse(controlador.validarCantidadGrupos("0"));
        assertFalse(controlador.validarCantidadGrupos("1"));
        assertTrue(controlador.validarCantidadGrupos("2"));
        assertFalse(controlador.validarCantidadGrupos("3"));
        assertFalse(controlador.validarCantidadGrupos("abc"));
    }

    @Test
    public void testConvertirCantidadGrupos() {
        ControladorAfinidad controlador = new ControladorAfinidad();
        int resultado = controlador.convertirCantidadGrupos("2");
        assertEquals(2, resultado);
    }

    @Test
    public void testGenerarGruposValido() {
        ControladorAfinidad controlador = new ControladorAfinidad();
        controlador.agregarUsuario("Juan", 1, 2, 3, 4);
        controlador.agregarUsuario("Ana", 2, 3, 4, 5);

        List<List<Usuario>> grupos = controlador.generarGrupos(2);
        assertEquals(2, grupos.size());
    }

    @Test
    public void testGenerarGruposInvalido() {
        ControladorAfinidad controlador = new ControladorAfinidad();
        controlador.agregarUsuario("Juan", 1, 2, 3, 4);
        controlador.agregarUsuario("Ana", 2, 3, 4, 5);

        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            controlador.generarGrupos(1); // no se permite un solo grupo
        });
        assertEquals("El número de grupos debe ser mayor que 1 y menor o igual a la cantidad de usuarios.", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> {
            controlador.generarGrupos(3); // más que usuarios
        });
        assertEquals("El número de grupos debe ser mayor que 1 y menor o igual a la cantidad de usuarios.", e.getMessage());
    }

    @Test
    public void testObtenerResumenGrupos() {
        ControladorAfinidad controlador = new ControladorAfinidad();
        controlador.agregarUsuario("Juan", 3, 2, 5, 1);
        controlador.agregarUsuario("Ana", 4, 3, 4, 2);

        List<List<Usuario>> grupos = controlador.generarGrupos(2);
        List<String> resumenes = controlador.obtenerResumenGrupos(grupos);

        assertEquals(2, resumenes.size());
        assertTrue(resumenes.get(0).contains("Grupo 1"));
        assertTrue(resumenes.get(0).contains("Juan") || resumenes.get(0).contains("Ana"));
    }

    @Test
    public void testGetNumeroGrupos() {
        ControladorAfinidad controlador = new ControladorAfinidad();
        controlador.agregarUsuario("Juan", 1, 1, 1, 1);
        controlador.agregarUsuario("Ana", 2, 2, 2, 2);

        controlador.generarGrupos(2);
        assertEquals(2, controlador.getNumeroGrupos());
    }

    @Test
    public void testObtenerGrafoVisualDividido() {
        ControladorAfinidad controlador = new ControladorAfinidad();
        controlador.agregarUsuario("Juan", 1, 1, 1, 1);
        controlador.agregarUsuario("Ana", 2, 2, 2, 2);

        // Validación: no debe lanzar excepción
        try {
            controlador.obtenerGrafoVisualDividido(2);
        } catch (IllegalArgumentException ex) {
            fail("No debería lanzar excepción con k válido");
        }

        // Con k inválido
        try {
            controlador.obtenerGrafoVisualDividido(3);
            fail("Debería lanzar IllegalArgumentException con k inválido");
        } catch (IllegalArgumentException ex) {
            assertEquals("Número de grupos inválido", ex.getMessage());
        }
    }

}
