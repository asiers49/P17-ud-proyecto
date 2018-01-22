package aplicacionusuario.visualizacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import aplicacionusuario.datos.BD;
import aplicacionusuario.datos.Jugador;
import aplicacionusuario.datos.Usuario;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	ArrayList<Jugador> equipo = new ArrayList<>();
	static String posicion;
	static JPanel suplentes;
	static JPanel titular;
	static int codigotitular;
	Usuario u;
	BotonJugador botonseleccionado;
	VentanaPrincipal ventana;
	Jugador jtitular;
	ArrayList<Jugador> jSuplentes = new ArrayList<>();

	/**
	 * Create the frame.
	 */
	public ventanaSeleccionarJugadores(ArrayList<Jugador> listaequipo, String Posicion, Usuario user,
			VentanaPrincipal vp, BotonJugador btn) {
		u = user;
		equipo = listaequipo;
		posicion = Posicion;
		ventana = vp;
		botonseleccionado = btn;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 150, 603, 430);
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
		titular.setLayout(new MigLayout("", "[200px][200px][90px,grow]", "[60px][]"));
		panel.add(titular, BorderLayout.NORTH);
		JButton btnVolver = new JButton("Volver");
		contentPane.add(btnVolver, BorderLayout.SOUTH);
		btnVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ventanaSeleccionarJugadores.this.dispose();
				ventana.repaint();

			}
		});
		refresh();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ventana.repaint();
			}
		});
	}

	private void refresh() {
		suplentes.removeAll();
		titular.removeAll();
		JLabel nombre;
		JLabel valor;
		JLabel puntos;
		JLabel puntosJornada;

		if (botonseleccionado.getJugador() != null) {
			nombre = new JLabel();
			nombre.setFont(new Font("Monospaced", Font.PLAIN, 18));
			valor = new JLabel();
			puntos = new JLabel();
			puntosJornada = new JLabel();
			jtitular = botonseleccionado.getJugador();
			System.out.println(jtitular.getNombre());
			nombre.setText(jtitular.getNombre());
			valor.setText("Valor: " + jtitular.getValor());
			puntos.setText("Puntos Totales: " + jtitular.getPuntos());
			puntosJornada.setText("Puntos Jornada: " + jtitular.getPuntosJornada());
			titular.add(nombre, "cell 0 0");
			titular.add(valor, " cell 0 1");
			titular.add(puntos, "cell 1 0");
			titular.add(puntosJornada, "cell 1 1");
			for (Jugador j : equipo) {
				if (j != jtitular && !estaseleccionado(j) && j.getPosicion().equals(posicion)) {
					jSuplentes.add(j);
				}
			}
		} else {
			for (Jugador j : equipo) {
				if (!estaseleccionado(j) && j.getPosicion().equals(posicion)) {
					jSuplentes.add(j);
				}
			}
		}
		for (int i = 0; i < jSuplentes.size(); i++) {
			Jugador j = jSuplentes.get(i);
			nombre = new JLabel();
			nombre.setFont(new Font("Monospaced", Font.PLAIN, 18));
			valor = new JLabel();
			puntos = new JLabel();
			puntosJornada = new JLabel();
			JButton cambiar = new JButton("Seleccionar");
			JButton vender = new JButton("Vender");
			cambiar.setBackground(Color.WHITE);
			nombre.setText(j.getNombre());
			valor.setText("Valor: " + j.getValor());
			puntos.setText("Puntos Totales: " + j.getPuntos());
			puntosJornada.setText("Puntos Jornada: " + j.getPuntosJornada());
			JPanel panel1 = new JPanel();
			panel1.setLayout(new MigLayout("", "[200px][200px][90px]", "[60px][]"));
			panel1.add(nombre, "cell 0 0");
			panel1.add(cambiar, " cell 2 1");
			panel1.add(vender, " cell 2 0");
			panel1.add(valor, "cell 0 1");
			panel1.add(puntos, "cell 1 0");
			panel1.add(puntosJornada, "cell 1 1");
			suplentes.add(panel1, "cell 1 " + i);
			cambiar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					hacerTitular(j);
					refresh();

				}

			});
			vender.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int precio=j.getValor();
					int opcion=JOptionPane.showConfirmDialog(ventanaSeleccionarJugadores.this, "¿Quieres vender a este jugador por "+precio+"?");
						if (opcion==0) {
							BD.venderJugador(u, j, precio);
							equipo.remove(j);
							ventana.lblDinero2.setText(""+(u.getDinero()+precio));
					};
				}
			});
		}
	}

	private boolean estaseleccionado(Jugador j) {
		boolean seleccionado = false;
		for (BotonJugador btn : ventana.botones) {
			if (btn.getJugador() == j) {
				seleccionado = true;
			}
		}

		return seleccionado;

	}

	private void hacerTitular(Jugador j) {
		BD.hacerTitular(u, j.getCod_jugador());
		if (jtitular != null) {
			BD.hacerSuplente(u, jtitular.getCod_jugador());
		}
		jtitular = j;
		for (BotonJugador btn : ventana.botones) {
			if (btn.getPosicion() == botonseleccionado.getPosicion()
					&& btn.getNumero() == botonseleccionado.getNumero()) {
				btn.setJugador(j);
				botonseleccionado.setJugador(j);
			}
		}
	}
}
