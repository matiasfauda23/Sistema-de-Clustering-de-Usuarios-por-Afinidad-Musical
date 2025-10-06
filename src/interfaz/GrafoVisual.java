package interfaz;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.Viewer;

public class GrafoVisual {
    
    private Graph grafo;
    
    public GrafoVisual() {       
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
       
    public void agregarNodo(String id, String etiqueta) {
        Node nodo = grafo.addNode(id);
        nodo.setAttribute("ui.label", etiqueta);
    }
    
    public void agregarArista(String id, String nodoOrigen, String nodoDestino) {
        Edge arista = grafo.addEdge(id, nodoOrigen, nodoDestino);      
    }
    
    public void agregarAristaConPeso(String id, String nodoOrigen, String nodoDestino, double peso) {
        Edge arista = grafo.addEdge(id, nodoOrigen, nodoDestino);
        arista.setAttribute("ui.label", String.valueOf(peso));
    }
    
    public void mostrar() {
        Viewer viewer = grafo.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    }
    
//    // Obtener el componente visual para embeber en un JPanel
//    public javax.swing.JComponent obtenerComponenteVisual() {
//        System.setProperty("org.graphstream.ui", "swing");
//        
//        Viewer viewer = new SwingViewer(grafo, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
//        viewer.enableAutoLayout();
//        
//        // Obtener la vista (el componente Swing)
//        org.graphstream.ui.view.View view = viewer.addDefaultView(false);
//        
//        // Retornar como JComponent para agregar a un JPanel
//        return (javax.swing.JComponent) view;
//    }
        
    public static void main(String[] args) {
        // Necesario para evitar problemas con Swing
        System.setProperty("org.graphstream.ui", "swing");
        
        GrafoVisual miGrafo = new GrafoVisual();
        
        
        miGrafo.agregarNodo("A", "Nodo A");
        miGrafo.agregarNodo("B", "Nodo B");
        miGrafo.agregarNodo("C", "Nodo C");
        miGrafo.agregarNodo("D", "Nodo D");
        
        
        miGrafo.agregarArista("AB", "A", "B");
        miGrafo.agregarArista("BC", "B", "C");
        miGrafo.agregarArista("CD", "C", "D");
        miGrafo.agregarArista("DA", "D", "A");
        miGrafo.agregarAristaConPeso("AC", "A", "C", 5.5);
        
        
        miGrafo.mostrar();
    }
}