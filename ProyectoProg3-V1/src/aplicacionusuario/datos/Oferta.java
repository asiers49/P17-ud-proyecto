package aplicacionusuario.datos;

public class Oferta {
	
	private Jugador jugador;
	private int valor;
	private Usuario user;
	
	public Oferta(Jugador jugador, int valor, Usuario user) {
		this.jugador = jugador;
		this.valor = valor;
		this.user = user;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
	
	
}
