package controlador;

import java.util.ArrayList;
import java.util.List;
import logica.Grafo;
import logica.Usuario;
import interfaz.GrafoVisual; // cambiar de package
import logica.ArchivoJSON;
import logica.Arista;
import logica.Estadistica;

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
        return k > 1 && k <= cantidadUsuarios;
    }
    
    public boolean validarCantidadGrupos(String input) {
        try {
            int k = Integer.parseInt(input);
            return k > 1 && k <= usuarios.size();
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

    public List<String> obtenerResumenGrupos(List<List<Usuario>> grupos) {
        List<String> resumenes = new ArrayList<>();

        int g = 1;
        for (List<Usuario> grupo : grupos) {
            Estadistica e = new Estadistica(grupo);
            StringBuilder sb = new StringBuilder();

            sb.append("Grupo ").append(g).append("\n");
            sb.append("Integrantes:\n");
            for (Usuario u : grupo) {
                sb.append(" • ").append(u.getNombre()).append("\n");
            }

            sb.append("Promedio Tango: ").append(e.getPromedioTango()).append("\n");
            sb.append("Promedio Folklore: ").append(e.getPromedioFolklore()).append("\n");
            sb.append("Promedio Rock: ").append(e.getPromedioRock()).append("\n");
            sb.append("Promedio Urbano: ").append(e.getPromedioUrbano()).append("\n");

            resumenes.add(sb.toString());
            g++;
        }
        return resumenes;
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
        
    public List<List<Usuario>> generarGrupos(int k) {
        if (!esNumeroDeGruposValido(k)) {
            throw new IllegalArgumentException(
                "El número de grupos debe ser mayor que 1 y menor o igual a la cantidad de usuarios."
            );
        }
        this.ultimoK = k;
        return calcularGrupos(k);
    }


    
    public int getNumeroGrupos() {
        return this.ultimoK;
    }
    
}
