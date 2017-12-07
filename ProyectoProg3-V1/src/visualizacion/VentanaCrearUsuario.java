package visualizacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import datos.BD;
import datos.ListaJugadores;
import datos.Usuario;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCrearUsuario frame = new VentanaCrearUsuario(null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param listaJugadores 
	 */
	public VentanaCrearUsuario(VentanaInicio ventanaanterior, ListaJugadores listaJugadores) {
		
		vI = ventanaanterior;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 329);
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
		btn_Volver.setBackground(new Color(0, 102, 204));
		btn_Volver.setForeground(new Color(255, 255, 255));
		btn_Volver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaCrearUsuario.this.setVisible(false);
				vI.setVisible(true);

			}
		});
		panel.add(btn_Volver, "cell 1 0");

		JButton btn_Registrarse = new JButton("Registrarse");
		btn_Registrarse.setBackground(new Color(0, 102, 204));
		btn_Registrarse.setForeground(new Color(255, 255, 255));
		panel.add(btn_Registrarse, "cell 3 0");
		btn_Registrarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String usuario = textField_usuario.getText();
				String password = String.valueOf(passwordField.getPassword());
				String email = textField_email.getText();
				System.out.println(email);
				user = new Usuario(0,usuario, password, email);
				ventanaPrincipal vP =new ventanaPrincipal(user, listaJugadores);
				vP.setVisible(true);
				VentanaCrearUsuario.this.setVisible(false);
				try {
					BD.getConnection();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				BD.nuevoUsuario(user);
				
			}
		});

	}
public void aBD(){
		
		File usuarios=new File(File.pathSeparator+"Usuarios.txt");
		try {
			FileWriter fw=new FileWriter(usuarios,true);
			fw.write(user.getNombre()+";"+user.getContraseña()+";"+user.getEmail());
			fw.write("\n");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}