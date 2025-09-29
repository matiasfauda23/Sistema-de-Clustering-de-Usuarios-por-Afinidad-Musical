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
    	if (tango < 1 || tango > 5 || folklore < 1 || folklore > 5 || 
                rock < 1 || rock > 5 || urbano < 1 || urbano > 5) {
                throw new IllegalArgumentException(
                    "Todos los géneros musicales deben estar entre 1 y 5");
            }
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
    public int getValorTango() {
        return tango;
    }
    public int getValorFolklore() {
        return folklore;
    }
    public int getValorRock() {
        return rock;
    }
    public int getValorUrbano() {
        return urbano;
    }
    
}