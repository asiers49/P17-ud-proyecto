package datos;

public class Usuario {
	private String nombre;
	private String contraseña;
	private String email;
	private Equipo miEquipo;
	private Liga liga;
	private long dinero;
	private int puntos;
	private int puntosJornada;

	public Usuario() {

	}

	public Usuario(String nombre, String contraseña, String email) {
		this.setNombre(nombre);
		this.setContraseña(contraseña);
		this.email = email;
		this.setMiEquipo(null);
		this.setLiga(null);
	}
	
	public Usuario(String nombre, int puntos, int puntosJornada) {
		this.nombre=nombre;
		this.puntos=puntos;
		this.puntosJornada=puntosJornada;
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

}
