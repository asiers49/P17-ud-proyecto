package crawler;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
//Imports relacionados con el proceso
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

//Imports de librería externa  -  https://sourceforge.net/projects/htmlparser/
import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.nodes.TextNode;

import crawler.VentanaColorConsola;

/**
 * Clase de scrapping de una página web para procesar su contenido Programación
 * II y III - IEIAII
 * 
 * Utiliza la librería externa htmlparser 1.6 Descargada en feb de 2016 de
 * https://sourceforge.net/projects/htmlparser/files/htmlparser/1.6/htmlparser1_6_20060610.zip
 * Web del proyecto: https://sourceforge.net/projects/htmlparser/ Descomprimir,
 * guardar y enlazar fichero: htmlparser.jar
 * 
 * Programada para procesar estadísticas de jugadores desde la web comuniazo.com
 * 
 */
public class Crawler {

	private static boolean MOSTRAR_TODOS_LOS_TAGS = false;
	public ProcesadoLaLiga p;
	private static ArrayList<String> Equipos = new ArrayList<>();

	public static void main(String[] args) {

		// Analisis de la web de comuniazo. Poner aquí la URL con la que trabajar:

		// String urlAAnalizar2 = "http://www.comuniazo.com/comunio/jugadores";
		// revisaWeb( urlAAnalizar2 );

		ProcesadoLaLiga pLaLiga = new ProcesadoLaLiga();
		String urlLaLiga = "http://www.comuniazo.com/comunio/jugadores";
		procesaWeb(urlLaLiga, pLaLiga);

	}

	public Crawler() {
		p = new ProcesadoLaLiga();
	}

	// procesado de comuniazo
	public static class ProcesadoLaLiga implements ProcesadoWeb {
		private String tagBuscado = "TD";
		private boolean lista = false;
		private boolean jugador = false;
		private boolean posicion = false;
		private boolean equipo = false;
		private boolean nombre = false;
		private boolean puntos = false;
		private String nombre1;
		private String posicion1;
		private String equipo1;
		private int puntos1;
		private long valor1;
		private boolean listaequipos;

		@Override
		public void procesaTexto(TextNode texto, LinkedList<Tag> pilaTags) {
			if (pilaContieneTags(pilaTags, tagBuscado)) {
				if (jugador) {
					if (equipo) {
						// aFichero(texto.getText());
						nombre1 = texto.getText();
						System.out.println(" Nombre:" + texto.getText());
						nombre = true;
						equipo = false;
					} else if (nombre) {
						System.out.println(" Puntos:" + texto.getText());
						nombre = false;
					} else if (puntos) {
						System.out.println(" Valor:" + texto.getText());
						System.out.println("Fin Jugador ");
						System.out.println("");
						jugador = false;
						puntos = false;
						crearJugador(nombre1, equipo1, posicion1, puntos1, valor1);
					}
				}
			}
			if (listaequipos) {
				System.out.println(texto.getText());
				actualizarListaEquipos(texto.getText());
			}

		}

		@Override
		public void procesaTag(Tag tag, LinkedList<Tag> pilaTags) {
			// TODO programación del método (si procede)
			if (tag.getTagName().equals("TR") && tag.getText().contains("tr class=\"head\"")) { // Empieza la lista
				lista = true;
				// aFichero("Lista");
				System.out.println("Lista: ");

			} else if (lista && tag.getTagName().equals("TR")) { // Empieza el jugado
				jugador = true;
				// aFichero("Jugador: ");
				System.out.println("Jugador: ");
			} else if (jugador && tag.getTagName().equals("SPAN") && tag.getText().contains("pos")) {
				posicion1 = tag.getText();
				posicion = true;
				System.out.println("Posicion:  " + tag.getText());
			} else if (posicion && tag.getTagName().equals("A") && tag.getText().contains("equipos")) {
				equipo = true;
				aFichero(tag.getText());
				equipo1 = tag.getText();
			} else if (jugador && tag.getTagName().equals("TD") && tag.getText().equals("td class=\"aright font-m\"")) {
				puntos = true;
			}

			if (tag.getText().equals("ul class=\"sub\"")) {
				listaequipos = true;
			} else if (tag.getText().equals("i class=\"icon-menu\"")) {
				listaequipos = false;
			}

		}

		@Override
		public void procesaTagCierre(Tag tag, LinkedList<Tag> pilaTags, boolean enHtml) {
		}
	}

	//
	// Métodos de utilidad generales
	//

	private static LinkedList<Tag> pilaTags;

