package datos;

public class Jugador {
	private String nombre;
	private int puntos;
	private int puntosJornada;
	private long valor;
	private String posicion;
	
	public Jugador(String nombre, int p, int pJ, long v, String posicion) {
		this.nombre=nombre;
		this.puntos=p;
		this.puntosJornada=pJ;
		this.valor=v;
		this.posicion=posicion;
		
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
	public long getValor() {
		return valor;
	}
	public void setValor(long valor) {
		this.valor = valor;
	}
	public String getPosicion() {
		return posicion;
	}
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}
	
	
	
}
