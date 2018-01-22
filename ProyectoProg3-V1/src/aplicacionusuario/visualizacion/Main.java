package aplicacionusuario.visualizacion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import aplicacionusuario.datos.BD;
import aplicacionusuario.datos.Usuario;

public class Main {

	static Usuario u;

	/**
	 * Inicio de la aplicacion
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		// Properties
		Main m = new Main();
		u = new Usuario();
		try {
			BD.getConnection();
			m.cargaProperties();
			VentanaPrincipal vp = new VentanaPrincipal(u);
			vp.setVisible(true);
		} catch (IOException e) {
			VentanaInicio vi = new VentanaInicio();
			vi.setVisible(true);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Properties properties;

	/**
	 * Carga del fichero properties el usuario y contrasenya que estaban
	 * registrados cuando se cerro la aplicacion por ultima vez
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvalidPropertiesFormatException
	 * 
	 */
	private void cargaProperties() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		properties = new Properties();
		String usuario = " ";
		String contrasenya = "";
		properties.load(new FileInputStream("usuarios.ini"));
		usuario = properties.getProperty("usuario");
		contrasenya = properties.getProperty("contra");
		u = BD.buscarUsuario(new Usuario(usuario, contrasenya, ""));
		if (u == null) {
			throw new IOException();
		}
	}

	/**
	 * Guarda en fichero el usuario y la contraseï¿½a
	 */

	public static void guardaProperties(Usuario u) {
		properties = new Properties();
		String usuario = u.getNombre();
		String contrasenya = u.getContraseña();

		properties.setProperty("usuario", usuario);
		properties.setProperty("contra", contrasenya);

		try {
			properties.store(new FileOutputStream("usuarios.ini"), "");

		} catch (Exception e) {

		}
	}
}
