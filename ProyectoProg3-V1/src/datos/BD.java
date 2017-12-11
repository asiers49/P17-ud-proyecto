package datos;

import java.sql.*;
import java.util.ArrayList;

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
	public static ArrayList<Jugador> obtenerJugadores() {

		return null;

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
			stmt.executeUpdate("INSERT INTO USUARIOS VALUES ('" + nombre + "', '" + contrasenya + "', '"
					+ mail + "')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 */
	
	public static Usuario buscarUsuario(Usuario u) {
		Usuario user=new Usuario();
		try {
			Statement stmt=conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");
	        while (rs.next()) {
	            if (rs.getString(1).equals(u.getNombre())) {
	            	user.setNombre(rs.getString(1));
	            	System.out.println(user.getNombre());
	            }
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		return user;
		
	}
	public static void main (String[] args) {
		try {
			getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buscarUsuario(new Usuario("garrix"," pass", ""));
	}
}
