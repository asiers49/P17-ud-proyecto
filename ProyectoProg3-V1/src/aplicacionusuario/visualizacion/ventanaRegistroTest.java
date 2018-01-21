package aplicacionusuario.visualizacion;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import aplicacionusuario.datos.BD;
import aplicacionusuario.datos.Usuario;

public class ventanaRegistroTest {
	
	
	@Test
	public void consultarBD(){
		String usuario1 = "garrix";
		String contrasenya1 = "pass";
		String usuario2 = "anderja";
		String contrasenya2 = "co";
		Usuario user1 = new Usuario(usuario1, contrasenya1, "");
		Usuario user2 = new Usuario(usuario2, contrasenya2, "");

		try {
			BD.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Usuario u1 = BD.buscarUsuario(user1);
		Usuario u2 = BD.buscarUsuario(user2);

		
		assertEquals("Usuario esta en la BD", usuario1, u1.getNombre());
		assertEquals("Contrae�a esta en la BD", contrasenya1, u1.getContrase�a());

		assertEquals("Usuario esta en la BD", usuario2, u2.getNombre());
		assertEquals("Contrae�a esta en la BD", contrasenya2, u2.getContrase�a());

	}
	
	@Test
	public void consultarContrasenyaIncorrectaBD(){
		String usuario1 = "garrix";
		String contrasenya1 = "pas"; // contrase�a correcta "pass"
		String usuario2 = "anderja";
		String contrasenya2 = "CO"; // contrase�a correcta "co"
		Usuario user1 = new Usuario(usuario1, contrasenya1, "");
		Usuario user2 = new Usuario(usuario2, contrasenya2, "");
		
		try {
			BD.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Usuario u1 = BD.buscarUsuario(user1);
		Usuario u2 = BD.buscarUsuario(user2);
		
		
		
		assertTrue("Usuario no esta en la BD", u1==null);
		assertTrue("Usuario no esta en la BD", u2==null);

	

	}
	
}
