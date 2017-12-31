package visualizacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import datos.Jugador;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

public class ventanaSeleccionarJugadores extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventanaSeleccionarJugadores frame = new ventanaSeleccionarJugadores(null, null);
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
	public ventanaSeleccionarJugadores(ArrayList<Jugador> equipo, String posicion) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 546, 382);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblPosicion = new JLabel(posicion);
		lblPosicion.setFont(new Font("Monospaced", Font.PLAIN, 18));
		contentPane.add(lblPosicion, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[2px]", "[2px]"));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "cell 0 0,alignx right ,aligny top");
	}

}
