package aplicacionusuario.visualizacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import aplicacionusuario.datos.BD;
import aplicacionusuario.datos.Jugador;
import aplicacionusuario.datos.Usuario;
import net.miginfocom.swing.MigLayout;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 4429640112774618601L;
	private JPanel contentPane;
	Usuario user;
	private JPanel panel_Top;
	private JLabel lblNomUsuario2;
	private JPanel panel_User;
	private JLabel lblLigaFantasyFlex;

	private ArrayList<Jugador> equipo;
	private JLabel lblNomUsuario;
	private JLabel lblNomLiga;
	private JLabel lblDinero;
	private JLabel lblNomLiga2;
	JLabel lblDinero2;
	private JTabbedPane tabbedPane;
	private JPanel panel;
	ArrayList<BotonJugador> botones;
	private JButton cerrarsesion;

	/**
	 * Create the frame.
	 * 
	 * @param user1
	 *            The user that has logged in
	 * @param listaJugadores
	 */
	public VentanaPrincipal(Usuario user1) {

		/*
		 * Actualizamos/Inicializamos variables
		 */

		equipo = new ArrayList<>();
		user = user1;

		/*
		 * Creates Frame
		 */

		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 150, 900, 750);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(3, 3, 3, 3));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();
		panel.setBackground(new Color(29, 101, 171));
		contentPane.add(panel, BorderLayout.CENTER);
		panel_Top = new JPanel();
		panel_Top.setBorder(null);
		panel_Top.setBackground(new Color(29, 101, 171));
		contentPane.add(panel_Top, BorderLayout.NORTH);
		panel_Top.setLayout(new MigLayout("", "[200][grow][]", "[100px,grow]"));
		cerrarsesion = new JButton("Cerrar Sesion");
		cerrarsesion.setBackground(new Color(229, 124, 80));
		cerrarsesion.setFont(new Font("Monospaced", Font.PLAIN, 18));
		lblLigaFantasyFlex = new JLabel("LIGA FANTASY FLEX");
		lblLigaFantasyFlex.setFont(new Font("Monospaced", Font.BOLD, 38));
		panel_Top.add(lblLigaFantasyFlex, "cell 1 0,alignx center,aligny center");
		panel_Top.add(cerrarsesion, "cell 2 0,alignx center,aligny center");
		cerrarsesion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				VentanaInicio a = new VentanaInicio();
				Usuario u = new Usuario();
				u = null;
				a.setVisible(true);
				VentanaPrincipal.this.dispose();
			}
		});

		/*
		 * Panel User
		 */

		panel_User = new JPanel();
		panel_User.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_User.setBackground(new Color(29, 101, 171));
		panel_Top.add(panel_User, "cell 0 0,grow");
		panel_User.setLayout(new MigLayout("", "[90px][]", "[22px][][]"));

		lblNomUsuario = new JLabel("Nombre:");
		lblNomUsuario.setFont(new Font("Monospaced", Font.BOLD, 16));
		panel_User.add(lblNomUsuario, "cell 0 0");

		lblNomUsuario2 = new JLabel("New label");
		lblNomUsuario2.setFont(new Font("Monospaced", Font.PLAIN, 16));
		lblNomUsuario2.setText(user.getNombre());
		panel_User.add(lblNomUsuario2, "cell 1 0,alignx left,aligny top");

		lblNomLiga = new JLabel("Liga:");
		lblNomLiga.setFont(new Font("Monospaced", Font.BOLD, 16));
		panel_User.add(lblNomLiga, "cell 0 1");

		lblDinero = new JLabel("Dinero:");
		lblDinero.setFont(new Font("Monospaced", Font.BOLD, 16));
		panel_User.add(lblDinero, "cell 0 2");

		lblDinero2 = new JLabel("" + user.getDinero());
		lblDinero2.setFont(new Font("Monospaced", Font.PLAIN, 16));
		panel_User.add(lblDinero2, "cell 1 2");
		lblNomLiga2 = new JLabel();
		lblNomLiga2.setFont(new Font("Monospaced", Font.PLAIN, 16));
		panel.setLayout(new BorderLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		panel.add(tabbedPane);
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setBackground(new Color(7, 46, 113));
		tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		tabbedPane.setFont(new Font("Monospaced", Font.BOLD, 17));
		tabbedPane.add("Mi Liga", new PanelLigas());
		tabbedPane.add("Mi Equipo", new PanelMiEquipo());
		tabbedPane.add("Mercado", new PanelMercado());
		JLabel lab = new JLabel("Mi Liga");
		lab.setPreferredSize(new Dimension(160, 40));
		lab.setFont(new Font("Monospaced", Font.BOLD, 16));
		lab.setForeground(new Color(255, 255, 255));
		JLabel lab2 = new JLabel("Mi Equipo");
		lab2.setPreferredSize(new Dimension(160, 40));
		lab2.setFont(new Font("Monospaced", Font.BOLD, 16));
		lab2.setForeground(new Color(255, 255, 255));
		JLabel lab3 = new JLabel("Mercado");
		lab3.setPreferredSize(new Dimension(160, 40));
		lab3.setFont(new Font("Monospaced", Font.BOLD, 16));
		lab3.setForeground(new Color(255, 255, 255));
		tabbedPane.setTabComponentAt(0, lab);
		tabbedPane.setTabComponentAt(1, lab2);
		tabbedPane.setTabComponentAt(2, lab3);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				BD.cerrarConnection();
				Main.guardaProperties(user);

			}

		});

	}

	private class PanelLigas extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5817000428864655052L;
		private ArrayList<Usuario> listaUsuarios;
		private JTextPane txtpnBienvenidos;

		public PanelLigas() {

			PanelLigas.this.setBackground(new Color(255, 255, 255));
			PanelLigas.this.setLayout(new MigLayout("", "[60px][grow][]", "[100px][grow][350][]"));
			PanelLigas.this.setVisible(false);

			if (user.getLiga() == null) {

				txtpnBienvenidos = new JTextPane();
				txtpnBienvenidos.setFont(new Font("Monospaced", Font.PLAIN, 16));
				txtpnBienvenidos.setEditable(false);
				txtpnBienvenidos.setText(
						"Bienvenidos a la Liga Fantasy Flex. Para ingresar o crear una liga pulse el boton de abajo.\r\nUna vez que ya tenga una liga se le asignara un equipo con el que buscara ganar su Liga.\r\n\r\nDesde el equipo detras de esta Liga Fantasy esperemos que disfrute.\r\n\r\n\t\t\t\t\t\t\t\t\tMucha suerte!!!");
				PanelLigas.this.add(txtpnBienvenidos, "cell 1 1,alignx center,grow");
				JButton anadirLiga1 = new JButton("Añadir Ligas");
				anadirLiga1.setFont(new Font("Monospaced", Font.PLAIN, 22));
				anadirLiga1.setBounds(0, 0, 300, 150);
				anadirLiga1.setBackground(new Color(255, 255, 255));
				anadirLiga1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						VentanaPrincipal.this.setVisible(false);
						VentanaCrearLiga vCL = new VentanaCrearLiga(VentanaPrincipal.this, user);
						vCL.setVisible(true);

					}
				});
				PanelLigas.this.add(anadirLiga1, "cell 1 2,alignx center,aligny top");

			} else {
				listaUsuarios = BD.sacarUsuariosLiga(user);
				PanelLigas.this.setLayout(new MigLayout("", "[30px][650px][grow]", "[45px][][][][]"));
				JPanel paneltitulo = new JPanel();
				paneltitulo.setLayout(new MigLayout());
				paneltitulo.setBackground(Color.white);
				PanelLigas.this.add(paneltitulo, "cell 1 1");
				JLabel lbltitulo = new JLabel("LIGA " + user.getLiga().getNombre());
				lbltitulo.setForeground(new Color(229, 124, 8));
				lbltitulo.setFont(new Font("Monospaced", Font.BOLD, 24));
				paneltitulo.add(lbltitulo);
				Collections.sort(listaUsuarios, new Comparator<Usuario>() {
					public int compare(Usuario u1, Usuario u2) {
						Integer i1 = new Integer(u1.getPuntos());
						Integer i2 = new Integer(u2.getPuntos());
						return i2.compareTo(i1);
					}
				});
				for (int i = 0; i < listaUsuarios.size(); i++) {
					JPanel panel1 = new JPanel();
					panel1.setLayout(new MigLayout("", "[100px][400px]", "[70px]"));
					panel1.setBackground(new Color(13, 135, 188));
					PanelLigas.this.add(panel1, "cell  1 " + (i + 2) + " ");
					JPanel panel_Jugador = new JPanel();
					panel_Jugador.setBackground(new Color(13, 135, 188));
					panel1.add(panel_Jugador, "cell 1 0, alignx center, aligny center");
					panel_Jugador.setLayout(new MigLayout("", "[50px][250px][50px][100px]", "[70px]"));
					JLabel labelnom = new JLabel(listaUsuarios.get(i).getNombre());
					labelnom.setFont(new Font("Monospaced", Font.PLAIN, 18));
					panel_Jugador.add(labelnom, "cell 1 0,alignx left, aligny center");
					JLabel labelpuntos = new JLabel("Puntos Totales: " + listaUsuarios.get(i).getPuntos());
					labelpuntos.setFont(new Font("Monospaced", Font.PLAIN, 18));
					panel_Jugador.add(labelpuntos, "cell 3 0, grow");
					JPanel panelpos = new JPanel();
					panelpos.setLayout(new MigLayout("", "[50px]", "[70px]"));
					panelpos.setBackground(new Color(229, 124, 80));
					JLabel lblPos1 = new JLabel("" + (i + 1) + ")");
					lblPos1.setFont(new Font("Monospaced", Font.PLAIN, 22));
					lblPos1.setHorizontalAlignment(SwingConstants.RIGHT);
					panelpos.add(lblPos1, "cell 0 0,alignx left, aligny center");
					panel1.add(panelpos, "cell 0 0,alignx left,aligny center");
				}
				lblNomLiga2.setText(user.getLiga().getNombre());
				panel_User.add(lblNomLiga2, "cell 1 1");
			}

		}
	}

	/**
	 * 
	 * @author Asier Salazar
	 *
	 */

	private class PanelMiEquipo extends JPanel {

		private static final long serialVersionUID = 1605981510261014903L; // FALTA
		private JPanel panel_Portero;
		private JPanel panel_Defensa;
		private JPanel panel_Centro;
		private JPanel panel_Delanteros;
		private JLabel lblEsteEsTu;

		public PanelMiEquipo() {
			PanelMiEquipo.this.setBackground(new Color(255, 255, 255));
			PanelMiEquipo.this
					.setLayout(new MigLayout("", "[grow]", "[50px][100px][100px,grow][100px,grow][100px,grow]"));
			equipo = BD.sacarEquipo(user);
			botones = new ArrayList<>();
			lblEsteEsTu = new JLabel("Este es tu Equipo");
			lblEsteEsTu.setFont(new Font("Monospaced", Font.PLAIN, 28));
			PanelMiEquipo.this.add(lblEsteEsTu, "cell 0 0");
			panel_Portero = new JPanel();
			PanelMiEquipo.this.add(panel_Portero, "cell 0 1,alignx center,aligny center");
			panel_Portero.setLayout(new BorderLayout(0, 0));
			panel_Portero.setBackground(Color.WHITE);
			panel_Defensa = new JPanel();
			PanelMiEquipo.this.add(panel_Defensa, "cell 0 2,alignx center,aligny center");
			panel_Defensa.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel_Defensa.setBackground(Color.WHITE);
			panel_Centro = new JPanel();
			PanelMiEquipo.this.add(panel_Centro, "cell 0 3,growx,aligny center");
			panel_Centro.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel_Centro.setBackground(Color.WHITE);
			panel_Delanteros = new JPanel();
			PanelMiEquipo.this.add(panel_Delanteros, "cell 0 4,growx,aligny center");
			panel_Delanteros.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel_Delanteros.setBackground(Color.WHITE);

			for (int i = 0; i < 11; i++) {
				BotonJugador btn = new BotonJugador(null, "");
				btn.setBackground(new Color(13, 135, 188));
				btn.setFont(new Font("Mponospaced", Font.PLAIN, 17));
				btn.setForeground(Color.WHITE);
				boolean seleccionado = false;
				if (i == 0) {
					btn.setPosicionNumero("Portero", 1);
					panel_Portero.add(btn);
					for (Jugador j : equipo) {
						if (j.getPosicion().equals("Portero") && j.isTitular()) {
							for (BotonJugador boton : botones) {
								if (boton.getJugador() == j) {
									seleccionado = true;
								}
							}
							if (!seleccionado) {
								btn.setJugador(j);
							}
						}
					}
				} else if (i < 5) {
					btn.setPosicionNumero("Defensa", i);
					for (Jugador j : equipo) {
						if (j.getPosicion().equals("Defensa") && j.isTitular()) {
							for (BotonJugador boton : botones) {
								if (boton.getJugador() == j) {
									seleccionado = true;
								}
							}
							if (!seleccionado) {
								btn.setJugador(j);
							}
						}
					}
					panel_Defensa.add(btn);
				} else if (i < 8) {
					btn.setPosicionNumero("Mediocentro", (i - 4));
					for (Jugador j : equipo) {
						if (j.getPosicion().equals("Mediocentro") && j.isTitular()) {
							for (BotonJugador boton : botones) {
								if (boton.getJugador() == j) {
									seleccionado = true;
								}
							}
							if (!seleccionado) {
								btn.setJugador(j);
							}
						}
					}
					panel_Centro.add(btn);
				} else if (i < 11) {
					btn.setPosicionNumero("Delantero", (i - 7));
					for (Jugador j : equipo) {
						if (j.getPosicion().equals("Delantero") && j.isTitular()) {
							for (BotonJugador boton : botones) {
								if (boton.getJugador() == j) {
									seleccionado = true;
								}
							}
							if (!seleccionado) {
								btn.setJugador(j);
							}
						}
					}
					panel_Delanteros.add(btn);
				}
				btn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub

						ventanaSeleccionarJugadores vSJ = new ventanaSeleccionarJugadores(equipo, btn.getPosicion(),
								user, VentanaPrincipal.this, btn);
						vSJ.setVisible(true);

					}
				});
				botones.add(btn);
			}

		}
	}

	/**
	 * 
	 * @author Asier Salazar
	 *
	 */

	private class PanelMercado extends JPanel {

		private static final long serialVersionUID = 1L;
		private ArrayList<Jugador> jugadoresmercado;

		public PanelMercado() {
			jugadoresmercado = BD.actualizarMercado(user.getLiga());
			PanelMercado.this.setBackground(new Color(255, 255, 255));
			PanelMercado.this.setLayout(new BorderLayout(35, 20));
			JLabel nombre;
			JLabel valor;
			JLabel puntos;
			JLabel posicion;
			JPanel listajugadores = new JPanel();
			listajugadores.setLayout(new MigLayout("", "[75px][grow]", "[][][][][][][]"));
			listajugadores.setBackground(new Color(255, 255, 255));
			JScrollPane scrollPane = new JScrollPane(listajugadores);
			scrollPane.setViewportBorder(null);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			PanelMercado.this.add(scrollPane, BorderLayout.CENTER);
			for (int i = 0; i < jugadoresmercado.size(); i++) {
				Jugador j = jugadoresmercado.get(i);
				nombre = new JLabel();
				nombre.setFont(new Font("Monospaced", Font.PLAIN, 20));
				valor = new JLabel();
				puntos = new JLabel();
				posicion = new JLabel();
				JButton fichar = new JButton("Comprar");
				fichar.setBackground(Color.WHITE);
				nombre.setText(j.getNombre());
				valor.setText("Valor: " + j.getValor());
				puntos.setText("Puntos Totales: " + j.getPuntos());
				posicion.setText("Posicion: " + j.getPosicion());
				JPanel PanelJugador = new JPanel();
				PanelJugador.setBackground(new Color(2, 121, 188));
				PanelJugador.setLayout(new MigLayout("", "[200px][200px][90px]", "[60px][]"));
				PanelJugador.add(nombre, "cell 0 0");
				PanelJugador.add(fichar, " cell 2 1");
				PanelJugador.add(valor, "cell 0 1");
				PanelJugador.add(puntos, "cell 1 0");
				PanelJugador.add(posicion, "cell 1 1");
				listajugadores.add(PanelJugador, "cell 1 " + i);
				fichar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String s = JOptionPane.showInputDialog("Escriba su Puja");
						int precio = Integer.parseInt(s);
						if (user.getDinero() < precio) {
							JOptionPane.showMessageDialog(VentanaPrincipal.this,
									"No tienes suficiente dinero para hacer esta puja");
						} else if (precio >= j.getValor()) {
							BD.hacerPuja(user, j, precio);
							JLabel confirmacion = new JLabel(" Puja hecha: " + precio);
							confirmacion.setFont(new Font("Monospaced", Font.ITALIC, 12));
							PanelJugador.add(confirmacion, "cell 2 0");
						} else {
							JOptionPane.showInternalMessageDialog(VentanaPrincipal.this,
									"La puja tiene que ser como minimo igual al valor del jugador");
						}
					}
				});
			}

		}
	}
}
