package aplicacionusuario.datos;

import java.util.ArrayList;

public class Equipo {
	
	private int alineaci�n;
	
	private ArrayList<Jugador> equipo;

	public int getAlineaci�n() {
		return alineaci�n;
	}

	public void setAlineaci�n(int alineaci�n) {
		this.alineaci�n = alineaci�n;
	}

	public ArrayList<Jugador> getEquipo() {
		return equipo;
	}

	public void setEquipo(ArrayList<Jugador> equipo) {
		this.equipo = equipo;
	}
	
	public Equipo () {
		
	}
	
	public long getValorEquipo() {
		long valortotal=0;
		for (int i=0; i<equipo.size(); i++) {
			valortotal=valortotal+equipo.get(i).getValor();
		}
		return valortotal;
	}
}
