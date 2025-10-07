package controlador;

import java.util.ArrayList;
import java.util.List;
import logica.Grafo;
import logica.Usuario;
import interfaz.GrafoVisual; // cambiar de package
import logica.ArchivoJSON;
import logica.Arista;

public class ControladorAfinidad {
    private List<Usuario> usuarios;
    private int ultimoK = 2; // Valor predeterminado de k

    public ControladorAfinidad() {
        this.usuarios = new ArrayList<>();
    }

    public void agregarUsuario(String nombre, int t, int f, int r, int u) {
        Usuario nuevo = new Usuario(nombre, t, f, r, u);
        usuarios.add(nuevo);
    }

    public void agregarUsuarioDesdeJSON(Usuario usuario){
        usuarios.add(usuario);
    }
    
    public List<List<Usuario>> calcularGrupos(int k) {
        Grafo grafo = new Grafo(usuarios);
        return grafo.obtenerGrupos(k);
    }
    
    public boolean esNumeroDeGruposValido(int k) {
        int cantidadUsuarios = usuarios.size();
        return k >= 1 && k <= cantidadUsuarios;
    }
    
    public boolean validarCantidadGrupos(String input) {
        try {
            int k = Integer.parseInt(input);
            return k >= 1 && k <= usuarios.size();
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public int convertirCantidadGrupos(String input) { //pasa de string a int
        return Integer.parseInt(input);
    }
    
    public List<Usuario> cargarUsuariosDesdeArchivo(String ruta) {
        ArchivoJSON manejador = new ArchivoJSON();
        return manejador.leerUsuarios(ruta);
    }



    
    public GrafoVisual obtenerGrafoVisualDividido(int k) {
    	
    	this.ultimoK = k; // Guardar el valor de k
    	
        // Validación
        if (usuarios.size() < 2) {
            throw new IllegalArgumentException("Debe haber al menos 2 usuarios");
        }
        
        if (k < 1 || k > usuarios.size()) {
            throw new IllegalArgumentException("Número de grupos inválido");
        }
        
        // Configurar GraphStream (solo una vez)
        System.setProperty("org.graphstream.ui", "swing");
        
        // Crear el grafo de lógica internamente
        Grafo grafo = new Grafo(this.usuarios);
        
        GrafoVisual grafoVisual = new GrafoVisual();
        
        for (Usuario usuario : this.usuarios) {
            grafoVisual.agregarNodo(usuario.getNombre(), usuario.getNombre());
        }
        
       
        List<Arista> agm = grafo.kruskal();
        
        
        for (int i = 0; i < k - 1; i++) {
            grafo.eliminarAristaMayorPeso(agm);
        }
        
        // Agregar aristas al grafo visual
        int contador = 0;
        for (Arista arista : agm) {
            String nodo1 = arista.getUsuario1().getNombre();
            String nodo2 = arista.getUsuario2().getNombre();
            double peso = arista.getPeso();
            
            grafoVisual.agregarAristaConPeso("arista" + contador, nodo1, nodo2, peso);
            contador++;
        }
        
        return grafoVisual;
    }
    
    public List<Usuario> getUsuarios() {
        return usuarios;
    }
        
    public void setNumeroGrupos(int k) {
        if (k < 1 || k > usuarios.size()) {
            throw new IllegalArgumentException("Número de grupos inválido");
        }
        this.ultimoK = k;
    }
    
    public int getNumeroGrupos() {
        return this.ultimoK;
    }
    
}
