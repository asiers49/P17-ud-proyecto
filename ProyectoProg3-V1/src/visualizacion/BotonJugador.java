package visualizacion;

import javax.swing.JButton;

import datos.Jugador;

public class BotonJugador extends JButton {

	private String posicion;
	private Jugador jugador;
	private int numero;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public BotonJugador(Jugador j, String pos) {
		if (j != null) {
			this.jugador = j;
			this.setText(j.getNombre());

		}
		this.posicion = pos;
		this.setText(posicion + " " + numero);
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
		this.setText(jugador.getNombre());
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicionNumero(String posicion, int n) {
		this.posicion = posicion;
		this.numero = n;
		if (this.jugador == null) {
			this.setText(posicion + " " + numero);
		}
	}
}


