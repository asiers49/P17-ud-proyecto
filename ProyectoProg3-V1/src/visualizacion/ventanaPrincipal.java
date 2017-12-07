package visualizacion;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import datos.ListaJugadores;
import datos.Usuario;
import javax.swing.JLabel;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.FlowLayout;

public class ventanaPrincipal extends JFrame {

	private static final long serialVersionUID = 4429640112774618601L;
	private JPanel contentPane;
	private Usuario user;
	private static String menuselected;
	private ArrayList<JButton> listaMenu;
	private JButton btnLiga;
	private JButton btnMiEquipo;
	private JPanel panel_Top;
	private JLabel lblNombreUsuario;
	private JPanel panel_User;
	private JTextPane txtpnBienvenidos;
	private JLabel lblLigaFantasyFlex;
	private static final String[] menus = { "Mi Liga", "Mi Equipo", "Mercado" };
	private PanelLigas panelLigas;
	private PanelMiEquipo panelEquipo;
	private JLabel lblEsteEsTu;
	private ArrayList<JPanel> listapaneles;
	@SuppressWarnings("unused")
	private ListaJugadores listajugadores;
	private JPanel panel_1;
	private JPanel panel_Jugador1;
	private JLabel lblNombreUsuario1;
	private JPanel panel_Pos1;
	private JLabel lblPos1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventanaPrincipal frame = new ventanaPrincipal(null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param user1
	 *            The user that has logged in
	 * @param listaJugadores
	 */
	public ventanaPrincipal(Usuario user1, ListaJugadores listaJugadores) {

		/*
		 * Actualizamos/Inicializamos variables
		 */

		user = user1;
		menuselected = menus[0];
		listaMenu = new ArrayList<JButton>();
		listajugadores = listaJugadores;

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

		/*
		 * Panel_Menu que contiene los botones del Menu
		 */

		JPanel panel_Menu = new JPanel();
		contentPane.add(panel_Menu, BorderLayout.WEST);
		panel_Menu.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_Menu.setBackground(new Color(0, 102, 204));
		panel_Menu.setLayout(new MigLayout("", "[20px][160px]", "[70px][][][][][][][][]"));

		/*
		 * Boton Liga
		 */

		btnLiga = new JButton("Mi Liga");
		btnLiga.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_Menu.add(btnLiga, "cell 1 1");
		btnLiga.setContentAreaFilled(false);
		btnLiga.setBorder(BorderFactory.createEmptyBorder());
		btnLiga.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				menuselected = menus[0];
				refresh();

			}
		});

		listaMenu.add(btnLiga);

		/*
		 * Boton Mi Equipo
		 */

		btnMiEquipo = new JButton("Mi Equipo");
		btnMiEquipo.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_Menu.add(btnMiEquipo, "cell 1 3");
		btnMiEquipo.setContentAreaFilled(false);
		btnMiEquipo.setBorder(BorderFactory.createEmptyBorder());
		btnMiEquipo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				menuselected = menus[1];
				refresh();

			}
		});
		listaMenu.add(btnMiEquipo);

		/*
		 * Panel Top incluye el titulo y la info del Usuario
		 */

		panel_Top = new JPanel();
		panel_Top.setBorder(null);
		panel_Top.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel_Top, BorderLayout.NORTH);
		panel_Top.setLayout(new MigLayout("", "[200][grow][]", "[100px,grow]"));

		lblLigaFantasyFlex = new JLabel("LIGA FANTASY FLEX");
		lblLigaFantasyFlex.setFont(new Font("Monospaced", Font.BOLD, 32));
		panel_Top.add(lblLigaFantasyFlex, "cell 1 0,alignx center,aligny center");

		/*
		 * Panel User
		 */

		panel_User = new JPanel();
		panel_User.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_User.setBackground(Color.LIGHT_GRAY);
		panel_Top.add(panel_User, "cell 0 0,grow");
		panel_User.setLayout(new MigLayout("", "[90px][]", "[22px][][]"));

		lblNombreUsuario = new JLabel("New label");
		lblNombreUsuario.setFont(new Font("Monospaced", Font.PLAIN, 16));
		lblNombreUsuario.setText(user.getNombre());
		panel_User.add(lblNombreUsuario, "cell 1 2,alignx left,aligny top");

		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);

		/*
		 * Panel Liga
		 */

		listapaneles = new ArrayList<>();
		panelLigas = new PanelLigas();
		listapaneles.add(panelLigas);
		panelEquipo = new PanelMiEquipo();
		listapaneles.add(panelEquipo);
		refresh();

	}

	/**
	 * Metodo que actualiza la pagina
	 */

	public void refresh() {
		ArrayList<JButton> lista = new ArrayList<>();
		lista = listaMenu;
		mostrarPanel();
		for (int i = 0; i < lista.size(); i++) {
			JButton b = listaMenu.get(i);
			if (menuselected.equals(b.getText())) {
				b.setFont(new Font("Monospaced", Font.PLAIN, 22));
				b.setForeground(new Color(255, 255, 255));
			} else {
				b.setFont(new Font("Monospaced", Font.PLAIN, 22));
				b.setForeground(new Color(0, 0, 0));

			}

		}

		listaMenu = lista;

	}

	public void mostrarPanel() {
		for (int i = 0; i < listapaneles.size(); i++) {
			listapaneles.get(i).setVisible(false);
		}
		if (menuselected.equals(menus[0])) {
			panelLigas.setVisible(true);
		} else if (menuselected.equals(menus[1])) {

			panelEquipo.setVisible(true);
		} else if (menuselected.equals(menus[2])) {

		}
	}

	private class PanelLigas extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5817000428864655052L;

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
						ventanaPrincipal.this.setVisible(false);
						VentanaCrearLiga vCL = new VentanaCrearLiga(ventanaPrincipal.this, user);
						vCL.setVisible(true);

					}
				});
				PanelLigas.this.add(anadirLiga1, "cell 1 2,alignx center,aligny top");

				btnMiEquipo.setEnabled(false);

			} else { 		//FALTA

				JPanel panel = new JPanel();
				PanelLigas.this.add(panel, "cell 1 1, grow");
				panel.setLayout(new MigLayout("", "[100px][200px,grow]", "[][][60px]"));
				panel_Pos1 = new JPanel();
				panel.add(panel_Pos1, "cell 0 2,alignx center,aligny center");
				lblPos1 = new JLabel("1");
				lblPos1.setFont(new Font("Tahoma", Font.PLAIN, 16));
				lblPos1.setHorizontalAlignment(SwingConstants.RIGHT);
				panel_Pos1.add(lblPos1);

				panel_Jugador1 = new JPanel();
				panel.add(panel_Jugador1, "cell 1 2,grow");
				panel_Jugador1.setLayout(new MigLayout("", "[56px][56px][56px][][][][][][][]", "[16px][]"));

				lblNombreUsuario1 = new JLabel(user.getNombre());
				panel_Jugador1.add(lblNombreUsuario1, "cell 0 0,alignx left,aligny top");

				// lblValorUsuario1 = new JLabel(" Valor:"+user.getMiEquipo().getValorEquipo());
				// panel_Jugador1.add(lblValorUsuario1, "cell 9 0,alignx left,aligny top");
			}

			contentPane.add(PanelLigas.this, BorderLayout.CENTER);
		}
	}

	private class PanelMiEquipo extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1605981510261014903L;				//FALTA
		private JPanel panel_Portero;
		private JPanel panel_Defensa;
		private JPanel panel_Centro;
		private JPanel panel_Delanteros;
		private JButton btnPortero;
		private JButton btnDef1;
		private JButton btnDef4;
		private JButton btnDef3;
		private JButton btnDef2;
		private JButton btnMed3;
		private JButton btnMed2;
		private JButton btnMed1;
		private JButton btnDel2;
		private JButton btnDel3;
		private JButton btnDel1;


		public PanelMiEquipo() {
			PanelMiEquipo.this.setBackground(new Color(255, 255, 255));
			PanelMiEquipo.this
					.setLayout(new MigLayout("", "[grow]", "[50px][100px][100px,grow][100px,grow][100px,grow]"));

			lblEsteEsTu = new JLabel("Este es tu Equipo");
			PanelMiEquipo.this.add(lblEsteEsTu, "cell 0 0");

			panel_Portero = new JPanel();
			PanelMiEquipo.this.add(panel_Portero, "cell 0 1,alignx center,aligny center");
			panel_Portero.setLayout(new BorderLayout(0, 0));

			btnPortero = new JButton("Portero");
			btnPortero.setBackground(Color.WHITE);
			panel_Portero.add(btnPortero, BorderLayout.CENTER);

			panel_Defensa = new JPanel();
			PanelMiEquipo.this.add(panel_Defensa, "cell 0 2,alignx center,aligny center");
			panel_Defensa.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			btnDef1 = new JButton("New button");
			btnDef1.setBackground(Color.WHITE);
			panel_Defensa.add(btnDef1);

			btnDef2 = new JButton("New button");
			btnDef2.setBackground(Color.WHITE);
			panel_Defensa.add(btnDef2);

			btnDef3 = new JButton("New button");
			btnDef3.setBackground(Color.WHITE);
			panel_Defensa.add(btnDef3);

			btnDef4 = new JButton("New button");
			btnDef4.setBackground(Color.WHITE);
			panel_Defensa.add(btnDef4);

			panel_Centro = new JPanel();
			PanelMiEquipo.this.add(panel_Centro, "cell 0 3,growx,aligny center");

			btnMed1 = new JButton("New button");
			btnMed1.setBackground(Color.WHITE);
			panel_Centro.add(btnMed1);

			btnMed2 = new JButton("New button");
			btnMed2.setBackground(Color.WHITE);
			panel_Centro.add(btnMed2);

			btnMed3 = new JButton("New button");
			btnMed3.setBackground(Color.WHITE);
			panel_Centro.add(btnMed3);

			panel_Delanteros = new JPanel();
			PanelMiEquipo.this.add(panel_Delanteros, "cell 0 4,growx,aligny center");

			btnDel1 = new JButton("New button");
			panel_Delanteros.add(btnDel1);

			btnDel2 = new JButton("New button");
			panel_Delanteros.add(btnDel2);

			btnDel3 = new JButton("New button");
			panel_Delanteros.add(btnDel3);

			// contentPane.add(PanelMiEquipo.this, BorderLayout.CENTER);
		}
	}

	
}
