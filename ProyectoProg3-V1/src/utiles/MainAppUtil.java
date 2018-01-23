package utiles;

import java.sql.SQLException;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import aplicacionusuario.datos.BD;

public class MainAppUtil {
	
	/**
	 * Clase main que ejecuta el crawler, resuelve ofertas, da el dinero y saca el mercado de cada liga
	 * @param args
	 * @throws SchedulerException
	 * @throws InterruptedException
	 */

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		org.apache.log4j.BasicConfigurator.configure();
		try {
			BD.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*
		 * Uso de la libreria quarz para que el programa ejecute el codigo en un tiempo determinado
		 */
		
		JobKey tarea1key = new JobKey("Tarea1");
		JobKey tarea2key = new JobKey("Tarea2");
		JobKey tarea3key = new JobKey("Tarea3");
		JobDetail tarea1 = JobBuilder.newJob(Tarea1.class).withIdentity(tarea1key).build();
		JobDetail tarea2 = JobBuilder.newJob(Tarea2.class).withIdentity(tarea2key).build();
		JobDetail tarea3 = JobBuilder.newJob(Crawler.class).withIdentity(tarea3key).build();
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();
		
		//Trigger 1 que ejecuta el codigo cada dia a la 1am
		Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("trigger1")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 45 20 * * ?")).build();
		//Trigger 2 que ejecuta el codigo martes y viernes a la 1am	
		Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 * 2,5 ?")).build();
		//Trigger 3 que ejecuta el codigo cada hora
		Trigger trigger3 = TriggerBuilder.newTrigger().withIdentity("trigger3")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 * * * ?")).build();
		
		//Se inicia el scheduler
		scheduler.start();
		scheduler.scheduleJob(tarea1, trigger1);
		scheduler.scheduleJob(tarea2, trigger2);
		scheduler.scheduleJob(tarea3, trigger3);
		Thread.sleep(300L * 1000L);
		scheduler.shutdown(true);
	}

}
