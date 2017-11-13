package visualizacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import datos.ListaJugadores;
import datos.Usuario;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLabel;

public class VentanaInicio extends JFrame {

	private JPanel contentPane;
	private ListaJugadores listaJugadores;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInicio frame = new VentanaInicio();
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
	public VentanaInicio() {
		listaJugadores=new ListaJugadores();
//		listaJugadores.actualizar();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 457, 229);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(50, 50));
		
		JPanel panel_Top = new JPanel();
		panel_Top.setBackground(Color.WHITE);
		panel.add(panel_Top, BorderLayout.NORTH);
		panel_Top.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("LIGA FANTASY FLEX");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Monospaced", Font.PLAIN, 35));
		panel_Top.add(lblNewLabel, BorderLayout.SOUTH);
		
		JPanel panel_MID = new JPanel();
		panel_MID.setBackground(Color.WHITE);
		panel.add(panel_MID, BorderLayout.CENTER);
		panel_MID.setLayout(new MigLayout("", "[100px][][35px][][95px][100px]", "[23px][][]"));
		
		JButton btnIniSesison = new JButton("Iniciar Sesion");
		btnIniSesison.setForeground(Color.WHITE);
		btnIniSesison.setBackground(new Color(0, 102, 204));
		btnIniSesison.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaInicio.this.setVisible(false);
				ventanaRegistro vR=new ventanaRegistro(VentanaInicio.this, listaJugadores);
				vR.setVisible(true);
				
			}
		});
		panel_MID.add(btnIniSesison, "cell 1 1,grow");
		
		JButton btnRegistrar = new JButton("Registrarse");
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setBackground(new Color(0, 102, 204));
		panel_MID.add(btnRegistrar, "cell 4 1,grow");
		btnRegistrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				VentanaInicio.this.setVisible(false);
				VentanaCrearUsuario vCr=new VentanaCrearUsuario(VentanaInicio.this, listaJugadores);
				vCr.setVisible(true);
			}
		});
		
			
	}
	
	
}
