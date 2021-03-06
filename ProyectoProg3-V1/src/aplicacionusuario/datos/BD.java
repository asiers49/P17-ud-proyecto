package aplicacionusuario.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JOptionPane;

public class BD {
	private static final String URL = "ec2-184-73-206-155.compute-1.amazonaws.com:5432/dacbprtaga7o1f";
	private static final String USERNAME = "nhbcdfgbdulfyd";
	private static final String PASSWORD = "8be9eab9ba5530cc1b79809fe9310e3f318c1ecf5ec0ec96150cffc457d37c2c";
	private static Connection conn;

	/**
	 * Metodo que establece conexion con la base de datos
	 * 
	 * @throws SQLException
	 */

	public static void getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
		}
		conn = DriverManager.getConnection("jdbc:postgresql://" + URL + "?sslmode=require", USERNAME, PASSWORD);

	}

	/**
	 * Inserta un jugador nuevo en la base de datos
	 * 
	 * @param Jugador
	 *            j
	 */

	public static void insertJugador(Jugador j) {
		int cod = j.getCod_jugador();
		String nombre = j.getNombre();
		String equipo = j.getEquipo();
		String posicion = j.getPosicion();
		int valor = j.getValor();
		int puntos = j.getPuntos();
		int puntosjornada = j.getPuntosJornada();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO JUGADOR VALUES (" + cod + ",'" + nombre + "', '" + equipo + "', '"
					+ posicion + "', " + puntos + "," + puntosjornada + ", " + valor + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Actualiza los valores en la base de datos de cada jugador
	 * 
	 * @param j
	 */

	public static void actualizarJugador(Jugador j) {
		String nombre = j.getNombre();
		String equipo = j.getEquipo();
		String posicion = j.getPosicion();
		int valor = j.getValor();
		int puntos = j.getPuntos();
		int puntosjornada = j.getPuntosJornada();
		try {
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery("SELECT cod_jugador from JUGADOR WHERE nomjugador='" + j.getNombre() + "'");
			Statement stm2 = conn.createStatement();
			while (rs.next()) {
				stm2.executeUpdate("UPDATE JUGADOR SET nomjugador = '" + nombre + "', equipo = '" + equipo
						+ "', posicion= '" + posicion + "', puntos=" + puntos + ", puntosjornada=" + puntosjornada
						+ ", valor = " + valor + " WHERE cod_jugador=" + rs.getInt(1));
			}
			rs.close();
			stm2.close();

			System.out.println("Record (Jugador: "+j.getNombre()+") is updated to  table!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * inserta en la tabla relacion los codigos de los jugadores de esa liga
	 * 
	 * @param l
	 *            liga nueva.
	 */

	public static void nuevaLiga(Liga l) {
		ArrayList<Integer> codigos = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT cod_jugador FROM JUGADOR");
			while (rs.next()) {
				codigos.add(rs.getInt(1));
			}
			for (int h = 0; h < codigos.size(); h++) {
				stmt.executeUpdate("INSERT INTO RELACION (codjugador, nomliga) VALUES (" + codigos.get(h) + ", '"
						+ l.getNombre() + "')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param user
	 */

	public static void nuevoUsuario(Usuario user) {
		String nombre = user.getNombre();
		String contrasenya = user.getContraseña();
		String mail = user.getEmail();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO USUARIOS VALUES ('" + nombre + "', '" + contrasenya + "', '" + mail + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Busca el usuario que entra como parametro en la base de datos y si esta en la
	 * base de datos saca todos sus atributos
	 * 
	 * @param u
	 *            usuario
	 * @return usuario
	 */

	public static Usuario buscarUsuario(Usuario u) {
		Usuario user = null;
		String nomliga = null;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");
			while (rs.next()) {
				if (rs.getString(1).equals(u.getNombre()) && rs.getString(2).equals(u.getContraseña())) {

					user = new Usuario();
					user.setNombre(rs.getString(1));
					user.setContraseña(rs.getString(2));
					user.setEmail(rs.getString(3));
					nomliga = rs.getString(4);
					user.setDinero(rs.getLong(7));
				}
			}
			ResultSet rs2 = stmt.executeQuery("SELECT * FROM LIGAS WHERE nomliga='" + nomliga + "'");
			while (rs2.next()) {
				Liga liga = new Liga(rs2.getString(1), rs2.getString(2));
				user.setLiga(liga);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;

	}

	/**
	 * Busca un mail en la base de datos y devuelve la contraseña de ese usuario
	 * 
	 * @param mail
	 * @return
	 */

	public static String olvidarContra(String mail) {

		String pass = "";

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT contrasenya FROM usuarios WHERE mail ='" + mail + "'");
			while (rs.next()) {
				pass = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pass;
	}

	/**
	 * Saca una clave aleatoria para cada liga
	 * 
	 * @return String contrasenya
	 */

	public static String contrasenyaAleatoria() {
		String cal = UUID.randomUUID().toString().substring(28);
		return cal;
	}

	/**
	 * Comprueba si una liga esta en la base de datos
	 * 
	 * @param liga
	 * @return boolean true si esta, false si no esta
	 */

	private static boolean buscarLiga(Liga liga) {
		boolean encontrado = false;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM ligas");
			while (rs.next()) {
				if (rs.getString(1).equals(liga.getNombre())) {
					if (rs.getString(2).equals(liga.getClave())) {
						encontrado = true;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return encontrado;
	}

	/**
	 * Inserta en la tabla ligas una nueva liga, y actualiza atributos en el usuario
	 * 
	 * @param u
	 *            usuario
	 * @param nombre
	 *            de la liga
	 */

	public static void crearliga(Usuario u, String nombre) {
		Liga liga1 = new Liga(nombre, null);
		try {
			Statement stmt = conn.createStatement();
			if (!buscarLiga(liga1)) {
				liga1.setClave(contrasenyaAleatoria());
				u.setLiga(liga1);
				stmt.executeUpdate("INSERT INTO LIGAS VALUES('" + liga1.getNombre() + "','" + liga1.getClave() + "')");
				stmt.executeUpdate("UPDATE USUARIOS SET nomliga='" + liga1.getNombre()
						+ "' , dinero=15000000  WHERE nombre ='" + u.getNombre() + "'");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nuevaLiga(liga1);
		crearEquipo(u, liga1);
	}

	/**
	 * 
	 * @param u
	 * @param nombre
	 * @param clave
	 */

	public static void unirseaLiga(Usuario u, String nombre, String clave) {
		Liga liga1 = new Liga(nombre, clave);
		try {
			Statement stmt = conn.createStatement();
			if (buscarLiga(liga1)) {
				stmt.executeUpdate("UPDATE USUARIOS SET nomliga='" + liga1.getNombre()
						+ "', puntos=0, puntosjornada=0, dinero=15000000 WHERE nombre ='" + u.getNombre() + "'");
			} else {
				JOptionPane.showMessageDialog(null, "La liga no es correcta");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		crearEquipo(u, liga1);
	}

	/**
	 * Saca los usuarios que pertenecen a una liga
	 * 
	 * @param u
	 *            usario que pertenece a esa liga
	 * @return lista de usuarios
	 */

	public static ArrayList<Usuario> sacarUsuariosLiga(Usuario u) {
		ArrayList<Usuario> lista = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT * FROM USUARIOS WHERE NOMLIGA IN (SELECT NOMLIGA FROM USUARIOS WHERE NOMBRE='"
							+ u.getNombre() + "')");
			while (rs.next()) {
				String nombre = rs.getString(1);
				int puntos = rs.getInt(5);
				int puntosJornada = rs.getInt(6);
				lista.add(new Usuario(nombre, puntos, puntosJornada));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;

	}

	/**
	 * Crea un nuevo equipo, utiliza un algoritmo que calcula el valor de equipo y
	 * va sacando aleatoriamente jugadores de la base de datos hasta que el valor de
	 * equipo sea de alrededor 28 millones
	 * 
	 * @param u
	 *            usuario al que se le crea el equipo
	 * @param l
	 *            liga a la que pertenece
	 */

	private static void crearEquipo(Usuario u, Liga l) {
		int valorequipoTotal = 0;
		int valorequipo = 0;
		boolean seguir = true;
		ArrayList<Jugador> equipo = new ArrayList<>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			while (seguir) {

				ResultSet rs = stmt.executeQuery("SELECT * FROM PORTEROS ORDER BY RANDOM() LIMIT 2");
				while (rs.next()) {
					equipo.add(new Jugador(rs.getInt(1), rs.getInt(7)));
					valorequipo = valorequipo + rs.getInt(7);
				}
				if (valorequipo > 550000 && valorequipo < 2500000) {
					seguir = false;
				} else {
					equipo.removeAll(equipo);
					valorequipo = 0;
				}

			}
			valorequipoTotal = valorequipo;
			seguir = true;
			while (seguir) {

				ResultSet rs = stmt.executeQuery("SELECT * FROM DEFENSAS ORDER BY RANDOM() LIMIT 7");
				while (rs.next()) {
					equipo.add(new Jugador(rs.getInt(1), rs.getInt(7)));
					valorequipo = valorequipo + rs.getInt(7);
				}
				if (valorequipo > 3150000 && valorequipo < 12000000) {
					seguir = false;
				} else {
					for (int k = equipo.size() - 1; k > 1; k--) {
						equipo.remove(equipo.get(k));
					}
					valorequipo = valorequipoTotal;
				}

			}
			valorequipoTotal = valorequipo;
			seguir = true;
			while (seguir) {

				ResultSet rs = stmt.executeQuery("SELECT * FROM MEDIOS ORDER BY RANDOM() LIMIT 6");
				while (rs.next()) {
					equipo.add(new Jugador(rs.getInt(1), rs.getInt(7)));
					valorequipo = valorequipo + rs.getInt(7);
				}
				if (valorequipo > 11150000 && valorequipo < 21000000) {
					seguir = false;
				} else {
					for (int k = equipo.size() - 1; k > 8; k--) {
						equipo.remove(equipo.get(k));
					}
					valorequipo = valorequipoTotal;

				}

			}
			valorequipoTotal = valorequipo;
			seguir = true;
			while (seguir) {

				ResultSet rs = stmt.executeQuery("SELECT * FROM DELANTEROS ORDER BY RANDOM() LIMIT 4");
				while (rs.next()) {
					equipo.add(new Jugador(rs.getInt(1), rs.getInt(7)));
					valorequipo = valorequipo + rs.getInt(7);
				}

				if (valorequipo > 25150000 && valorequipo < 30000000) {
					seguir = false;
				} else {
					for (int k = equipo.size() - 1; k > 14; k--) {
						equipo.remove(equipo.get(k));
					}

					valorequipo = valorequipoTotal;
				}
			}
			for (int i = 0; i < equipo.size(); i++) {
				stmt.executeUpdate("UPDATE RELACION SET NOMUSUARIO='" + u.getNombre() + "' WHERE NOMLIGA='"
						+ l.getNombre() + "' AND CODJUGADOR =" + equipo.get(i).getCod_jugador());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		valorequipoTotal = valorequipo;

	}

	/**
	 * 
	 */

	public static void calcularPuntos() {
		ArrayList<String> nomusuarios = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT nombre from usuarios ");
			while (rs.next()) {
				nomusuarios.add(rs.getString(1));
			}
			for (String nombre : nomusuarios) {
				ArrayList<Integer> ptsjugadores = new ArrayList<>();
				ResultSet rs2 = stmt.executeQuery(
						"SELECT  public.jugador.puntosjornada FROM public.jugador INNER JOIN public.relacion ON (public.jugador.cod_jugador = public.relacion.codjugador)	WHERE public.relacion.nomusuario = '"
								+ nombre + "' AND public.relacion.titular=true");

				while (rs2.next()) {
					ptsjugadores.add(rs2.getInt(1));
				}
				int puntos = calcPuntosUsuario(ptsjugadores, 0);
				stmt.executeUpdate("UPDATE USUARIOS SET puntos=puntos +" + puntos + ", puntosJornada=" + puntos
						+ ", dinero=dinero +" + (puntos * 100000) + " WHERE nombre ='" + nombre + "'");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Metodo recursivo que calcula los puntos de jornada de ese usuario
	 * 
	 * @param ptosjugadores
	 *            de los jugadores del usuario que son titulares
	 * @param i
	 *            indice
	 * @return
	 */

	public static int calcPuntosUsuario(ArrayList<Integer> ptosjugadores, int i) {
		int puntos = 0;
		int s;

		if (i < ptosjugadores.size()) {
			s = calcPuntosUsuario(ptosjugadores, i + 1);
			puntos = s + ptosjugadores.get(i);
		}
		return puntos;

	}

	/**
	 * 
	 * @param u
	 * @return
	 */

	public static ArrayList<Jugador> sacarEquipo(Usuario u) {
		ArrayList<Jugador> equipo = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT  public.jugador.cod_jugador, public.jugador.nomjugador, public.jugador.equipo, public.jugador.posicion, public.jugador.puntos, public.jugador.puntosjornada, public.jugador.valor, public.relacion.titular FROM public.jugador INNER JOIN public.relacion ON (public.jugador.cod_jugador = public.relacion.codjugador)	WHERE public.relacion.nomusuario = '"
							+ u.getNombre() + "'");
			while (rs.next()) {
				Jugador j = new Jugador(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getInt(6), rs.getInt(7), rs.getBoolean(8));
				equipo.add(j);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return equipo;
	}

	/**
	 * Hace a un jugador titular, actualizando la tabla relacion
	 * 
	 * @param u
	 *            usuario
	 * @param Cod_Jugador
	 *            del jugador
	 */

	public static void hacerTitular(Usuario u, int Cod_Jugador) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE RELACION SET TITULAR=true WHERE NOMUSUARIO='" + u.getNombre()
					+ "' AND CODJUGADOR=" + Cod_Jugador);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Hace a un jugador suplente, actualizando la tabla relacion
	 * 
	 * @param u
	 *            usuario
	 * @param Cod_Jugador
	 *            del jugador
	 */

	public static void hacerSuplente(Usuario u, int Cod_Jugador) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE RELACION SET TITULAR=false WHERE NOMUSUARIO='" + u.getNombre()
					+ "' AND CODJUGADOR=" + Cod_Jugador);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Coge de la tabla mercado, los jugadores que estan en el mercado
	 * correpondiente a esa liga
	 * 
	 * @param l
	 *            liga
	 * @return lista de jugadores
	 */
	public static ArrayList<Jugador> actualizarMercado(Liga l) {
		ArrayList<Jugador> listamercado = new ArrayList<>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM public.jugador WHERE cod_jugador in (Select cod_jugador from public.mercado WHERE nomliga='"
							+ l.getNombre() + "')");
			while (rs.next()) {
				Jugador j = new Jugador(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getInt(6), rs.getInt(7), false);
				listamercado.add(j);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listamercado;
	}

	/**
	 * Inserta en la tabla mercado 10 jugadores aleatorios por cada liga
	 */

	public static void sacarJugadoresMercado() {
		ArrayList<String> nomligas = new ArrayList<>();

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("Delete from mercado");
			ResultSet rs = stmt.executeQuery("SELECT nomliga from ligas");
			while (rs.next()) {
				nomligas.add(rs.getString(1));
			}
			for (String l : nomligas) {
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery(
						"SELECT cod_jugador FROM public.jugador WHERE cod_jugador in (Select cod_jugador from relacion WHERE nomusuario IS NULL AND nomliga='"
								+ l + "') ORDER BY RANDOM() LIMIT 10");

				while (rs2.next()) {
					stmt.executeUpdate("INSERT INTO MERCADO VALUES(" + rs2.getInt(1) + ", '" + l + "')");

				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Obtiene las ofertas de cada jugador y selecciona la mas alta, a continuacion
	 * llama al metodo comprarJugador
	 */
	
	public static void sacarOfertas() {
		ArrayList<String> nomligas = new ArrayList<>();
		ArrayList<Integer> codjugadores;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT nomliga from Ofertas ");
			while (rs.next()) {
				nomligas.add(rs.getString(1));
			}
			rs.close();
			for (String a : nomligas) {
				Statement stmt2 = conn.createStatement();
				ResultSet rs2 = stmt2.executeQuery("SELECT DISTINCT CODJUGADOR FROM OFERTAS WHERE nomliga='" + a + "'");
				codjugadores = new ArrayList<>();
				while (rs2.next()) {
					codjugadores.add(rs2.getInt(1));
				}
				for (int cod : codjugadores) {

					Statement stmt3 = conn.createStatement();
					ResultSet rs3 = stmt3.executeQuery(
							"SELECT * FROM OFERTAS WHERE VALOR IN (SELECT MAX(VALOR) FROM OFERTAS WHERE nomliga='" + a
									+ "' AND codjugador=" + cod + ")");
					while (rs3.next()) {
						comprarJugador(rs3.getString(1), rs3.getInt(2), rs3.getInt(3), rs3.getString(4));

					}
				}

			}
			stmt.executeUpdate("Delete from Ofertas");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Inserta en la tabla ofertas, una nueva puja por un jugador
	 * 
	 * @param u
	 *            usuario que realiza la puja
	 * @param j
	 *            jugador que se quiere comprar
	 * @param valor
	 *            de la puja
	 */

	public static void hacerPuja(Usuario u, Jugador j, int valor) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO OFERTAS VALUES ('" + u.getNombre() + "'," + j.getCod_jugador() + ", "
					+ valor + ", '" + u.getLiga().getNombre() + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Actualiza la tabla relacion cuando un usuario compra un jugador
	 * 
	 * @param u
	 * @param j
	 */

	public static void comprarJugador(String u, int j, int precio, String l) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(
					"UPDATE RELACION SET NOMUSUARIO='" + u + "' WHERE nomliga='" + l + "' AND CODJUGADOR=" + j + "");
			stmt.executeUpdate("UPDATE USUARIOS SET dinero=dinero -" + precio + " WHERE nombre ='" + u + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Actualiza las tablas relacion y usuarios cuando un usuario vende a un jugador
	 * 
	 * @param u usuario
	 * @param j jugador
	 * @param precio 
	 */

	public static void venderJugador(Usuario u, Jugador j, int precio) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE RELACION SET NOMUSUARIO=NULL WHERE nomliga='" + u.getLiga().getNombre()
					+ "' AND CODJUGADOR=" + j.getCod_jugador() + "");

			stmt.executeUpdate(
					"UPDATE USUARIOS SET dinero=dinero +" + precio + " WHERE nombre ='" + u.getNombre() + "'");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Cierra la conexion con la base de datos
	 */

	public static void cerrarConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


}
