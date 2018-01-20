package aplicacionusuario.datos;

import java.util.ArrayList;

public class Equipo {
	
	private int alineación;
	
	private ArrayList<Jugador> equipo;

	public int getAlineación() {
		return alineación;
	}

	public void setAlineación(int alineación) {
		this.alineación = alineación;
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
