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
           
}