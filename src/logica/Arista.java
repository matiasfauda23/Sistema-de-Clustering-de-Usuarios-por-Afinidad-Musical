package logica;

public class Arista {

	//La idea es construitr la arista con los dos usuarios
	private Usuario usuario1;
	private Usuario usuario2;
	private int peso;
	
	//Constructor
	public Arista(Usuario usuario1, Usuario usuario2, int peso) {
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
	
	//En el algoritmo de kruskal vamos a necesitar comparar aristas por peso
	
	public int compareTo(Arista otra) {
		return Integer.compare(this.peso, otra.getPeso());
	}
}
