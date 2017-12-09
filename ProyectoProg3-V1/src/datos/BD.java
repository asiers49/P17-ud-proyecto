package datos;

import java.sql.*;
import java.util.ArrayList;

public class BD {
	private static final String URL = "ec2-184-73-206-155.compute-1.amazonaws.com:5432/dacbprtaga7o1f";
	private static final String USERNAME = "nhbcdfgbdulfyd";
	private static final String PASSWORD = "8be9eab9ba5530cc1b79809fe9310e3f318c1ecf5ec0ec96150cffc457d37c2c";
	private static Connection conn;

	public static void getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
		}
		conn = DriverManager.getConnection("jdbc:postgresql://" + URL + "?sslmode=require", USERNAME, PASSWORD);

	}

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

	public static ArrayList<Jugador> obtenerJugadores() {

		return null;

	}

	public static void nuevoUsuario(Usuario user) {
		int cod = user.getCod_usuario();
		String nombre = user.getNombre();
		String contrasenya = user.getContraseña();
		String mail = user.getEmail();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO USUARIOS VALUES (" + cod + ",'" + nombre + "', '" + contrasenya + "', '"
					+ mail + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static ArrayList<Integer> codsJugadores;
	private static ArrayList<Integer> codsUsuarios;

	public static int crearCodigo(int tipo) {
		boolean igual = false;
		int n = 0;
		if (tipo == 0) {
			n = (int) Math.random();
			for (int i = 0; i < codsJugadores.size(); i++) {
				if (codsJugadores.get(i) != n) {
				} else {
					igual = true;
				}
			}
			if (!igual) {
				return n;

			} else {
				crearCodigo(0);
			}

		} else if (tipo == 1) {
			n = (int) Math.random();
			for (int i = 0; i < codsUsuarios.size(); i++) {
				if (codsUsuarios.get(i) != n) {
				} else {
					igual = true;
				}
			}
			if (!igual) {
				return n;

			} else {
				crearCodigo(1);
			}
		}
		return n;
		
	}
}
