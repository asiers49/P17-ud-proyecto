package utiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import aplicacionusuario.datos.BD;

public class Main {

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		org.apache.log4j.BasicConfigurator.configure();
		VentanaUtiles ventana = new VentanaUtiles();
		ventana.setVisible(true);
		try {
			BD.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JobKey tarea1key = new JobKey("Tarea1");
		JobKey tarea2key = new JobKey("Tarea2");
		JobDetail tarea1 = JobBuilder.newJob(Tarea1.class).withIdentity(tarea1key).build();
		JobDetail tarea2 = JobBuilder.newJob(Crawler.class).withIdentity(tarea2key).build();
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();

		
		Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("trigger1")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 2 * * ?")).build();
		Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("trigger2")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 0 * * * ?")).build();
		scheduler.start();
		scheduler.scheduleJob(tarea1, trigger1);
		scheduler.scheduleJob(tarea2, trigger2);
		Thread.sleep(300L * 1000L);
		scheduler.shutdown(true);
	}

	

	public static void calcularPuntos() {

	}

	public static void crearMercado() {

	}
}
