package visualizacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import datos.BD;
import datos.Jugador;
import datos.Usuario;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;

public class ventanaSeleccionarJugadores extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static ArrayList<Jugador> equipo;
	static String posicion;
	static JPanel suplentes;
	static JPanel titular;
	static int codigotitular;
	Usuario u;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventanaSeleccionarJugadores frame = new ventanaSeleccionarJugadores(null, null, null);
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
	public ventanaSeleccionarJugadores(ArrayList<Jugador> listaequipo, String Posicion, Usuario user) {
		u=user;
		equipo = listaequipo;
		posicion = Posicion;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 430);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 25));
		setContentPane(contentPane);
		JLabel lblPosicion = new JLabel(posicion);
		lblPosicion.setFont(new Font("Monospaced", Font.PLAIN, 18));
		contentPane.add(lblPosicion, BorderLayout.NORTH);
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.CENTER);
		BorderLayout bl_panel = new BorderLayout();
		bl_panel.setVgap(35);
		bl_panel.setHgap(50);
		panel.setLayout(bl_panel);
		suplentes = new JPanel();
		suplentes.setBackground(Color.WHITE);
		suplentes.setLayout(new MigLayout("", "[grow]", "[][][][][][][]"));
		JScrollPane scrollPane = new JScrollPane(suplentes);
		scrollPane.setViewportBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane, BorderLayout.CENTER);
		titular = new JPanel();
		titular.setLayout(new MigLayout("", "[150px][200px][90px,grow]", "[60px][]"));
		panel.add(titular, BorderLayout.NORTH);
		JButton btnVolver = new JButton("Volver");
		contentPane.add(btnVolver, BorderLayout.SOUTH);
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ventanaSeleccionarJugadores.this.dispose();
			}
		});
		refresh();
	}

	private void refresh() {
		suplentes.removeAll();
		titular.removeAll();
		for (int i = 0; i < equipo.size(); i++) {
			JLabel nombre = new JLabel();
			nombre.setFont(new Font("Monospaced", Font.PLAIN, 18));
			JButton cambiar = new JButton("Cambiar");
			JLabel valor = new JLabel();
			if (equipo.get(i).getPosicion().equals(posicion)) {
				Jugador j = equipo.get(i);
				System.out.println(j.getNombre()+" "+j.isTitular());
				nombre.setText(j.getNombre());
				valor.setText("Valor: "+j.getValor());
				if (j.isTitular()) {
					titular.add(nombre, "cell 0 0");
					titular.add(valor, " cell 0 1");
					codigotitular=j.getCod_jugador();
				} else {
					JPanel panel1 = new JPanel();
					panel1.setLayout(new MigLayout("", "[150px][200px][90px,grow]", "[60px][]"));
					panel1.add(nombre, "cell 0 0");
					panel1.add(cambiar, " cell 2 1");
					panel1.add(valor, "cell 0 1");
					suplentes.add(panel1, "cell 1 " + i);
					cambiar.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							for (int k = 0; k < equipo.size(); k++) {
								if (equipo.get(k) == j) {
									System.out.println(equipo.get(k).getNombre() + " titular");
									BD.hacerTitular(u, equipo.get(k).getCod_jugador());
									equipo.get(k).setTitular(true);
								}else if (codigotitular==equipo.get(k).getCod_jugador()){
									equipo.get(k).setTitular(false);
									System.out.println(equipo.get(k).getNombre() + " suplente");
								}
								
									refresh();
									ventanaSeleccionarJugadores.this.repaint();
								}
						}
					});
				}
			}

		}
	
	}
}
