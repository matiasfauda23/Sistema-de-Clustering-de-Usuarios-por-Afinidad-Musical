package interfaz;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.Viewer;

public class GrafoVisual {
    
    private Graph grafo;
    
    public GrafoVisual() {
        // Crear el grafo
        grafo = new SingleGraph("Mi Grafo");
        
        // Configurar el estilo visual
        grafo.setAttribute("ui.stylesheet", 
            "node { " +
            "   fill-color: #4CAF50; " +
            "   size: 30px; " +
            "   text-size: 16px; " +
            "   text-alignment: center; " +
            "} " +
            "edge { " +
            "   fill-color: #333; " +
            "   arrow-size: 10px, 5px; " +
            "}"
        );
        
        // Habilitar auto-layout
        grafo.setAttribute("ui.quality");
        grafo.setAttribute("ui.antialias");
    }
    
    // Agregar un nodo
    public void agregarNodo(String id, String etiqueta) {
        Node nodo = grafo.addNode(id);
        nodo.setAttribute("ui.label", etiqueta);
    }
    
    // Agregar una arista (conexión)
    public void agregarArista(String id, String nodoOrigen, String nodoDestino) {
        Edge arista = grafo.addEdge(id, nodoOrigen, nodoDestino);
        // Para grafos dirigidos, descomentar la siguiente línea:
        // arista.setAttribute("ui.class", "directed");
    }
    
    // Agregar arista con peso/etiqueta
    public void agregarAristaConPeso(String id, String nodoOrigen, String nodoDestino, double peso) {
        Edge arista = grafo.addEdge(id, nodoOrigen, nodoDestino);
        arista.setAttribute("ui.label", String.valueOf(peso));
    }
    
    // Mostrar el grafo
    public void mostrar() {
        Viewer viewer = grafo.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    }
    
    // Ejemplo de uso
    public static void main(String[] args) {
        // Necesario para evitar problemas con Swing
        System.setProperty("org.graphstream.ui", "swing");
        
        GrafoVisual miGrafo = new GrafoVisual();
        
        // Crear nodos
        miGrafo.agregarNodo("A", "Mati");
        miGrafo.agregarNodo("B", "Tobi");
        miGrafo.agregarNodo("C", "Mateo");
        miGrafo.agregarNodo("D", "Alan");
        
        // Crear conexiones
        miGrafo.agregarArista("AB", "A", "B");
        miGrafo.agregarArista("BC", "B", "C");
        miGrafo.agregarArista("CD", "C", "D");
        miGrafo.agregarArista("DA", "D", "A");
        miGrafo.agregarAristaConPeso("AC", "A", "C", 5.5);
        
        // Mostrar el grafo
        miGrafo.mostrar();
    }
}

