package logica;

public class Arista implements Comparable<Arista> {

    //La idea es construir la arista con los dos usuarios
    private Usuario usuario1;
    private Usuario usuario2;
    private int peso;
    
    //Constructor
    public Arista(Usuario usuario1, Usuario usuario2, int peso) {
        if (usuario1 == null || usuario2 == null) {
            throw new IllegalArgumentException("Los usuarios no pueden ser nulos");
        }
        if (usuario1.equals(usuario2)) {
            throw new IllegalArgumentException(
                "No se puede crear una arista entre el mismo usuario (loop)");
        }       
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.peso = peso;
    }
    
    //Getters
    public Usuario getUsuario1() {
        return usuario1;
    }
    public Usuario getUsuario2() {
        return usuario2;
    }
    public int getPeso() {
        return peso;
    }
    
    //Implementación de Comparable para Kruskal
    @Override
    public int compareTo(Arista otra) {
        return Integer.compare(this.peso, otra.getPeso());
    }
    
    @Override
    public String toString() {
        return usuario1.getNombre() + " - " + usuario2.getNombre() + " (" + peso + ")";
    }
}