package logica;

import java.util.*;

public class UnionFind {
    private Map<Usuario, Usuario> padre;

    private Map<Usuario, Integer> altura;

    public UnionFind(List<Usuario> usuarios) {
        padre = new HashMap<>();
        altura = new HashMap<>();

        for (Usuario u : usuarios) {
            padre.put(u, u);   // al inicio, cada usuario es su propio padre
            altura.put(u, 0);  // altura inicial en 0
        }
    }

    public Usuario encontrarRaiz(Usuario u) {
        if (padre.get(u) != u) {
            padre.put(u, encontrarRaiz(padre.get(u)));
        }
        return padre.get(u);
    }


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

    public boolean estanConectados(Usuario u1, Usuario u2) {
        return encontrarRaiz(u1) == encontrarRaiz(u2);
    }
}

