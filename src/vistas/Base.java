package vistas;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.Palabra;
import modelo.Personaje;
import modelo.RutaPersonaje;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import fonts.Fuentes;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Font;

public class Base extends JFrame {
	//Inicializar los elementos de diseños
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private modelo.imagen imagen;
	private JLabel palabraMostrada;
	private Palabra palabra;
	private JTextField letterField;
	private JLabel comunicacionLabel;
	private JLabel listaLetrasUsadas;
	private JButton jugarNuevoBoton;
	private JLabel personajeLabel;
	private modelo.imagen anteriorPersonaje;
	private modelo.imagen siguientePersonaje;
	
	
	/*
	 * DEFINICION DE VARIABLES
	 */
	List<Palabra> listaPalabras = Palabra.obtener();
	List<String> listaPalabra = new ArrayList<String>();
	List<String> listaGuiones = List.of();
	List<String> listaLetras = new ArrayList<String>();
	List<String> letrasValidas = List.of("A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z");
	Map<List<String>,List<String>> pair = new HashMap<>();
	Integer contadorFallos = 0;
	Fuentes tipoFuente;
	
	//Variables Personajes
	Personaje[] listaPersonaje = Personaje.values();
	Integer personajeIndex = 0;
	String nomPersonaje;
	String ruta;
	
