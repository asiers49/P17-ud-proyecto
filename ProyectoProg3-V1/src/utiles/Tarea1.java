package utiles;

import java.sql.SQLException;
import java.sql.Statement;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Tarea1 implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("Empieza Tarea 1");
		Ofertas();
		System.out.println("Tarea 1 acabo");
	}

	public void Ofertas() {
		

	}

}
