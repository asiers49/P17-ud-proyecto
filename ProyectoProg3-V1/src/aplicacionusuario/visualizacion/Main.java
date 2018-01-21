package aplicacionusuario.visualizacion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

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
		
		try {
			
			m.cargaProperties();
			VentanaPrincipal vp=new VentanaPrincipal(u);
			vp.setVisible(true);
		} catch (IOException e) {
			VentanaInicio vi=new VentanaInicio();
			vi.setVisible(true);
			
		

		}

	}

	private static Properties properties;

	/**
	 * Carga del fichero properties el usuario y contrase�a que estaban registrados
	 * cuando se cerro la aplicacion por ultima vez
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvalidPropertiesFormatException
	 * 
	 */
	private void cargaProperties() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		properties = new Properties();
		String usuario= " ";
		String contraseña="";
		
		properties.load(new FileInputStream("usuarios.ini"));
		usuario = properties.getProperty(usuario);
		contraseña=properties.getProperty(contraseña);
		
		
		}

	/**
	 * Guarda en fichero el usuario y la contrase�a
	 */

	public static void guardaProperties(Usuario u) {
		properties = new Properties();
		String usuario= u.getNombre();
		String contraseña=u.getContrase;
		
		

		try {
			properties.store(new FileOutputStream("usuarios.ini"), "");
			properties.setProperty(usuario, contraseña);
			
			
			} catch (Exception e) {
			
			
		}
	}
}
