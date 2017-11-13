package datos;

import java.security.Timestamp;
import java.util.ArrayList;
import crawler.Crawler;

public class ListaJugadores extends ArrayList<Jugador>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long ultimaActualizacion;
	
	public long getUltimaActualizacion() {
		return ultimaActualizacion;
	}
	public void setUltimaActualizacion(long ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}
	
	public void actualizar () {
		
		Crawler c= new Crawler();
		String urlLaLiga = "http://www.comuniazo.com/comunio/jugadores";
		Crawler.procesaWeb( urlLaLiga, c.p );
		ultimaActualizacion=System.currentTimeMillis();
	}
}
