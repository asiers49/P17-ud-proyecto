package crawler;

import java.awt.Color;
//Imports relacionados con el proceso
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

//Imports de librería externa  -  https://sourceforge.net/projects/htmlparser/
import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.nodes.TextNode;

import crawler.VentanaColorConsola;
import datos.Jugador;
import datos.BD;

public class Crawler {
	private static boolean MOSTRAR_TODOS_LOS_TAGS = false;
	public ProcesadoLaLiga p;
	private static ArrayList<String> Equipos = new ArrayList<>();
	private static int n;
	private static boolean actualizar;
	private static LinkedList<Tag> pilaTags;

	public static boolean isActualizar() {
		return actualizar;
	}

	public static void setActualizar(boolean actualizar) {
		Crawler.actualizar = actualizar;
	}

	public static void main(String[] args) {

		// Analisis de la web de comuniazo. Poner aquí la URL con la que trabajar:

		// String urlAAnalizar2 = "http://www.comuniazo.com/comunio/jugadores";
		// revisaWeb( urlAAnalizar2 );
		try {
			BD.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		n = 1;
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
		private int puntosj;
		private int valor1;
		private boolean listaequipos;
		private Jugador j;
		private int i;
		
		/**
		 * 
		 */
		@Override
		public void procesaTexto(TextNode texto, LinkedList<Tag> pilaTags) {
			if (pilaContieneTags(pilaTags, tagBuscado)) {
				if (jugador) {
					if (equipo) {
						// aFichero(texto.getText());
						nombre1 = texto.getText();
						nombre = true;
						equipo = false;
					} else if (nombre) {
						puntos1 = Integer.parseInt(Crawler.removeDot(texto.getText()));
						nombre = false;
					} else if (puntos && i == 1) {
						if (!texto.getText().contains(" ") || !texto.getText().contains("-")) {
							valor1 = Integer.parseInt(Crawler.removeDot(texto.getText()));
						}
					}
					if (i == 5) {
						if (!texto.getText().contains(" ") || !texto.getText().contains("-")) {
							puntosj = Integer.parseInt(Crawler.removeDot(texto.getText()));
						}
						jugador = false;
						puntos = false;
						try {
							actualizarJugador(n, nombre1, equipo1, posicion1, puntos1, puntosj, valor1);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			if (listaequipos) {
				actualizarListaEquipos(texto.getText());
			}

		}

		@Override
		public void procesaTag(Tag tag, LinkedList<Tag> pilaTags) {
			// TODO programación del método (si procede)
			if (tag.getTagName().equals("TR") && tag.getText().contains("tr class=\"head\"")) { // Empieza la lista
				lista = true;
			} else if (lista && tag.getTagName().equals("TR")) { // Empieza el jugado
				jugador = true;
				i = 0;
			} else if (jugador && tag.getTagName().equals("SPAN") && tag.getText().contains("pos")) {
				posicion1 = tag.getText();
				posicion = true;

				if (posicion1.equals("span class=\"pos-1\"")) {
					posicion1 = "Portero";
				} else if (posicion1.equals("span class=\"pos-2\"")) {
					posicion1 = "Defensa";
				} else if (posicion1.equals("span class=\"pos-3\"")) {
					posicion1 = "Mediocentro";
				} else if (posicion1.equals("span class=\"pos-4\"")) {
					posicion1 = "Delantero";
				}
				i = i + 1;
			} else if (posicion && tag.getTagName().equals("A") && tag.getText().contains("equipos")) {
				equipo = true;
				equipo1 = tag.getText();
				for (int i = 0; i < Equipos.size(); i++) {
					String equipocomparar = Equipos.get(i).toLowerCase();
					if (equipo1.contains(equipocomparar)) {
						equipo1 = Equipos.get(i);
					} else if (equipo1.contains("leganes")) {
						equipo1 = "Leganes";
					} else if (equipo1.contains("real-madrid")) {
						equipo1 = "Real Madrid";
					} else if (equipo1.contains("atletico")) {
						equipo1 = "Atletico";
					} else if (equipo1.contains("athletic")) {
						equipo1 = "Athletic";
					} else if (equipo1.contains("alaves")) {
						equipo1 = "Alaves";
					} else if (equipo1.contains("real-sociedad")) {
						equipo1 = "Real Sociedad";
					} else if (equipo1.contains("las-palmas")) {
						equipo1 = "Las Palmas";
					} else if (equipo1.contains("malaga")) {
						equipo1 = "Malaga";
					}
				}
			} else if (jugador && tag.getTagName().equals("TD") && tag.getText().equals("td class=\"aright font-m\"")) {
				puntos = true;
			} else if (jugador && tag.getTagName().equals("SPAN")) {
				i = i + 1;
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

	public static String removeDot(String s) {
		String r = "";
		if (s.equals("-")) {
			r = "0";
		} else {
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == '.') {
				} else {
					r = r + s.charAt(i);
				}
			}
		}
		return r;
	}

	public static void actualizarListaEquipos(String string) {
		Equipos.add(string);
	}

	public static void actualizarJugador(int k, String nombre1, String equipo1, String posicion1, int puntos1, int puntosJ,
			int valor1) throws SQLException {
		Jugador j = new Jugador(n, nombre1, equipo1, posicion1, puntos1, puntosJ, valor1);
		BD.actualizarJugador(j);
		n++;
	}

	public ArrayList<String> getEquipos() {
		return Equipos;
	}

	public void setEquipos(ArrayList<String> equipos) {
		Equipos = equipos;
	}
}
