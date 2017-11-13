package visualizacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import datos.ListaJugadores;
import datos.Usuario;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;

public class ventanaRegistro extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private Usuario Usuario;
	private boolean correcto;
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
		Usuario=new Usuario();
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

		JLabel lblContraseña = new JLabel("Contrase\u00F1a");
		lblContraseña.setHorizontalAlignment(SwingConstants.LEFT);
		panel_Mid.add(lblContraseña, "cell 0 1,alignx right,growy");

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_Mid.add(passwordField, "cell 1 1,growx,aligny center");

		JButton btnHasOlvidadoLa = new JButton("\u00BFHas olviado la contrase\u00F1a?");
		btnHasOlvidadoLa.setBackground(new Color(0, 102, 204));
		btnHasOlvidadoLa.setForeground(new Color(255, 255, 255));
		btnHasOlvidadoLa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				File ListaUsuario = new File(File.pathSeparator + "Usuarios.txt");
				String pass = null, email = null;
				try {
					FileReader fr = new FileReader(ListaUsuario);
					BufferedReader bfr = new BufferedReader(fr);
					String linea = bfr.readLine();
					while (linea != null) {
						String[] lineas = linea.split(";");
						pass = lineas[1];
						email = lineas[2];
						System.out.println(email);
						linea = bfr.readLine();
					}
					bfr.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				 correo =JOptionPane.showInputDialog("Indique su correo", "");
				 boolean tieneArroba=false;
				 for(int i = 0; i < correo.length() && !tieneArroba; i++){
				 if(correo.charAt(i)=='@') {
				 tieneArroba=true;
				
				 }
				 if (correo.equals(email)){
				 enviarCorreo(pass);
				 }else{
					 JOptionPane.showMessageDialog(null, "Correo erroneo");
				 }}
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

			@Override
			public void actionPerformed(ActionEvent e) {
				correcto = false;
				ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
				String usuario = textField.getText();
				String contraseña = String.valueOf(passwordField.getPassword());
				File ListaUsuario = new File(File.pathSeparator + "Usuarios.txt");
				String user = null, pass = null, email = null;
				try {
					FileReader fr = new FileReader(ListaUsuario);
					BufferedReader bfr = new BufferedReader(fr);
					String linea = bfr.readLine();
					while (linea != null) {
						String[] lineas = linea.split(";");
						user = lineas[0];
						pass = lineas[1];
						email = lineas[2];
						usuarios.add(new Usuario(user, pass, email));
						linea = bfr.readLine();
					}
					bfr.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				for (int i = 0; i < usuarios.size(); i++) {
					if (usuario.equals(usuarios.get(i).getNombre())) {
						if (contraseña.equals(usuarios.get(i).getContraseña())) {
							Usuario.setNombre(user);
							Usuario.setContraseña(pass);
							Usuario.setEmail(email);
							ventanaPrincipal vP = new ventanaPrincipal(Usuario, listaJugadores);
							vP.setVisible(true);
							ventanaRegistro.this.setVisible(false);
						} else {
							JOptionPane.showMessageDialog(ventanaRegistro.this, "Contraseña incorrecta");
						}
					} else {
						JOptionPane.showMessageDialog(ventanaRegistro.this, "Usuario incorrecto");
					}

				}

			}
		});
	}

	public static void enviarCorreo(String contrasenya) {
		// Recipient's email ID needs to be mentioned.
		String to = correo;

		// Sender's email ID needs to be mentioned
		String from = "ligafantasyflex@gamil.com";

		// Assuming you are sending email from localhost
		String host = "localhost";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Contraseña liga fantasy");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(contrasenya);

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = "file.txt";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			JOptionPane.showMessageDialog(null, "Contraseña enviada");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
