package utiles;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import aplicacionusuario.datos.BD;

public class Tarea1 implements Job {
	
	/**
	 * Tarea 1 que resuelve las ofertas y saca el mercado
	 */

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("Empieza Tarea 1");
		BD.sacarOfertas();
		System.out.println("Ofertas resueltas");
		BD.sacarJugadoresMercado();
		System.out.println("Tarea 1 acabo");
	}
	
}
