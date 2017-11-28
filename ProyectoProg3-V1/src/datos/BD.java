package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class BD {
	private static final String DRIVER = "";
	private static final String URL = "";
	private static final String USERNAME = "";
	private static final String PASSWORD = "";

	private static Connection getConnection() throws SQLException {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ex) {
			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			return null;
		}
		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		return conn;
	}


	public static Statement usarBD(Connection con) {
		try {
			Statement statement = con.createStatement();
			return statement;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}