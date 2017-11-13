package datos;

import java.util.ArrayList;

public class Liga {
	
	private String nombre;
	private double clave;
	private ArrayList<Usuario> listausuarios;

	
	
	public Liga(String nombre, double clave) {
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

	public double getClave() {
		return clave;
	}

	public void setClave(double clave) {
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
