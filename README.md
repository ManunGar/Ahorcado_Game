# Ahorcado Game

<p align="center">
    <image width="500px" src="/src/img/Cartel.png">
</p>

El clasico juego del ahorcado en español con un estilo pixelado ambientado en el viejo oeste perfecto para entretenerse o para aprender vocabulario. Dispone de más de 1000 palabras pertenecientes al diccionario español.

## Acerca del Proyecto

Desarrollado en **Java** con **jdk 18**
- [Documentation](#documentation) 

## Documentation

### Clase Main
Para ejecutar el juego se utiliza la clase **main.java** que muestra la interfaz del juego.
```java
Base frame = new Base();
frame.setVisible(true);
```

### Modelo Imagen
Las imagenes se generan como un JPanel en la base del Juego pintado por encima con la imagen por la cual se pasa por parametro la ruta de la misma.
```java
@Override
protected void paintComponent(Graphics gg) {
    Graphics2D g = (Graphics2D)gg;
    URL rutaAbsoluta =  getClass().getResource(ruta);
    
    if(rutaAbsoluta != null) {
        ImageIcon imagen = new ImageIcon(rutaAbsoluta);
        g.drawImage(imagen.getImage(),0,0,this.getWidth(),this.getHeight(), null);
    }
    
    g.dispose();
}
```

### Modelo Palabra

Las palabras generadas en el juego vendrán dadas por la clase **Palabra** la cual tendrá como propiedades la cadena de texto de la palabra, el tamaño de esta y una lista con las letras separadas. Para obtener un objeto de esta clase se utiliza un parseo dando una cadena de texto que sería la palabra.
```java
public static Palabra of(String palabra) {
    Integer longitud = palabra.length();
    List<String> ls = new ArrayList<String>();
    String[] letras = palabra.split("");
    for(int i = 0; i < letras.length; i++) {
        ls.add(letras[i].toUpperCase());
    }
    return new Palabra(palabra.toUpperCase(), longitud, ls);
}
```

Para obtener las palabras usadas en el juego se utiliza un documento txt que contiene todas las palabras que se van a utilizar. Este fichero se lee con la siguiente funcion y retornará la lista con las palabras.
```java
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
```

Además de esto tiene funciones como el **coger una palabra aleatoria** de la lista, **crear la lista de los guiones**, **convertir una lista en cadena** util para comprobar las letras faltantes o **cambiar los guiones por las letras**.

(Documentación en desarrollo)

