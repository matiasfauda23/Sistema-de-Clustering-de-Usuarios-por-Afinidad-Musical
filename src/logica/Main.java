package logica;

import java.util.*;

public class Main {
	
	public static void main(String[] args) {
	    List<Usuario> usuarios = new ArrayList<>();
	    usuarios.add(new Usuario("Ana", 5,2,3,1));
	    usuarios.add(new Usuario("Luis", 4,2,5,1));
	    usuarios.add(new Usuario("Marta", 1,5,2,3));
	    usuarios.add(new Usuario("Pedro", 2,4,1,4));

	    Grafo grafo = new Grafo(usuarios);

	    grafo.mostrarGrafoConsola();
	    grafo.mostrarArbolGeneradorMinimo();

	    System.out.println("\n=== Grupos ===");
	    List<List<Usuario>> grupos = grafo.obtenerGrupos();

	    int i = 1;
	    for (List<Usuario> grupo : grupos) {
	        System.out.println("Grupo " + i + ":");
	        for (Usuario u : grupo) {
	            System.out.println(" - " + u.getNombre());
	        }
	        i++;
	    }
	}

}

