package datos;

import java.util.ArrayList;

public class Usuario {
	private int cod_usuario;
	private String nombre;
	private String contraseña;
	private String email;
	private Equipo miEquipo;
	private Liga liga;
	private long dinero;
	private int puntos;

	public Usuario() {

	}

	public Usuario(int n, String nombre, String contraseña, String email) {
		this.cod_usuario=n;
		this.setNombre(nombre);
		this.setContraseña(contraseña);
		this.email=email;
		this.setMiEquipo(null);
		this.setLiga(null);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		boolean tieneArroba = false;
		for (int i = 0; i < email.length() && !tieneArroba; i++) {
			if (email.charAt(i) == '@') {
				tieneArroba = true;
				this.email = email;
			}
		}

	}

	public Equipo getMiEquipo() {
		return miEquipo;
	}

	public void setMiEquipo(Equipo miEquipo) {
		this.miEquipo = miEquipo;
	}

	public Liga getLiga() {
		return liga;
	}

	public void setLiga(Liga liga) {
		this.liga = liga;
	}

	public long getDinero() {
		return dinero;
	}

	public void setDinero(long dinero) {
		this.dinero = dinero;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getCod_usuario() {
		return cod_usuario;
	}

	public void setCod_usuario(int cod_usuario) {
		this.cod_usuario = cod_usuario;
	}

}