	/**
	 * DISEÑO DEL FRAME.
	 */
	public Base() {
		setBackground(new Color(0, 0, 0));
		
		elegirPersonaje(personajeIndex);//PERSONAJE INICIAL
		
		//DISEÑO FRAME
		setTitle("Ahorcado");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Base.class.getResource("/img/Fondo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		
		//INICIALIZACION DE FUENTES PERSONALIZADAS
		tipoFuente = new Fuentes();
		
		//PANEL DE CONTENIDO
		contentPane = new JPanel();
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contentPane.requestFocus();
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//IMAGEN DEL AHORCADO
		imagen = new modelo.imagen();
		imagen.setRuta(ruta+"TablaAhorcado6.png");
		imagen.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		imagen.setLocation(30, 30);
		imagen.setBackground(new Color(255, 255, 255));
		imagen.setSize(220, 220);
		contentPane.add(imagen);
		imagen.setVisible(true);
		
		//BOTON DE GENERAR PALABRA===========================================================
		JButton palabraBoton = new JButton("Generar Palabra");
		palabraBoton.setHorizontalTextPosition(SwingConstants.CENTER);
		palabraBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				palabraBoton.setBackground(new Color(85, 54, 27));
			}
		});
		palabraBoton.setDefaultCapable(false);
		palabraBoton.setFocusPainted(false);
		palabraBoton.setForeground(new Color(0, 0, 0));
		palabraBoton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		palabraBoton.setBorder(null);
		palabraBoton.setBackground(new Color(85, 54, 27));
		palabraBoton.setAlignmentY(Component.TOP_ALIGNMENT);
		palabraBoton.setFont(tipoFuente.fuente(tipoFuente.PC, 0, 15));
		palabraBoton.addMouseListener(new MouseAdapter() {
			/*
			 * Generar la palabra
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				generarPalabra();
				palabraBoton.setVisible(false);
					
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				palabraBoton.setBorder(new LineBorder(new Color(0, 0, 0), 5));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				palabraBoton.setBorder(null);
			}
		});
		palabraBoton.setBounds(160, 391, 275, 50);
		contentPane.add(palabraBoton);
		
		//BOTON JUGAR DE NUEVO
		jugarNuevoBoton = new JButton("Jugar de Nuevo");
		jugarNuevoBoton.setHorizontalTextPosition(SwingConstants.CENTER);
		jugarNuevoBoton.setForeground(Color.BLACK);
		jugarNuevoBoton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jugarNuevoBoton.setFont(tipoFuente.fuente(tipoFuente.PC, 0, 15));
		jugarNuevoBoton.setFocusPainted(false);
		jugarNuevoBoton.setDefaultCapable(false);
		jugarNuevoBoton.setBorder(null);
		jugarNuevoBoton.setBackground(new Color(85, 54, 27));
		jugarNuevoBoton.setAlignmentY(0.0f);
		jugarNuevoBoton.setBounds(160, 391, 275, 50);
		jugarNuevoBoton.setVisible(false);
		jugarNuevoBoton.addMouseListener(new MouseAdapter() {
			/*
			 * Jugar de Nuevo
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				juegoNuevo();	
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				jugarNuevoBoton.setBorder(new LineBorder(new Color(0, 0, 0), 5));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				jugarNuevoBoton.setBorder(null);
			}
		});
		contentPane.add(jugarNuevoBoton);
		
		//LABEL DE LA PALABRA QUE SE VA A MOSTRAR ===========================================
		palabraMostrada = new JLabel("AHORCADO");
		palabraMostrada.setForeground(new Color(0, 0, 0));
		palabraMostrada.setAlignmentY(Component.TOP_ALIGNMENT);
		palabraMostrada.setBackground(new Color(255, 255, 128));
		palabraMostrada.setHorizontalAlignment(SwingConstants.CENTER);
		palabraMostrada.setFont(tipoFuente.fuente(tipoFuente.PC, 1, 30));
		palabraMostrada.setBounds(-2, 305, 600, 46);
		contentPane.add(palabraMostrada);
		
		//INPUT DE LA LETRA QUE SE RECIBIRA ================================================
		letterField = new JTextField();
		letterField.setCaretColor(new Color(124, 79, 40));
		letterField.setBackground(new Color(124, 79, 40));
		letterField.setBorder(null);
		letterField.setDisabledTextColor(new Color(0, 0, 0));
		letterField.setMargin(new Insets(0, 0, 0, 0));
		letterField.setAlignmentY(Component.TOP_ALIGNMENT);
		letterField.setHorizontalAlignment(SwingConstants.CENTER);
		letterField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				/*
				 * COMPRUEBA LA LETRA (MEDIANTE INTRO)
				 */
				String letraInput = letterField.getText().toUpperCase();
				if(e.getKeyCode()==KeyEvent.VK_ENTER && !palabraBoton.isVisible()) {
					comunicacionLabel.setText(comprobarInput(letraInput));//Comprueba la letra
					letterField.setText(""); //Restablece el input
					listaLetrasUsadas.setText(listaLetras.toString());
					comprobarFallos(contadorFallos);
					
					if(!listaGuiones.contains("_")) {//Comprobar si ha ganado
						palabraAdivinada();
					}
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				String letraInput = letterField.getText().toUpperCase();
				
				char caracter = Character.toUpperCase(e.getKeyChar()); 
		        e.setKeyChar(caracter);
		        if(letraInput.length() >= 1) {
		        	e.consume(); //ignora el evento teclado
		        }
			}
		});
		letterField.setForeground(new Color(0, 0, 0));
		letterField.setFont(tipoFuente.fuente(tipoFuente.PC, 0, 30));
		letterField.setBounds(278, 391, 40, 50);
		contentPane.add(letterField);
		letterField.setColumns(10);
		letterField.setVisible(false);
		
		//TEXTO DE COMUNICACION Y AVISOS
		comunicacionLabel = new JLabel("Presiona el botón, genera una palabra y empiza a jugar");
		comunicacionLabel.setForeground(new Color(0, 0, 0));
		comunicacionLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		comunicacionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		comunicacionLabel.setBounds(20, 360, 556, 30);
		comunicacionLabel.setFont(tipoFuente.fuente(tipoFuente.PLL, 0, 20));
		contentPane.add(comunicacionLabel);
		
		
		//TITULO DE LAS LETRAS INCORRECTAS
		JLabel tituloLetras = new JLabel("LETRAS INCORRECTAS: ");
		tituloLetras.setVerticalAlignment(SwingConstants.TOP);
		tituloLetras.setFont(tipoFuente.fuente(tipoFuente.PLL, 1, 20));
		tituloLetras.setForeground(new Color(0,0,0));
		tituloLetras.setBounds(323, 30, 298, 30);
		contentPane.add(tituloLetras);
		
		//LISTA DE LAS LETRAS USADAS
		listaLetrasUsadas = new JLabel(listaLetras.toString());
		listaLetrasUsadas.setFont(tipoFuente.fuente(tipoFuente.PLL, 0, 20));
		listaLetrasUsadas.setForeground(new Color(0,0,0));
		listaLetrasUsadas.setVerticalAlignment(SwingConstants.TOP);
		listaLetrasUsadas.setBounds(323, 60, 353, 190);
		contentPane.add(listaLetrasUsadas);
		
		//IMAGEN DE CARTEL
		modelo.imagen imagenCartel = new modelo.imagen();
		imagenCartel.setRuta("/img/Cartel.png");
		imagenCartel.setBounds(30, 293, 535, 190);
		contentPane.add(imagenCartel);
		
		//IMAGEN CARTEL LETRAS USADAS
		JPanel imagenCartelLetras = new JPanel();
		imagenCartelLetras.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		imagenCartelLetras.setBounds(310, 15, 266, 235);
		contentPane.add(imagenCartelLetras);
		
		//FLECHA IZQ
		anteriorPersonaje = new modelo.imagen();
		anteriorPersonaje.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		anteriorPersonaje.setRuta("/img/FlechaIzq.png");
		anteriorPersonaje.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				anteriorPersonaje();
			}
		});
		anteriorPersonaje.setBounds(30, 489, 84, 63);
		contentPane.add(anteriorPersonaje);
		
		//FLECHA DER
		siguientePersonaje = new modelo.imagen();
		siguientePersonaje.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		siguientePersonaje.setRuta("/img/FlechaDer.png");
		siguientePersonaje.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				siguientePersonaje();
			}
		});
		siguientePersonaje.setBounds(481, 489, 84, 63);
		contentPane.add(siguientePersonaje);
		
		//LABEL DEL PERSONAJE
		personajeLabel = new JLabel(nomPersonaje);
		personajeLabel.setForeground(new Color(255, 217, 128));
		personajeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		personajeLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
		personajeLabel.setBounds(30, 494, 535, 46);
		contentPane.add(personajeLabel);
		
		//IMAGEN DE FONDO
		modelo.imagen imagenFondo = new modelo.imagen();
		imagenFondo.setRuta("/img/FondoImg.png");
		imagenFondo.setBounds(0, 0, 610, 600);
		contentPane.add(imagenFondo);
		
	}
	
	
	/* =========================================================================================
	 * FUNCIONES
	 ===========================================================================================*/
	
	/*
	 * FUNCION DE CONVERTIR PERSONAJE
	 */
	public void elegirPersonaje(Integer index) {
		nomPersonaje = RutaPersonaje.nombre(listaPersonaje[index]).toUpperCase();
		ruta = RutaPersonaje.ruta(listaPersonaje[index]);
	}
	
	/*
	 *FUNCION SIGUIENTE PERSONAJE
	 */
	public void siguientePersonaje() {
		personajeIndex++;
		if(personajeIndex == listaPersonaje.length) {
			personajeIndex = 0;
			elegirPersonaje(personajeIndex);
		}else {
			elegirPersonaje(personajeIndex);
		}
		personajeLabel.setText(nomPersonaje);
		imagen.setRuta(ruta+"TablaAhorcado6.png");
		imagen.repaint();
	}
	
	/*
	 *FUNCION ANTERIOR PERSONAJE
	 */
	public void anteriorPersonaje() {
		personajeIndex--;
		if(personajeIndex == -1) {
			personajeIndex = listaPersonaje.length-1;
			elegirPersonaje(personajeIndex);
		}else {
			elegirPersonaje(personajeIndex);
		}
		personajeLabel.setText(nomPersonaje);
		imagen.setRuta(ruta+"TablaAhorcado6.png");
		imagen.repaint();
	}
	
	/*
	 * FUNCION GENERAR PALABRAS
	 */
	public void generarPalabra() {
		palabra = Palabra.palabraAleatoria(listaPalabras);
//		System.out.println(palabra);
		listaPalabra.addAll(palabra.letras());
//		System.out.println(listaPalabra);
		listaGuiones = palabra.listaGuiones();
		palabraMostrada.setText(Palabra.concatena(listaGuiones));
		palabraMostrada.setForeground(Color.black);
		letterField.setVisible(true);
		letterField.requestFocus();
		imagen.setRuta(ruta+"TablaAhorcado0.png");
		imagen.repaint();
		personajeLabel.setVisible(false);
		anteriorPersonaje.setVisible(false);
		siguientePersonaje.setVisible(false);
		comunicacionLabel.setText("Añade una letra");
	}
	
	/*
	 * FUNCION DE JUGAR DE NUEVO
	 */
	public void juegoNuevo() {
//		System.out.println(listaPalabras);
		jugarNuevoBoton.setVisible(false);
		letterField.setVisible(true);
		comunicacionLabel.setText("Añade una letra");
		contadorFallos = 0;
		listaPalabra = new ArrayList<String>();
		listaLetras = new ArrayList<String>();
		listaLetrasUsadas.setText(listaLetras.toString());
		generarPalabra();
	}
	
	/*
	 * FUNCION DE COMPROBAR LETRA
	 */
	public String comprobarInput(String letraInput) {//Funcion devuelve lo que se mostrara en el texto de comunicacion y realiza las operaciones de descubri la existencia de la letra
		String respuesta = "";
		
		if(letraInput.length()<1) {
			respuesta = "Añade una letra";
			
		}else if(!letrasValidas.contains(letraInput)) {
			respuesta = letraInput + " no es una letra valida";
			
		}else { //LETRA VALIDA
			if(!listaLetras.contains(letraInput) && !listaGuiones.contains(letraInput)) {
				
				if(listaPalabra.contains(letraInput)) {
					pair = palabra.modificaPalabra(letraInput, listaGuiones, listaPalabra); 
					//^Modifica los guiones de la palabra
					
					listaPalabra = pair.keySet().stream().toList().get(0);
					listaGuiones = pair.get(listaPalabra);
//					System.out.println(listaGuiones +" "+ listaPalabra);
					
					palabraMostrada.setText(Palabra.concatena(listaGuiones));//Muestra las letras reveladas
					respuesta = "Letra Correcta";
					
				}else {
					contadorFallos++;
					respuesta = "Letra equivocada";
					listaLetras.add(letraInput);
					
					//La imagen cambia tras 200ms
					Timer timerImagen = new Timer();
					TimerTask sustituirImagen = new TimerTask() {
						@Override
						public void run() {
							imagen.setRuta(ruta+"TablaAhorcado"+contadorFallos+".png");
							imagen.repaint();
						}
					};
					timerImagen.schedule(sustituirImagen, 200);
				}
				
			}else {
				respuesta = "Letra ya utilizada";
			}
			
		}
		return respuesta;
	}

	/*
	 * FUNCION DE COMPROBAR SI HA PERDIDO
	 */
	public void comprobarFallos(Integer fallos) {
		if(fallos == 6) {
			letterField.setVisible(false);
			jugarNuevoBoton.setVisible(true);
			comunicacionLabel.setText("Has perdido, la palabra era " + palabra.palabra());
			palabraMostrada.setText(palabra.palabra());
			palabraMostrada.setForeground(new Color(178, 52, 52));
			personajeLabel.setVisible(true);
			anteriorPersonaje.setVisible(true);
			siguientePersonaje.setVisible(true);
		}
	}
	
	/*
	 * FUNCION TRAS GANAR
	 */
	public void palabraAdivinada() {
		comunicacionLabel.setText("Has ganado");
		palabraMostrada.setText(palabra.palabra());
		palabraMostrada.setForeground(new Color(26, 89, 38));
		jugarNuevoBoton.setVisible(true);
		letterField.setVisible(false);
		personajeLabel.setVisible(true);
		anteriorPersonaje.setVisible(true);
		siguientePersonaje.setVisible(true);
		
	}
}

	





