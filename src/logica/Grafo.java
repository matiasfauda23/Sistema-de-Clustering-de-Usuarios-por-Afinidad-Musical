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
   
    // Divide el MST en dos grupos al eliminar la arista de mayor peso
    public List<List<Usuario>> obtenerGrupos(int k) {
    	
        if (k < 1 || k > usuarios.size()) {
            throw new IllegalArgumentException("Número de grupos inválido");
        }
        // Paso 1: construir el MST
        List<Arista> mst = kruskal();

        // 2. Eliminar (k-1) aristas más pesadas
        for (int i = 0; i < k - 1; i++) {
            eliminarAristaMayorPeso(mst);
        }

        // 3. Reconstruir componentes usando Union-Find
        UnionFind uf = new UnionFind(usuarios);
        for (Arista a : mst) {
            uf.unir(a.getUsuario1(), a.getUsuario2());
        }

        // 4. Agrupar usuarios por su raíz
        Map<Usuario, List<Usuario>> grupos = new HashMap<>();
        for (Usuario u : usuarios) {
            Usuario raiz = uf.encontrarRaiz(u);
            if (!grupos.containsKey(raiz)) {
                grupos.put(raiz, new ArrayList<Usuario>());
            }
            grupos.get(raiz).add(u);
        }

        return new ArrayList<>(grupos.values());
    }
    
    
    
  /*  // Comparator de mayor a menor
    public static final Comparator<Arista> POR_PESO_DESC =
        new Comparator<Arista>() {
            @Override
            public int compare(Arista a, Arista b) {
                return Integer.compare(b.getPeso(), a.getPeso());
            }
        };*/
 

    
    // Algoritmo de Kruskal para árbol generador mínimo
    public List<Arista> kruskal() {
        // Ordenar las aristas por peso (similaridad) de menor a mayor
        List<Arista> aristasOrdenadas = new ArrayList<>(aristas);
        Collections.sort(aristasOrdenadas);

        // Inicializar Union-Find
        UnionFind uf = new UnionFind(usuarios);
        
        // Lista para almacenar las aristas del arbol generador mínimo
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
    
    public void eliminarAristaMayorPeso(List<Arista> arbol) {
        if (arbol == null || arbol.isEmpty()) {
            return;
        }
        Arista aristaMayor = Collections.max(arbol); // usa compareTo
        arbol.remove(aristaMayor);
    }

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
    
    public String mostrarInfoGrafo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== GRAFO DE USUARIOS ===\n");
        sb.append("Usuarios (" + usuarios.size() + "):\n");

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            sb.append(i + ": " + u.getNombre() +
                    " [T:" + u.getValorTango() +
                    " F:" + u.getValorFolklore() +
                    " R:" + u.getValorRock() +
                    " U:" + u.getValorUrbano() + "]\n");
        }

        sb.append("\nAristas (" + aristas.size() + "):\n");
        for (Arista arista : aristas) {
            sb.append(arista.getUsuario1().getNombre() + " <--> " +
                      arista.getUsuario2().getNombre() +
                      " [Similaridad: " + arista.getPeso() + "]\n");
        }
        return sb.toString();
    }

    public String mostrarInfoMST() {
        List<Arista> arbol = kruskal();
        StringBuilder sb = new StringBuilder();

        sb.append("=== ÁRBOL GENERADOR MÍNIMO ===\n");
        sb.append("Número de aristas en el árbol: " + arbol.size() + "\n");
        sb.append("Aristas del árbol:\n");

        int similaridadTotal = 0;
        for (Arista arista : arbol) {
            sb.append(arista.getUsuario1().getNombre() + " <--> " +
                      arista.getUsuario2().getNombre() +
                      " [Similaridad: " + arista.getPeso() + "]\n");
            similaridadTotal += arista.getPeso();
        }
        sb.append("Similaridad total del árbol: " + similaridadTotal + "\n");

        return sb.toString();
    }

    public String mostrarInfoGruposConEstadisticas() {
        List<List<Usuario>> grupos = obtenerGrupos(2);
        StringBuilder sb = new StringBuilder();

        int g = 1;
        for (List<Usuario> grupo : grupos) {
            sb.append("\n=== Grupo " + g + " ===\n");
            for (Usuario u : grupo) {
                sb.append(" - " + u.getNombre() + "\n");
            }

            // Promedios de intereses
            double sumT = 0, sumF = 0, sumR = 0, sumU = 0;
            for (Usuario u : grupo) {
                sumT += u.getValorTango();
                sumF += u.getValorFolklore();
                sumR += u.getValorRock();
                sumU += u.getValorUrbano();
            }
            int n = grupo.size();
            sb.append("Promedio Tango: " + (sumT / n) + "\n");
            sb.append("Promedio Folklore: " + (sumF / n) + "\n");
            sb.append("Promedio Rock: " + (sumR / n) + "\n");
            sb.append("Promedio Urbano: " + (sumU / n) + "\n");
            g++;
        }

        return sb.toString();
    }

    //Getters
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
    public List<Arista> getAristas() {
        return aristas;
    }
}
