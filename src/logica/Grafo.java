package logica;

import java.util.ArrayList;
import java.util.List;

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
	    return Math.abs(a.getTango() - b.getTango()) +
	           Math.abs(a.getFolklore() - b.getFolklore()) +
	           Math.abs(a.getRock() - b.getRock()) +
	           Math.abs(a.getUrbano() - b.getUrbano());
	}

	//Getters
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public List<Arista> getAristas() {
		return aristas;
	}
	
}
