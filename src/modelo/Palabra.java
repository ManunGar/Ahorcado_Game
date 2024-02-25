package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public record Palabra(String palabra, Integer longitud, List<String> letras) {
	
	public static Palabra of(String palabra) {
		Integer longitud = palabra.length();
		List<String> ls = new ArrayList<String>();
		String[] letras = palabra.split("");
		for(int i = 0; i < letras.length; i++) {
			ls.add(letras[i].toUpperCase());
		}
		return new Palabra(palabra.toUpperCase(), longitud, ls);
	}
	
	// Parseo de las Palabras de txt a lista
	@SuppressWarnings("finally")
	public static ArrayList<Palabra> obtener() {
		final String NOMBRE_ARCHIVO = "src/resources/palabras.txt";
		ArrayList<Palabra> palabras = new ArrayList<>();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(NOMBRE_ARCHIVO);
			bufferedReader = new BufferedReader(fileReader);
			String linea;
			while ((linea = bufferedReader.readLine()) != null) {
				if(linea.length() < 12) {
					palabras.add(Palabra.of(linea));
				}
			}
		} catch (IOException e) {
			System.out.println("Excepción leyendo archivo: " + e.getMessage());
		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				System.out.println("Excepción cerrando: " + e.getMessage());
			}
			return palabras;
		}
	}
	
	//Seleccion de Palabra Aleatoria
	public static Palabra palabraAleatoria(List<Palabra> ls) {
		Random rand = new Random();
		return ls.get(rand.nextInt(ls.size()));
	}
	
	//Crea una lista de guiones con el mismo tamaño que el tamaño de la palabra
	public List<String> listaGuiones() { 
		List<String> ls = new ArrayList<String>();
		for(int i = 0; i < this.longitud;i++) {
			ls.add("_");
		}
		return ls;
	}
	
	//Combierte una lista en cadena
	public static String concatena(List<String> ls) {
		String guiones = " ";
		for(String letra: ls) {
			guiones += letra + " ";
		}
		return guiones;
	}

	/*
	 * MODIFICA LA PALABRA DADA LA LETRA SUSTITUYE LOS GUIONES POR LA/S LETRAS Y VICEBERSA
	 */
	public Map<List<String>, List<String>> modificaPalabra(String letra, List<String> listaGuiones, List<String> listaPalabra) {
		int ind = 0;
		letra = letra.toUpperCase();
		Map<List<String>,List<String>> mp = new HashMap<>();
		while(listaPalabra.contains(letra)) {
			ind = listaPalabra.indexOf(letra);
			
			listaPalabra.remove(ind);
			listaPalabra.add(ind, "_");
			
			listaGuiones.remove(ind);
			listaGuiones.add(ind, letra);
		}
		mp.put(listaPalabra, listaGuiones);
		return mp;
		
	}

	
	public static void main(String[] args) {
		List<Palabra> ls = Palabra.obtener();
		Palabra p = Palabra.palabraAleatoria(ls);
		System.out.println(p);
		List<String> listaPalabra = new ArrayList<String>();
		listaPalabra.addAll(p.letras());
		List<String> listaGuiones = p.listaGuiones();
		System.out.println(listaGuiones);
		System.out.println(p.modificaPalabra("a", listaGuiones, listaPalabra));
		System.out.println(ls);
		
	}
}
