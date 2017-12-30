package datos;

import java.lang.Thread.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

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
	 * @param j
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

	public static void actualizarJugador(Jugador j) {
		int cod = j.getCod_jugador();
		String nombre = j.getNombre();
		String equipo = j.getEquipo();
		String posicion = j.getPosicion();
		int valor = j.getValor();
		int puntos = j.getPuntos();
		int puntosjornada = j.getPuntosJornada();
		try {
			Statement stm = conn.createStatement();

			stm.executeUpdate("UPDATE JUGADOR SET nomjugador = '" + nombre + "', equipo = '" + equipo + "', posicion= '"
					+ posicion + "', puntos=" + puntos + ", puntosjornada=" + puntosjornada + ", valor = " + valor
					+ " WHERE cod_jugador=" + cod);

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
		int i=0;
		try {
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT cod_jugador FROM JUGADOR");
			while(rs.next()) {
				codigos.add(rs.getInt(1));
				System.out.println("jugador sacado "+(i+1));
				i++;
			}
			for (int h=0;h<codigos.size();h++) {
				stmt.executeUpdate("INSERT INTO RELACION (codjugador, nomliga) VALUES ("+codigos.get(h)+", '"+l.getNombre()+"')");
				System.out.println("Jugador insertado "+h);
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
				stmt.executeUpdate("UPDATE USUARIOS SET nomliga='" + liga1.getNombre() + "' WHERE nombre ='"
						+ u.getNombre() + "'");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	nuevaLiga(liga1);
	}

	public static void unirseaLiga(Usuario u, String nombre, String clave) {
		Liga liga1 = new Liga(nombre, clave);
		try {
			Statement stmt = conn.createStatement();
			if (buscarLiga(liga1)) {
				stmt.executeUpdate("UPDATE USUARIOS SET nomliga='" + liga1.getNombre() + "' WHERE nombre ='"
						+ u.getNombre() + "'");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Usuario> sacarUsuariosLiga(Usuario u) {
		ArrayList<Usuario> lista = new ArrayList<>();
		try {
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM USUARIOS WHERE NOMLIGA IN (SELECT NOMLIGA FROM USUARIOS WHERE NOMBRE='"+u.getNombre()+"')");
			while(rs.next()) {
				String nombre=rs.getString(1);
				int puntos=rs.getInt(5);
				int puntosJornada=rs.getInt(6);
				lista.add(new Usuario(nombre,puntos,puntosJornada));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lista;
		
	}
	public static void main(String[] args) {

		try {
			getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buscarUsuario(new Usuario("garrix", " pass", ""));
	}
}
