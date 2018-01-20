package aplicacionusuario.visualizacion;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import aplicacionusuario.datos.BD;
import aplicacionusuario.datos.Liga;
import aplicacionusuario.datos.Usuario;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class VentanaCrearLiga extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VentanaPrincipal VP;
	private JPanel contentPane;
	private JTextField textField_nombre;
	double clave;
	private JTextField textField_nombre2;
	private JTextField textField_Password;
	private JLabel lblNombre;
	private JLabel lblClave;
	private JLabel lblCrearLiga;
	private Usuario user;
	private JSeparator separator;
	private JButton btnAceptar1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCrearLiga frame = new VentanaCrearLiga(null, null);
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
	public VentanaCrearLiga(VentanaPrincipal ventanaanterior, Usuario user1) { // Cuando se crea una liga se inicia la
		user=user1;																		// base de datos.
		setFont(new Font("Monospaced", Font.PLAIN, 16));
		setType(Type.UTILITY);
		setTitle("Crear Liga");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 442);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[100px][300px][]", "[30px][15px][20px][15px][30px][10px][15px][30px][15px][20px][15px][20px][15px][30px][30px]"));

		lblCrearLiga = new JLabel("CREAR UNA  LIGA:");
		lblCrearLiga.setFont(new Font("Monospaced", Font.PLAIN, 16));
		contentPane.add(lblCrearLiga, "cell 1 0");

		JLabel lblNombreDeLa = new JLabel("Nombre:");
		lblNombreDeLa.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNombreDeLa.setFont(new Font("Monospaced", Font.PLAIN, 16));
		contentPane.add(lblNombreDeLa, "cell 0 2,alignx right");

		textField_nombre = new JTextField();
		contentPane.add(textField_nombre, "cell 1 2,growx");
		textField_nombre.setColumns(10);

		btnAceptar1 = new JButton("Aceptar");
		btnAceptar1.setForeground(Color.WHITE);
		btnAceptar1.setFont(new Font("Monospaced", Font.PLAIN, 16));
		btnAceptar1.setBackground(new Color(0, 102, 204));
		contentPane.add(btnAceptar1, "cell 1 4");
		btnAceptar1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nombre=textField_nombre.getText();
				JOptionPane.showMessageDialog(VentanaCrearLiga.this, "La liga se esta creando");
				BD.crearliga(user1, nombre);
				VentanaPrincipal vP = new VentanaPrincipal(user);
				vP.setVisible(true);
				VentanaCrearLiga.this.setVisible(false);
			}
		});

		separator = new JSeparator();
		contentPane.add(separator, "cell 1 5");

		JButton btnAceptar2 = new JButton("Aceptar");
		btnAceptar2.setForeground(Color.WHITE);
		btnAceptar2.setBackground(new Color(0, 102, 204));
		contentPane.add(btnAceptar2, "flowx,cell 1 13");
		btnAceptar2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String nombre=textField_nombre2.getText();
				String clave=textField_Password.getText();
				BD.unirseaLiga(user1, nombre, clave);
				VentanaPrincipal vP = new VentanaPrincipal(user);
				vP.setVisible(true);
				VentanaCrearLiga.this.setVisible(false);

			}
		});

		JLabel lblAadirLiga = new JLabel("UNIRSE A UNA LIGA:");
		lblAadirLiga.setFont(new Font("Monospaced", Font.PLAIN, 16));
		contentPane.add(lblAadirLiga, "cell 1 7");

		lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Monospaced", Font.PLAIN, 16));
		contentPane.add(lblNombre, "cell 0 9,alignx trailing");

		textField_nombre2 = new JTextField();
		contentPane.add(textField_nombre2, "cell 1 9,growx");
		textField_nombre2.setColumns(10);

		lblClave = new JLabel("Clave:");
		lblClave.setFont(new Font("Monospaced", Font.PLAIN, 16));
		contentPane.add(lblClave, "cell 0 11,alignx trailing");

		textField_Password = new JTextField();
		contentPane.add(textField_Password, "cell 1 11,growx");
		textField_Password.setColumns(10);
		btnAceptar2.setFont(new Font("Monospaced", Font.PLAIN, 16));
		
		JButton btnNewButton = new JButton("Volver");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(new Color(0, 102, 204));
		btnNewButton.setFont(new Font("Monospaced", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VP.setVisible(true);
				VentanaCrearLiga.this.setVisible(false);
			}
		});
		contentPane.add(btnNewButton, "cell 1 13");
	}

}
