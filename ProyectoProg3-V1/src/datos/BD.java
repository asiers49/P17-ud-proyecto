package datos;

import java.sql.*;

public class BD {
	private static final String URL = "ec2-184-73-206-155.compute-1.amazonaws.com:5432/dacbprtaga7o1f";
	private static final String USERNAME = "nhbcdfgbdulfyd";
	private static final String PASSWORD = "8be9eab9ba5530cc1b79809fe9310e3f318c1ecf5ec0ec96150cffc457d37c2c";

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			return null;
		}
		Connection conn = DriverManager.getConnection("jdbc:postgresql://" + URL + "?sslmode=require", USERNAME,
				PASSWORD);
		return conn;
	}

	public static void insertJugador(Connection conn, Jugador j) {
		int cod = j.getCod_jugador();
		String nombre = j.getNombre();
		String posicion = j.getPosicion();
		int valor = j.getValor();
		int puntos = j.getPuntos();
		int puntosjornada = j.getPuntosJornada();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO JUGADOR VALUES (" + cod + ",'" + nombre + "', '" + j.getEquipo() + "', '"
					+ posicion + "', " + puntos + "," + puntosjornada + ", " + valor + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void actualizarJugador(Connection conn, Jugador j) {
		int cod = j.getCod_jugador();
		String nombre = j.getNombre();
		String posicion = j.getPosicion();
		int valor = j.getValor();
		int puntos = j.getPuntos();
		int puntosjornada = j.getPuntosJornada();
		String update="UPDATE JUGADOR SET COD_JUGADOR = ? ";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(update);

			preparedStatement.setInt(1, cod);

			// execute update SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Record is updated to table!");
		} catch (SQLException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
