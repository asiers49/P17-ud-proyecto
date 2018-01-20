package aplicacionusuario.datos;

import java.util.ArrayList;

public class Liga {
	
	private String nombre;
	private String clave;
	private ArrayList<Usuario> listausuarios;

	
	
	public Liga(String nombre, String clave) {
		super();
		this.nombre = nombre;
		this.clave = clave;
		this.listausuarios = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	
	public ArrayList<Usuario> getListausuarios() {
		return listausuarios;
	}

	public void setListausuarios(ArrayList<Usuario> listausuarios) {
		this.listausuarios = listausuarios;
	}
	
	public Liga() {
		
	}
	
	public void entrarLiga(Usuario u) {
		listausuarios.add(u);
		u.setMiEquipo(new Equipo());
	}
}
