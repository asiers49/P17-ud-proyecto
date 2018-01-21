package aplicacionusuario.visualizacion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import aplicacionusuario.datos.Usuario;

public class Main {
	
	private Usuario u;
	
	/**
	 * Inicio de la aplicacion
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		// Properties
		Main m = new Main();
		try {
			m.cargaProperties();
		} catch (IOException e) {

		}

	}

	private Properties properties;

	/**
	 * Carga del fichero properties el usuario y contraseña que estaban registrados
	 * cuando se cerro la aplicacion por ultima vez
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvalidPropertiesFormatException
	 * 
	 */
	private void cargaProperties() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		properties = new Properties();

		properties.loadFromXML(new FileInputStream("usuarios.ini"));

	}

	/**
	 * Guarda en fichero el usuario y la contraseña
	 */

	private void guardaProperties() {

		try {
			properties.storeToXML(new FileOutputStream("usuarios.ini"), "datos de RevisionZonasUTMGPS");
		} catch (Exception e) {
		}
	}
}
