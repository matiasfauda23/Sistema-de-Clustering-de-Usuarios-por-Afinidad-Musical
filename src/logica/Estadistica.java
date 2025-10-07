package logica;

import java.util.List;




public class Estadistica {

    private double promedioTango;
    private double promedioFolklore;
    private double promedioRock;
    private double promedioUrbano;

    public Estadistica(List<Usuario> grupo) {
        calcularPromedios(grupo);
    }

    private void calcularPromedios(List<Usuario> grupo) {
        double sumT = 0, sumF = 0, sumR = 0, sumU = 0;
        int n = grupo.size();

        if (n == 0) {
            promedioTango = 0;
            promedioFolklore = 0;
            promedioRock = 0;
            promedioUrbano = 0;
            return;
        }

        for (Usuario u : grupo) {
            sumT += u.getValorTango();
            sumF += u.getValorFolklore();
            sumR += u.getValorRock();
            sumU += u.getValorUrbano();
        }

        promedioTango = sumT / n;
        promedioFolklore = sumF / n;
        promedioRock = sumR / n;
        promedioUrbano = sumU / n;
    }

    public double getPromedioTango()     {
    	return promedioTango; }
    public double getPromedioFolklore()  { 
    	return promedioFolklore; }
    public double getPromedioRock()      { 
    	return promedioRock; }
    public double getPromedioUrbano()    { 
    	return promedioUrbano; }
}
