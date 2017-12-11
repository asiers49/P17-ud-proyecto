package visualizacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import datos.ListaJugadores;
import datos.Usuario;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;

@SuppressWarnings("serial")
public class ventanaRegistro extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private Usuario Usuario;
	private static String correo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventanaRegistro frame = new ventanaRegistro(null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ventanaRegistro(VentanaInicio ventanaanterior, ListaJugadores listaJugadores) {		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 524, 303);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 25));
		setContentPane(contentPane);

		JPanel panel_Mid = new JPanel();
		panel_Mid.setBackground(new Color(255, 255, 255));
		contentPane.add(panel_Mid, BorderLayout.CENTER);
		panel_Mid.setLayout(new MigLayout("", "[125px][344px][100px]", "[30px][30px][30px][23px]"));

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_Mid.add(lblUsuario, "cell 0 0,grow");

		textField = new JTextField();
		panel_Mid.add(textField, "cell 1 0,grow");
		textField.setColumns(10);

		JLabel lblContraseņa = new JLabel("Contrase\u00F1a");
		lblContraseņa.setHorizontalAlignment(SwingConstants.LEFT);
		panel_Mid.add(lblContraseņa, "cell 0 1,alignx right,growy");

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_Mid.add(passwordField, "cell 1 1,growx,aligny center");

		JButton btnHasOlvidadoLa = new JButton("\u00BFHas olviado la contrase\u00F1a?");
		btnHasOlvidadoLa.setBackground(new Color(0, 102, 204));
		btnHasOlvidadoLa.setForeground(new Color(255, 255, 255));
		btnHasOlvidadoLa.addActionListener(new ActionListener() {
			
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			
			public void actionPerformed(ActionEvent arg0) {

				File ListaUsuario = new File(File.pathSeparator + "Usuarios.txt");
				String pass = null, email = null;
				ArrayList<String> listamails = new ArrayList<>();
				try {
					FileReader fr = new FileReader(ListaUsuario);
					BufferedReader bfr = new BufferedReader(fr);
					String linea = bfr.readLine();
					while (linea != null) {
						String[] lineas = linea.split(";");
						pass = lineas[1];
						email = lineas[2];
						listamails.add(email);
						System.out.println(email);
						linea = bfr.readLine();
					}
					bfr.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				correo = JOptionPane.showInputDialog("Indique su correo", "");
				boolean tieneArroba = false;
				boolean correoexiste = false;
				for (int i = 0; i < correo.length() && !tieneArroba; i++) {
					if (correo.charAt(i) == '@') {
						tieneArroba = true;

					}
				}
				for (int j = 0; j < listamails.size(); j++) {
					if (correo.equals(listamails.get(j))) {
						correoexiste = true;
					}
				}
				if (correoexiste) {
					JOptionPane.showMessageDialog(null, "Contraseņa enviada");
					enviarCorreo(pass, ventanaRegistro.this);

				} else {
					JOptionPane.showMessageDialog(null, "Correo erroneo");
				}

			}
		});
		panel_Mid.add(btnHasOlvidadoLa, "cell 1 3,alignx center,aligny center");

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblIniciarSesion = new JLabel("INICIAR SESION");
		lblIniciarSesion.setFont(new Font("Monospaced", Font.PLAIN, 29));
		panel.add(lblIniciarSesion);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new MigLayout("", "[180px][63px][95px][150]", "[23px]"));

		JButton btnVolver = new JButton("Volver");
		btnVolver.setBackground(new Color(0, 102, 204));
		btnVolver.setForeground(new Color(255, 255, 255));
		panel_1.add(btnVolver, "cell 1 0,alignx left,aligny top");
		btnVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ventanaRegistro.this.setVisible(false);
				ventanaanterior.setVisible(true);
			}
		});

		JButton btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.setBackground(new Color(0, 102, 204));
		btnIniciarSesion.setForeground(new Color(255, 255, 255));
		panel_1.add(btnIniciarSesion, "cell 2 0,alignx left,aligny top");
		btnIniciarSesion.addActionListener(new ActionListener() {
			
			/*
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
				String usuario = textField.getText();
				String contraseņa = String.valueOf(passwordField.getPassword());
				Usuario user=new Usuario(usuario, contraseņa, "");
				
				VentanaPrincipal vP = new VentanaPrincipal(Usuario, listaJugadores);
				vP.setVisible(true);
				ventanaRegistro.this.setVisible(false);
					

				}

			
		});
	}
	
	/**
	 * 
	 */
	
	public static void enviarCorreo(String pass, ventanaRegistro vR) {

		try {
			String host = "smtp.gmail.com";
			String user = "ligafantasyflex@gmail.com";
			String contrasenya1 = "candyflex";
			String para = "anderjarauta@gmail.com";
			String from = user;
			String subject = "Recuperacion de contraseņa";
			String messageText = "Su contraseņa es: " + "\n" + "	" + pass + "\n" + "\n" + "\n"
					+ "El Equipo de LigaFantasyFlex";
			boolean sessionDebug = false;

			Properties props = System.getProperties();

			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.required", "true");
			Session mailSession = Session.getDefaultInstance(props, null);
			mailSession.setDebug(sessionDebug);
			Message msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(para) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setText(messageText);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(host, user, contrasenya1);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(vR, "Error");
		}

	}
}
