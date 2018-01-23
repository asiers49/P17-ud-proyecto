package utiles;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import aplicacionusuario.datos.BD;

public class Tarea2 implements Job {
	
	/**
	 * Tarea 2 que calcula los puntos cuando acaba la jornada
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		BD.calcularPuntos();
		System.out.println("Tarea 2 se acabo");
	}

}
