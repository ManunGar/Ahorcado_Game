package modelo;

public class RutaPersonaje {
	
	public static String personaje(Personaje personaje) {
		if (personaje == Personaje.LUCKYLUKE) return "Lucky Luke,lucky";
		if (personaje == Personaje.LUCKYBLACK) return "Lucky Reverse,black";
		else return "";
	}
	
	public static String ruta(Personaje personaje) {
		return "/img/"+personaje(personaje).split(",")[1]+"/"; 
		
	}
	
	public static String nombre(Personaje personaje ) {
		return personaje(personaje).split(",")[0];
	}
}
