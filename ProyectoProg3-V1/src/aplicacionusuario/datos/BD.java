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
	 * 
	 * @param j
	 */

	public static void actualizarJugador(Jugador j) { // FALTA
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

			System.out.println("Record is updated to  table!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * 
	 */

	public static void nuevaLiga(Liga l) {
		ArrayList<Integer> codigos = new ArrayList<>();
		int i = 0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT cod_jugador FROM JUGADOR");
			while (rs.next()) {
				codigos.add(rs.getInt(1));
				System.out.println("jugador sacado " + (i + 1));
				i++;
			}
			for (int h = 0; h < codigos.size(); h++) {
				stmt.executeUpdate("INSERT INTO RELACION (codjugador, nomliga) VALUES (" + codigos.get(h) + ", '"
						+ l.getNombre() + "')");
				System.out.println("Jugador insertado " + h);
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

	/*
	 * 
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

	public static String contrasenyaAleatoria() {
		String cal = UUID.randomUUID().toString().substring(28); // String
																	// aleatoria
																	// de 8
																	// alfanumerics
																	// chars
		return cal;
	}

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

	public static void crearliga(Usuario u, String nombre) {
		Liga liga1 = new Liga(nombre, null);
		try {
			Statement stmt = conn.createStatement();
			if (!buscarLiga(liga1)) {
				liga1.setClave(contrasenyaAleatoria());
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

	public static void unirseaLiga(Usuario u, String nombre, String clave) {
		Liga liga1 = new Liga(nombre, clave);
		try {
			Statement stmt = conn.createStatement();
			if (buscarLiga(liga1)) {
				stmt.executeUpdate("UPDATE USUARIOS SET nomliga='" + liga1.getNombre()
						+ "', dinero=15000000 WHERE nombre ='" + u.getNombre() + "'");
			} else {
				JOptionPane.showMessageDialog(null, "La liga no es correcta");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		crearEquipo(u, liga1);
	}

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
				System.out.println(j.getNombre() + " " + j.getPosicion() + " " + j.getCod_jugador());
				equipo.add(j);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return equipo;
	}

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

	public static ArrayList<Jugador> sacarJugadoresMercado(Liga l) {
		ArrayList<Jugador> listamercado = new ArrayList<>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM public.jugador WHERE cod_jugador in (Select cod_jugador from relacion WHERE nomusuario IS NULL AND nomliga='"
							+ l.getNombre() + "') ORDER BY RANDOM() LIMIT 10");
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
	
	public static void comprarJugador(Usuario u, Jugador j) {
		System.out.println("comprar jugadores");

		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("UPDATE RELACION SET NOMUSUARIO='" + u.getNombre() + "' WHERE nomliga='"
					+ u.getLiga().getNombre() + "' AND CODJUGADOR=" + j.getCod_jugador() + "");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void venderJugador(Usuario u, Jugador j) {

	}

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

	public static void main(String[] args) {

		try {
			getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
