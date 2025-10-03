package logica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ArchivoJSON {
	
	private Gson gson;
	
	public ArchivoJSON() {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

 public void guardarUsuario(Usuario usuario, String archivo) {
		try (FileWriter writer = new FileWriter(archivo)) {
			gson.toJson(usuario, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
 // Desearializar un usuario desde un archivo JSON
 public List<Usuario> leerUsuarios(String archivo) {
	    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
	        Usuario[] array = gson.fromJson(br, Usuario[].class);
	        return java.util.Arrays.asList(array);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
 
}

