package visualizacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import datos.Liga;
import datos.Usuario;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Window.Type;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class VentanaCrearLiga extends JFrame {

	private ventanaPrincipal VP;
	private JPanel contentPane;
	private JTextField textField_nombre;
	private Liga liga1;
	private String nLiga = "";
	double clave;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblNombre;
	private JLabel lblClave;
	private JLabel lblCrearLiga;
	private Usuario user;

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
	public VentanaCrearLiga(ventanaPrincipal ventanaanterior, Usuario user1) {
		user=user1;
		setFont(new Font("Monospaced", Font.PLAIN, 16));
		setType(Type.UTILITY);
		setTitle("Crear Liga");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[100px][grow][]", "[][][][][][][][]"));

		lblCrearLiga = new JLabel("CREAR LIGA:");
		lblCrearLiga.setFont(new Font("Monospaced", Font.PLAIN, 16));
		contentPane.add(lblCrearLiga, "cell 1 0");

		JLabel lblNombreDeLa = new JLabel("Nombre de la comunidad:");
		lblNombreDeLa.setFont(new Font("Monospaced", Font.PLAIN, 16));
		contentPane.add(lblNombreDeLa, "cell 1 1 2 1");

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBackground(Color.BLUE);
		btnAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nLiga = textField_nombre.getText();
				clave = Math.random() * 999999 + 1;
				Liga liga = new Liga(nLiga, clave);
				user1.setLiga(liga);
				createFile();
				ventanaPrincipal vP=new ventanaPrincipal(user, null);
				vP.setVisible(true);
				VentanaCrearLiga.this.setVisible(false);

			}
		});

		textField_nombre = new JTextField();
		contentPane.add(textField_nombre, "cell 1 2,growx");
		textField_nombre.setColumns(10);

		JLabel lblAadirLiga = new JLabel("A\u00F1adir liga:");
		lblAadirLiga.setFont(new Font("Monospaced", Font.PLAIN, 16));
		contentPane.add(lblAadirLiga, "cell 1 3");

		lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Monospaced", Font.PLAIN, 16));
		contentPane.add(lblNombre, "cell 0 4,alignx trailing");

		textField_1 = new JTextField();
		contentPane.add(textField_1, "cell 1 4,growx");
		textField_1.setColumns(10);

		lblClave = new JLabel("Clave:");
		lblClave.setFont(new Font("Monospaced", Font.PLAIN, 16));
		contentPane.add(lblClave, "cell 0 5,alignx trailing");

		textField_2 = new JTextField();
		contentPane.add(textField_2, "cell 1 5,growx");
		textField_2.setColumns(10);
		btnAceptar.setFont(new Font("Monospaced", Font.PLAIN, 16));
		contentPane.add(btnAceptar, "flowx,cell 1 7");

		JButton btnNewButton = new JButton("Volver");
		btnNewButton.setBackground(Color.BLUE);
		btnNewButton.setFont(new Font("Monospaced", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VP.setVisible(true);
				VentanaCrearLiga.this.setVisible(false);
			}
		});
		contentPane.add(btnNewButton, "cell 1 7");
	}

	public void createFile() {

		File usuarios = new File(File.pathSeparator + "Usuarios.txt");
		try {
			FileReader fr = new FileReader(usuarios);
			BufferedReader bfr = new BufferedReader(fr);
			FileWriter fw = new FileWriter(usuarios, true);
			String linea = bfr.readLine();
		
			while (linea != null) {
				String[] lineas = linea.split(";");
				System.out.println(lineas[0]+" ,"+user.getNombre());
				if (lineas[0].equals(user.getNombre())) { //
					fw.write(";"+nLiga + ";" + clave);
					fw.flush();
					fw.close();
				}
				linea = bfr.readLine();
			}

			bfr.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
