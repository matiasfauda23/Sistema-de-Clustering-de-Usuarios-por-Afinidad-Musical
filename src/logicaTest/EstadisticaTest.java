package logicaTest;



import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import logica.Usuario;
import logica.Estadistica;

public class EstadisticaTest {
	//Caso normal
    @Test
    public void testPromediosCorrectos() {
        List<Usuario> grupo = new ArrayList<>();
        grupo.add(new Usuario("Ana", 5, 4, 3, 2));
        grupo.add(new Usuario("Luis", 3, 4, 3, 2));

        Estadistica estadistica = new Estadistica(grupo);

        assertEquals(4.0, estadistica.getPromedioTango(), 0.01); //EL ULTIMO 0.1 ES EL CASO DELTA (margen de error permitido entre ambos valores)
        assertEquals(4.0, estadistica.getPromedioFolklore(), 0.01);
        assertEquals(3.0, estadistica.getPromedioRock(), 0.01);
        assertEquals(2.0, estadistica.getPromedioUrbano(), 0.01);
    }
    //caso borde
    @Test
    public void testGrupoVacio() {
        List<Usuario> grupo = new ArrayList<>();
        Estadistica estadistica = new Estadistica(grupo);

        assertEquals(0.0, estadistica.getPromedioTango(), 0.01);
        assertEquals(0.0, estadistica.getPromedioFolklore(), 0.01);
        assertEquals(0.0, estadistica.getPromedioRock(), 0.01);
        assertEquals(0.0, estadistica.getPromedioUrbano(), 0.01);
    }
}
