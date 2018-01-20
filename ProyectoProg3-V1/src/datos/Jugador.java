package datos;

public class Jugador {
	private int cod_jugador;
	private String nombre;
	private int puntos;
	private int puntosJornada;
	private int valor;
	private String posicion;
	private String equipo;
	private boolean titular;
	private boolean seleccionado;

	public Jugador(int n, String nombre, String equipo, String posicion, int puntos, int puntosJ, int valor, boolean titular) {
		this.setCod_jugador(n);
		this.nombre = nombre;
		this.puntos = puntos;
		this.puntosJornada = puntosJ;
		this.valor = valor;
		this.posicion = posicion;
		this.equipo = equipo;
		this.titular=titular;
		this.setSeleccionado(false);
	}

	public Jugador(int cod_jugador, int valor) {
		this.cod_jugador=cod_jugador;
		this.valor=valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getPuntosJornada() {
		return puntosJornada;
	}

	public void setPuntosJornada(int puntosJornada) {
		this.puntosJornada = puntosJornada;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public int getCod_jugador() {
		return cod_jugador;
	}

	public void setCod_jugador(int cod_jugador) {
		this.cod_jugador = cod_jugador;
	}

	public boolean isTitular() {
		return titular;
	}

	public void setTitular(boolean titular) {
		this.titular = titular;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

}
