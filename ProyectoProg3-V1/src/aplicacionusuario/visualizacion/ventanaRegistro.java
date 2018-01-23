package aplicacionusuario.visualizacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import aplicacionusuario.datos.BD;
import aplicacionusuario.datos.Usuario;

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

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventanaRegistro frame = new ventanaRegistro(null);
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

	public ventanaRegistro(VentanaInicio ventanaanterior) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 150, 524, 303);
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
		btnHasOlvidadoLa.setBackground(new Color(6, 50, 113));
		btnHasOlvidadoLa.setForeground(new Color(255, 255, 255));
		btnHasOlvidadoLa.addActionListener(new ActionListener() {

			/*
			 * LLama al metodo de base de datos que comprueba si existe el mail y devuelve
			 * la contraseña
			 * 
			 * Luego llama al metodo enviarCorreo
			 */

			public void actionPerformed(ActionEvent arg0) {
				String mail = JOptionPane.showInputDialog("Escribe tu mail");
				String pass = BD.olvidarContra(mail);

				enviarCorreo(pass, mail);
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
		btnVolver.setBackground(new Color(6, 50, 113));
		btnVolver.setForeground(new Color(255, 255, 255));
		panel_1.add(btnVolver, "cell 1 0,alignx left,aligny top");
		btnVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaRegistro.this.setVisible(false);
				ventanaanterior.setVisible(true);
			}
		});

		JButton btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.setBackground(new Color(6, 50, 113));
		btnIniciarSesion.setForeground(new Color(255, 255, 255));
		panel_1.add(btnIniciarSesion, "cell 2 0,alignx left,aligny top");
		btnIniciarSesion.addActionListener(new ActionListener() {

			/*
			 * Boton iniciar sesion Comprueba en la base de datos si existe el usuario, si
			 * existe llama a la ventana principal sino mensaje de error
			 * 
			 */

			@Override
			public void actionPerformed(ActionEvent e) {
				String usuario = textField.getText();
				String contraseña = String.valueOf(passwordField.getPassword());
				Usuario user = new Usuario(usuario, contraseña, "");
				Usuario u = BD.buscarUsuario(user);
				if (u != null) {
					VentanaPrincipal vP = new VentanaPrincipal(u);
					vP.setVisible(true);
					ventanaRegistro.this.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(ventanaRegistro.this,
							"Usuario o Contraseña incorrecto. Intentelo de nuevo");
				}

			}

		});
	}

	/**
	 * 
	 */

	private void enviarCorreo(String pass, String mail) {

		try {
			String host = "smtp.gmail.com";
			String user = "ligafantasyflex@gmail.com";
			String contrasenya1 = "candyflex";
			String para = mail;
			String from = user;
			String subject = "Recuperacion de contraseña";
			String messageText = "Su contraseña es: " + "\n" + "	" + pass + "\n" + "\n" + "\n"
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
			JOptionPane.showMessageDialog(ventanaRegistro.this, "Error");
		}

	}

}
