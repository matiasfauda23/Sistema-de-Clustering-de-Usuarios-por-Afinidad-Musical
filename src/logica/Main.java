package logica;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Crear usuarios de ejemplo
        List<Usuario> usuarios = Arrays.asList(
            new Usuario("Juan", 5, 3, 2, 4),
            new Usuario("Maria", 3, 4, 5, 2),
            new Usuario("Pedro", 2, 5, 3, 4),
            new Usuario("Ana", 4, 2, 4, 3),
            new Usuario("Luis", 3, 3, 4, 2)
        );

        // Crear grafo completo
        Grafo grafo = new Grafo(usuarios);
        
        // Mostrar grafo completo
        System.out.println("=== GRAFO COMPLETO ===");
        grafo.mostrarGrafoConsola();
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // Construir y mostrar árbol generador mínimo
        grafo.mostrarArbolGeneradorMinimo();
    }
}

