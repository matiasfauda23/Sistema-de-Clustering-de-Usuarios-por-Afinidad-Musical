package logica;

import java.util.*;

public class UnionFind {
    // Mapa que guarda el "padre" de cada usuario
    private Map<Usuario, Usuario> padre;

    // Mapa que guarda la "altura aproximada" del árbol (para optimizar la unión)
    private Map<Usuario, Integer> altura;

    /**
     * Constructor: inicializa cada usuario como su propio conjunto
     */
    public UnionFind(List<Usuario> usuarios) {
        padre = new HashMap<>();
        altura = new HashMap<>();

        for (Usuario u : usuarios) {
            padre.put(u, u);   // al inicio, cada usuario es su propio padre
            altura.put(u, 0);  // altura inicial en 0
        }
    }

    /**
     * Método find:
     * Devuelve la raíz del conjunto al que pertenece el usuario.
     * Aplica "compresión de caminos" para que cada usuario
     * apunte directamente a la raíz → más eficiencia.
     */
    public Usuario encontrarRaiz(Usuario u) {
        if (padre.get(u) != u) {
            padre.put(u, encontrarRaiz(padre.get(u))); // compresión de caminos
        }
        return padre.get(u);
    }

    /**
     * Método union:
     * Une los conjuntos de dos usuarios, si son diferentes.
     * Usa "union by rank" → el árbol más bajo se cuelga del más alto.
     */
    public void unir(Usuario u1, Usuario u2) {
        Usuario raiz1 = encontrarRaiz(u1);
        Usuario raiz2 = encontrarRaiz(u2);

        if (raiz1 != raiz2) {
            int altura1 = altura.get(raiz1);
            int altura2 = altura.get(raiz2);

            if (altura1 > altura2) {
                padre.put(raiz2, raiz1); // raiz1 domina
            } else if (altura1 < altura2) {
                padre.put(raiz1, raiz2); // raiz2 domina
            } else {
                padre.put(raiz2, raiz1); // cualquiera puede dominar
                altura.put(raiz1, altura1 + 1); // aumentamos altura de la nueva raíz
            }
        }
    }

    /**
     * Método para verificar si dos usuarios pertenecen al mismo conjunto
     */
    public boolean estanConectados(Usuario u1, Usuario u2) {
        return encontrarRaiz(u1) == encontrarRaiz(u2);
    }
}

