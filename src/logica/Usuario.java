package logica;

public class Usuario {
	
	//Intereses del usuario
	private String nombre;
	private int tango;
	private int folklore;
	private int rock;
	private int urbano;
	
	//Constructor
	public Usuario(String nombre, int tango, int folklore, int rock, int urbano) {
		this.nombre = nombre;
		this.tango = tango;
		this.folklore = folklore;
		this.rock = rock;
		this.urbano = urbano;
	}
	//Getters
	public String getNombre() {
		return nombre;
	}
	public int getTango() {
		return tango;
	}
	public int getFolklore() {
		return folklore;
	}
	public int getRock() {
		return rock;
	}
	public int getUrbano() {
		return urbano;
	}
	
}
