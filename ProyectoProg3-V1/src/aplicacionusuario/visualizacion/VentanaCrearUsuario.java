package aplicacionusuario.visualizacion;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import aplicacionusuario.datos.BD;
import aplicacionusuario.datos.Usuario;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class VentanaCrearUsuario extends JFrame {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaInicio vI;
	private JTextField textField_usuario;
	private JPasswordField passwordField;
	private JTextField textField_email;
	private Usuario user;

	/**
	 * Create the frame.
	 * @param listaJugadores 
	 */
	public VentanaCrearUsuario(VentanaInicio ventanaanterior) {
		
		vI = ventanaanterior;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 150, 464, 329);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel_Mid = new JPanel();
		panel_Mid.setBackground(new Color(255, 255, 255));
		contentPane.add(panel_Mid, BorderLayout.CENTER);
		panel_Mid.setLayout(new MigLayout("", "[60px][][200px]", "[15px][25px][25px][25px][25px][25px]"));

		JLabel lbl_Usuario = new JLabel("Usuario");
		panel_Mid.add(lbl_Usuario, "cell 1 1,alignx trailing");

		textField_usuario = new JTextField();
		panel_Mid.add(textField_usuario, "flowx,cell 2 1,growx");
		textField_usuario.setColumns(10);

		JLabel lbl_Password = new JLabel("Password");
		panel_Mid.add(lbl_Password, "cell 1 3,growx");

		passwordField = new JPasswordField();
		panel_Mid.add(passwordField, "cell 2 3,growx");

		JLabel lbl_Email = new JLabel("Email");
		panel_Mid.add(lbl_Email, "cell 1 5,alignx trailing");

		textField_email = new JTextField();
		panel_Mid.add(textField_email, "cell 2 5,growx");
		textField_email.setColumns(10);

		JPanel panel_TOP = new JPanel();
		panel_TOP.setBackground(new Color(255, 255, 255));
		contentPane.add(panel_TOP, BorderLayout.NORTH);

		JLabel lblRegistro = new JLabel("Regristro");
		lblRegistro.setFont(new Font("Monospaced", Font.PLAIN, 29));
		panel_TOP.add(lblRegistro);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new MigLayout("", "[120px][][][][]", "[]"));

		JButton btn_Volver = new JButton("Volver");
		btn_Volver.setBackground(new Color(7, 46, 113));
		btn_Volver.setForeground(new Color(255, 255, 255));
		btn_Volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaCrearUsuario.this.setVisible(false);
				vI.setVisible(true);

			}
		});
		panel.add(btn_Volver, "cell 1 0");

		JButton btn_Registrarse = new JButton("Registrarse");
		btn_Registrarse.setBackground(new Color(7, 46, 113));
		btn_Registrarse.setForeground(new Color(255, 255, 255));
		panel.add(btn_Registrarse, "cell 3 0");
		btn_Registrarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Registra el usuario en la Base de Datos
				String usuario = textField_usuario.getText();
				String password = String.valueOf(passwordField.getPassword());
				String email = textField_email.getText();
				user = new Usuario(usuario, password, email);
				try {
					BD.getConnection();
					BD.nuevoUsuario(user);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				VentanaPrincipal vP =new VentanaPrincipal(user);
				vP.setVisible(true);
				VentanaCrearUsuario.this.setVisible(false);
				}
		});

	}
}