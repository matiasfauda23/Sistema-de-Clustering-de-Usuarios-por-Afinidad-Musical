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