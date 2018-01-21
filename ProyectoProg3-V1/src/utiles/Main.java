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

public class Main {

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		org.apache.log4j.BasicConfigurator.configure();
		VentanaUtiles ventana = new VentanaUtiles();
		ventana.setVisible(true);
//		try {
//			getConnection();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		JobKey tarea1key = new JobKey("Tarea1");
		JobDetail tarea1 = JobBuilder.newJob(Tarea1.class).withIdentity(tarea1key).build();
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();

		
		Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("trigger1")
				.withSchedule(CronScheduleBuilder.cronSchedule("0 46 17 * * ?")).build();
		scheduler.start();
		scheduler.scheduleJob(tarea1, trigger1);
		Thread.sleep(300L * 1000L);
		scheduler.shutdown(true);
	}

	private static final String URL = "ec2-184-73-206-155.compute-1.amazonaws.com:5432/dacbprtaga7o1f";
	private static final String USERNAME = "nhbcdfgbdulfyd";
	private static final String PASSWORD = "8be9eab9ba5530cc1b79809fe9310e3f318c1ecf5ec0ec96150cffc457d37c2c";
	private static Connection conn;

	/**
	 * 
	 * @throws SQLException
	 */

	public static void getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
		}
		conn = DriverManager.getConnection("jdbc:postgresql://" + URL + "?sslmode=require", USERNAME, PASSWORD);

	}

	public static void calcularPuntos() {

	}

	public static void crearMercado() {

	}
}
