package logica;

import java.util.*;

public class Grafo {
    //Como son conjuntos de usuarios y aristas, lo mejor es usar listas
    private List<Usuario> usuarios;
    private List<Arista> aristas;
    
    //Constructor, solo recibe la lista de usuarios, las aristas se generan a partir de los usuarios
    public Grafo(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        this.aristas = new ArrayList<>();
        construirAristas();
    }

    private void construirAristas() {
        for(int i = 0; i < usuarios.size(); i++) {
            for(int j = i+1; j < usuarios.size(); j++) {
                // Calcular similaridad entre usuarios i y j
                Usuario u1 = usuarios.get(i);
                Usuario u2 = usuarios.get(j);
                int similaridad = calcularSimilaridad(u1, u2);
                aristas.add(new Arista(u1, u2, similaridad));
            }
        }
    }

    private int calcularSimilaridad(Usuario a, Usuario b) {
        return Math.abs(a.getValorTango() - b.getValorTango()) +
               Math.abs(a.getValorFolklore() - b.getValorFolklore()) +
               Math.abs(a.getValorRock() - b.getValorRock()) +
               Math.abs(a.getValorUrbano() - b.getValorUrbano());
    }
    
    // Algoritmo de Kruskal para árbol generador mínimo
    public List<Arista> kruskal() {
        // Ordenar las aristas por peso (similaridad) de menor a mayor
        List<Arista> aristasOrdenadas = new ArrayList<>(aristas);
        Collections.sort(aristasOrdenadas);

        // Inicializar Union-Find
        UnionFind uf = new UnionFind(usuarios);
        
        // Lista para almacenar las aristas del árbol generador mínimo
        List<Arista> arbolGeneradorMinimo = new ArrayList<>();

        // Procesar aristas en orden ascendente
        for (Arista arista : aristasOrdenadas) {
            Usuario u1 = arista.getUsuario1();
            Usuario u2 = arista.getUsuario2();

            if (uf.encontrarRaiz(u1) != uf.encontrarRaiz(u2)) {
                arbolGeneradorMinimo.add(arista);
                uf.unir(u1, u2);
            }
        }

        return arbolGeneradorMinimo;
    }
    
    // Método para mostrar el grafo completo
    public void mostrarGrafoConsola() {
        System.out.println("=== GRAFO DE USUARIOS ===");
        System.out.println("Usuarios (" + usuarios.size() + "):");
        
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            System.out.println(i + ": " + u.getNombre() + 
                " [T:" + u.getValorTango() + 
                " F:" + u.getValorFolklore() + 
                " R:" + u.getValorRock() + 
                " U:" + u.getValorUrbano() + "]");
        }
        
        System.out.println("\nAristas (" + aristas.size() + "):");
        for (Arista arista : aristas) {
            System.out.println(arista.getUsuario1().getNombre() + " <--> " + 
                              arista.getUsuario2().getNombre() + 
                              " [Similaridad: " + arista.getPeso() + "]");
        }
    }
    
    // Método para mostrar el árbol generador mínimo
    public void mostrarArbolGeneradorMinimo() {
        List<Arista> arbol = kruskal();
        
        System.out.println("=== ÁRBOL GENERADOR MÍNIMO ===");
        System.out.println("Número de aristas en el árbol: " + arbol.size());
        System.out.println("Aristas del árbol:");
        
        int similaridadTotal = 0;
        for (Arista arista : arbol) {
            System.out.println(arista.getUsuario1().getNombre() + " <--> " + 
                              arista.getUsuario2().getNombre() + 
                              " [Similaridad: " + arista.getPeso() + "]");
            similaridadTotal += arista.getPeso();
        }
        
        System.out.println("Similaridad total del árbol: " + similaridadTotal);
    }

    //Getters
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    public List<Arista> getAristas() {
        return aristas;
    }
}