	/**
	 * Procesa una web y muestra en una ventana de consola coloreada sus contenidos
	 * etiquetados
	 * 
	 * @param dirWeb
	 */
	public static void revisaWeb(String dirWeb) {
		URL url;
		pilaTags = new LinkedList<>();
		try {
			url = new URL(dirWeb);
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/4.0"); // Hace pensar a la web que somos un navegador
			Lexer mLexer = new Lexer(new Page(connection));
			Node n = mLexer.nextNode();
			while (n != null) {
				if (n instanceof Tag) {
					Tag t = (Tag) n;
					if (t.isEndTag()) {
						if (pilaTags.get(0).getTagName().equals(t.getTagName())) { // Tag de cierre
							pilaTags.pop();
							if (MOSTRAR_TODOS_LOS_TAGS) {
								VentanaColorConsola.println(String.format("%" + (pilaTags.size() * 2 + 1) + "s", "")
										+ "</" + t.getTagName() + "> -> " + quitaCR(t.getText()), Color.ORANGE);
							}
						} else { // El tag que se cierra no es el último que se abrió: error html pero se procesa
							boolean estaEnPila = false;
							for (Tag tag : pilaTags)
								if (tag.getTagName().equals(t.getTagName()))
									estaEnPila = true;
							if (estaEnPila) { // Ese tag está en la pila: quitar todos los niveles hasta él
								while (!pilaTags.get(0).getTagName().equals(t.getTagName()))
									pilaTags.pop();
								pilaTags.pop();
								if (MOSTRAR_TODOS_LOS_TAGS) {
									VentanaColorConsola.println(String.format("%" + (pilaTags.size() * 2 + 1) + "s", "")
											+ "**PÉRDIDA DE TAGS ANIDADOS", Color.RED);
									VentanaColorConsola.println(String.format("%" + (pilaTags.size() * 2 + 1) + "s", "")
											+ "</" + t.getTagName() + "> -> " + quitaCR(t.getText()), Color.ORANGE);
								}
							} else { // El tag que se cierra no está en la pila
								VentanaColorConsola.println("**ERROR EN CIERRE DE TAG", Color.RED);
								VentanaColorConsola.println(String.format("%" + (pilaTags.size() * 2 + 1) + "s", "")
										+ "</" + t.getTagName() + "> -> " + quitaCR(t.getText()), Color.ORANGE);
							}
						}
					} else if (t.getText().endsWith("/")) { // Tag de cierre y apertura
						VentanaColorConsola.println(String.format("%" + (pilaTags.size() * 2 + 1) + "s", "") + "<"
								+ t.getTagName() + "/> -> " + quitaCR(t.getText()), Color.GREEN);
					} else {
						VentanaColorConsola.println(String.format("%" + (pilaTags.size() * 2 + 1) + "s", "") + "<"
								+ t.getTagName() + "> -> " + quitaCR(t.getText()), Color.BLUE);
						pilaTags.push(t);
					}
				} else {
					if (!quitaCR(n.getText()).trim().isEmpty()) {
						VentanaColorConsola.println(
								String.format("%" + (pilaTags.size() * 2 + 1) + "s", "") + quitaCR(n.getText()),
								Color.BLACK);
					}
				}
				n = mLexer.nextNode();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pilaTags.clear();
	}

	private static String quitaCR(String s) {
		return s.replaceAll("\n", " ");
	}

	/**
	 * Procesa una web y lanza el método observador con cada uno de sus elementos
	 * 
	 * @param dirWeb
	 *            Web que se procesa
	 * @param proc
	 *            Objeto observador que es llamado con cada elemento de la web
	 */
	public static void procesaWeb(String dirWeb, ProcesadoWeb proc) {
		URL url;
		pilaTags = new LinkedList<>();
		try {
			url = new URL(dirWeb);
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/4.0"); // Hace pensar a la web que somos un navegador
			Lexer mLexer = new Lexer(new Page(connection));
			Node n = mLexer.nextNode();
			while (n != null) {
				if (n instanceof Tag) {
					Tag t = (Tag) n;
					if (t.isEndTag()) {
						if (pilaTags.get(0).getTagName().equals(t.getTagName())) { // Tag de cierre
							pilaTags.pop();
							proc.procesaTagCierre(t, pilaTags, true);
						} else { // El tag que se cierra no es el último que se abrió: error html pero se procesa
							boolean estaEnPila = false;
							for (Tag tag : pilaTags)
								if (tag.getTagName().equals(t.getTagName()))
									estaEnPila = true;
							if (estaEnPila) { // Ese tag está en la pila: quitar todos los niveles hasta él
								while (!pilaTags.get(0).getTagName().equals(t.getTagName())) {
									Tag tag = pilaTags.pop();
									proc.procesaTagCierre(tag, pilaTags, false);
								}
								pilaTags.pop();
								proc.procesaTagCierre(t, pilaTags, true);
							} else { // El tag que se cierra no está en la pila
							}
						}
					} else if (t.getText().endsWith("/")) { // Tag de apertura y cierre
						proc.procesaTag(t, pilaTags);
						proc.procesaTagCierre(t, pilaTags, true);
					} else { // Tag de inicio
						proc.procesaTag(t, pilaTags);
						pilaTags.push(t);
					}
				} else {
					if (n instanceof TextNode) {
						proc.procesaTexto((TextNode) n, pilaTags);
					} else {
						// Otros nodos como org.htmlparser.nodes.RemarkNode no se procesan
						// System.out.println( n.getClass().getName() );
						// System.out.println( n.getText() );
					}
				}
				n = mLexer.nextNode();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		pilaTags.clear();
	}

	/**
	 * Interfaz de observador de procesado de web
	 * 
	 * @author andoni.eguiluz @ ingenieria.deusto.es
	 */
	public static interface ProcesadoWeb {
		/**
		 * Método llamado cuando se procesa un tag de apertura html
		 * 
		 * @param tag
		 *            Tag de apertura con toda la información incluida
		 * @param pilaTags
		 *            Pila actual de tags (previa a ese tag)
		 */
		void procesaTag(Tag tag, LinkedList<Tag> pilaTags);

		/**
		 * Método llamado cuando se procesa un tag de cierre
		 * 
		 * @param tag
		 *            Tag de cierre
		 * @param pilaTags
		 *            Pila actual de tags (posterior a cerrar ese tag)
		 * @param enHtml
		 *            true si el tag de cierre es explícito HTML, false si es implícito
		 *            en el fichero pero no está indicado
		 */
		void procesaTagCierre(Tag tag, LinkedList<Tag> pilaTags, boolean enHtml);

		/**
		 * Método llamado cuando se procesa un texto html
		 * 
		 * @param texto
		 *            Texto html
		 * @param pilaTags
		 *            Pila actual de tags donde aparece ese texto
		 */
		void procesaTexto(TextNode texto, LinkedList<Tag> pilaTags);
	}

	/**
	 * Chequea si la pila de tags contiene los tags indicados en el mismo orden
	 * 
	 * @param pilaTags
	 *            Pila de tags anidados (el primero es el más reciente)
	 * @param tags
	 *            Array de tags (solo nombres) buscados en la pila (el primero es el
	 *            más interior que se busca)
	 * @return true si en la pila están los tags indicados en el mismo orden de
	 *         anidamiento, false en caso contrario
	 */
	public static boolean pilaContieneTags(LinkedList<Tag> pilaTags, String... tags) {
		LinkedList<String> pilaBuscada = new LinkedList<String>(Arrays.asList(tags));
		if (pilaBuscada.size() == 0)
			return true;
		for (Tag tag : pilaTags) {
			if (tag.getTagName().equals(pilaBuscada.get(0))) {
				pilaBuscada.pop();
				if (pilaBuscada.size() == 0)
					return true;
			}
		}
		return false;
	}

	/**
	 * Convierte a string visualizable la pila de tags sacando en una línea solo los
	 * tags separados por barras
	 * 
	 * @param pilaTags
	 *            Pila de tags anidados (el primero es el más reciente)
	 * @return String de tags de la pila en el mismo orden
	 */
	public static String tagsDePila(LinkedList<Tag> pilaTags) {
		String ret = "";
		for (Tag tag : pilaTags)
			ret += (tag.getTagName() + "|");
		return ret;
	}

	public static void actualizarListaEquipos(String string) {
		Equipos.add(string);
	}

	public static void crearJugador(String nombre1, String equipo1, String posicion1, int puntos1, long valor1) {

	}

	public static void aFichero(String a) {
		File file = new File("jugadores.txt");
		try {
			FileWriter fw = new FileWriter(file, true);
			for (int i = 0; i < Equipos.size(); i++) {
				String equipocomparar = Equipos.get(i).toLowerCase();
				if (a.contains(equipocomparar) || a.contains("leganes") || a.contains("real")
						|| a.contains("las-palmas") || a.contains("at") || a.contains("alaves")
						|| a.contains("malaga")) {
					a = Equipos.get(i);
					fw.write(" Equipo: "+a);
					fw.write("\n");
				}
			}
			
		
			
			
			
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<String> getEquipos() {
		return Equipos;
	}

	public void setEquipos(ArrayList<String> equipos) {
		Equipos = equipos;
	}
}
