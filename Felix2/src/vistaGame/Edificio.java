package vistaGame;

import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Control;
import modelo.Seccion;
import modelo.Ventana;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Edificio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel mContentPane;
	private JLabel edificioLabel;
	private GJuego mJuego;
	private Control control;
	private Seccion[] secciones;
	private int seccionActual;

	// ---------------------------------------

	private JLabel lblPuntos;
	private JLabel lblMinutos;
	private JLabel lblCantVidas;

	// ---------------------------------------

	private AudioClip sonido;
	private boolean gano;
	private JLabel lblPasto;
	private JLabel lblFondoP1;

	private GVentana[][] vent;
	private int puntos;
	private JLabel lblPiso;
	private int nivel;

	public Edificio(Control control) {
		nivel = control.getNivel();
		seccionActual = 0;
		gano = false;
		sonido = java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/SoundFelix.wav"));
		sonido.play();
		sonido.loop();

		puntos = 0;
		vent = new GVentana[5][4];

		this.control = control;
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				accion(arg0);
			}
		});

		this.mContentPane = new JPanel();
		this.mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(this.mContentPane);
		getContentPane().setLayout(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 397);
		// ----------------------------------DIBUJO
		// VENTANAS---------------------------------------------------------------------
		secciones = control.getSecciones();
		iniciarSeccion(secciones[seccionActual]);

		// ----------------------------------MjUEGOS ES LA CLASE QUE ADMINISTRA TODOS
		// LOS OBJETOS Y PERSONAJES------------------------------------

		mContentPane.setLayout(null);

		lblPasto = new JLabel("");
		lblPasto.setIcon(new ImageIcon(Edificio.class.getResource("/img/pasto.png")));
		lblPasto.setBounds(0, 332, 341, 38);
		mContentPane.add(lblPasto);

		edificioLabel = new JLabel("");
		edificioLabel.setBounds(0, -11, 315, 381);
		edificioLabel.setIcon(new ImageIcon(Edificio.class.getResource("/img/edificio.png")));
		mContentPane.add(edificioLabel);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(341, 0, 131, 370);
		mContentPane.add(panel);
		panel.setLayout(null);
		// ----------------------------------------------------------------------------------------------------------------------
		JButton btnVolver = new JButton("volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.detenerCronometro();
				setVisible(false);
				sonido.stop();
				control.setVisibleMenu();
				control.reiniciarJuego();
			}
		});
		btnVolver.setBounds(10, 293, 101, 49);
		panel.add(btnVolver);
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Consolas", Font.PLAIN, 20));
		btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVolver.setBorderPainted(false);
		btnVolver.setContentAreaFilled(false);
		btnVolver.setDefaultCapable(false);
		btnVolver.setFocusTraversalKeysEnabled(false);
		btnVolver.setFocusable(false);
		btnVolver.setRolloverIcon(new ImageIcon(GUI.class.getResource("/img/yellow_button01.png")));
		btnVolver.setOpaque(false);
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Consolas", Font.PLAIN, 20));
		btnVolver.setFocusable(false);
		btnVolver.setFocusTraversalKeysEnabled(false);
		btnVolver.setDefaultCapable(false);
		btnVolver.setContentAreaFilled(false);
		btnVolver.setBorderPainted(false);
		btnVolver.setOpaque(false);
		btnVolver.setRequestFocusEnabled(false);
		btnVolver.setRolloverEnabled(false);
		btnVolver.setRolloverSelectedIcon(new ImageIcon(GUI.class.getResource("/img/yellow_button01.png")));
		btnVolver.setPressedIcon(new ImageIcon(GUI.class.getResource("/img/yellow_button01.png")));
		btnVolver.setIcon(new ImageIcon(GUI.class.getResource("/img/yellow_button00.png")));
		btnVolver.setHorizontalTextPosition(SwingConstants.CENTER);

		lblPuntos = new JLabel("00000" + puntos);
		lblPuntos.setForeground(Color.WHITE);
		lblPuntos.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblPuntos.setHorizontalAlignment(SwingConstants.CENTER);
		lblPuntos.setBounds(10, 52, 101, 22);
		panel.add(lblPuntos);

		JLabel lblPuntos_1 = new JLabel("SCORE");
		lblPuntos_1.setForeground(Color.RED);
		lblPuntos_1.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblPuntos_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPuntos_1.setBounds(10, 27, 101, 14);
		panel.add(lblPuntos_1);

		lblMinutos = new JLabel("");
		lblMinutos.setFont(new Font("Consolas", Font.PLAIN, 16));
		lblMinutos.setHorizontalAlignment(SwingConstants.CENTER);
		lblMinutos.setForeground(Color.WHITE);
		lblMinutos.setBounds(10, 117, 101, 28);
		panel.add(lblMinutos);

		JLabel lblTime = new JLabel("TIME");
		lblTime.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setForeground(Color.RED);
		lblTime.setBounds(10, 99, 101, 14);
		panel.add(lblTime);

		JLabel lblVidas = new JLabel("VIDAS");
		lblVidas.setHorizontalAlignment(SwingConstants.CENTER);
		lblVidas.setFont(new Font("Consolas", Font.PLAIN, 18));
		lblVidas.setForeground(Color.RED);
		lblVidas.setBounds(10, 162, 111, 28);
		panel.add(lblVidas);

		lblCantVidas = new JLabel(control.getFelix().getCantVidas() + "");
		lblCantVidas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCantVidas.setForeground(Color.WHITE);
		lblCantVidas.setBounds(0, 182, 121, 14);
		panel.add(lblCantVidas);

		lblFondoP1 = new JLabel("");
		lblFondoP1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblFondoP1.setLabelFor(lblFondoP1);
		lblFondoP1.setHorizontalAlignment(SwingConstants.CENTER);
		lblFondoP1.setIcon(new ImageIcon(Edificio.class.getResource("/img/fondoJuego.png")));
		lblFondoP1.setBounds(0, -27, 452, 382);
		mContentPane.add(lblFondoP1);

		lblPiso = new JLabel("New label");
		lblPiso.setVerticalAlignment(SwingConstants.TOP);
		lblPiso.setIcon(new ImageIcon(Edificio.class.getResource("/img/piso1.png")));
		lblPiso.setBounds(0, -11, 315, 366);
		mContentPane.add(lblPiso);

		JLabel lblNoche = new JLabel("");
		lblNoche.setVerticalAlignment(SwingConstants.TOP);
		lblNoche.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNoche.setIcon(new ImageIcon(Edificio.class.getResource("/img/fondoJuego.png")));
		lblNoche.setBounds(0, -11, 341, 381);
		mContentPane.add(lblNoche);

		// ----------------------------------------------------------------COMIENZA EL
		// JUEGO
		mJuego = new GJuego(this, control);

		control.start();

	}

	// -----------------------------------------------ADMINISTRACION DE ENTRADA POR
	// TECLADO--------------------------------------------------------
	protected void accion(KeyEvent key) {
		switch (key.getKeyCode()) {
		case KeyEvent.VK_A: {
			int aux = puntos;
			puntos = mJuego.martillar();

			if (puntos - aux == 500 && !mJuego.felixMuerto() && seccionActual != 2) {
				seccionActual++;
				lblPasto.setVisible(false);
				lblFondoP1.setVisible(false);
				edificioLabel.setVisible(false);
				iniciarSeccion(secciones[seccionActual]);

				control.subirSeccion();
				mJuego.reposicionarPersonajes();
			} else if (seccionActual == 2 && puntos - aux == 500) {
				seccionActual = 0;
				if (nivel == 10) {
					gano = true;
					gameOver();

				} else {
					lblPasto.setVisible(true);
					lblFondoP1.setVisible(true);
					edificioLabel.setVisible(true);
					nivel++;
					control.subirNivel();
					secciones = control.getSecciones();
					seccionActual = 0;
					iniciarSeccion(secciones[seccionActual]);
					mJuego.reposicionarPersonajes();
					control.start();
				}
				/*
				 * gano=true; gameOver();
				 */
			}

			lblPuntos.setText("" + puntos);
			break;
		}
		default:
			this.mJuego.moverFelix(key.getKeyCode());
			break;
		}

	}
	// ----------------------------------------------------------METODO DE DIBUJADO
	// DE LAS VENTANAS DEL EDIFICIO------------------------------------------------

	public void iniciarSeccion(Seccion sec) {

		if (vent[0][0] != null) {

			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 4; y++) {
					vent[x][y].borrar();
				}
			}
		}
		Ventana[][] ventanas = sec.getSeccion();

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				vent[x][y] = new GVentana(new Point(20 + (x * 54), (y * 90)), ventanas[x][y]);
			}
		}

		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 4; y++) {
				mContentPane.add(vent[x][y].getGrafico());
				mContentPane.setComponentZOrder(vent[x][y].getGrafico(), 0);
			}
		}
	}

	public void refrescar() {

		for (int x = 4; x > -1; x--) {
			for (int y = 3; y > -1; y--) {
				vent[x][y].refrescar();
			}
		}
	}

	// ------------------------------------------------------------METODO PARA
	// ACTUALIZAR EL TIEMPO CON UN THREAD
	// ----------------------------------------EN EL METODO TAMBIEN SE HACEN VARIOS
	// MOVIMIENTOS DE LOS PERSONAJES, SOLO PARA PROBAR EL FUNCIONAMIENTO DE LA
	// ----------------------------------------PARTE GRAFICA Y LOGICA, EN FUTURA
	// IMPLEMENTACION CADA OBJETO/PERSONAJE TENDRA SU PROPIO THREAD Y SE MOVERA CON
	// RESPECTO A SU
	// ----------------------------------------DETERMINADA VELOCIDAD

	public void actualizarPosiciones() {
		if (!mJuego.felixMuerto()) {
			lblCantVidas.setText(control.getFelix().getCantVidas() + "");
			if (!mJuego.felixMuerto())
				felixReposo();
			mJuego.moverRalph();
			mJuego.moverLadrillos();
			mJuego.moverPatos();
		} else
			control.detenerCronometro();
	}

	// --------------------------------------------------METODOS DE ACCIONES DE
	// PERSONAJES DEL JUEGO Y ACTUALIZACION DE ESTADO DEL
	// JUEGO-------------------------------------

	public void felixReposo() {
		mJuego.felixReposo();
	}

	// ----------------------------------------------------------------SETTERS---------------------------

	public void setVisible() {
		this.setVisible(true);
	}

	// ---------------------------------------------------METODO DE FINALIZACION DE
	// JUEGO

	public void gameOver() {

		sonido.stop();
		control.detenerCronometro();
		if (control.chequeoPuntos(puntos)) {

			GUI gui = new GUI(control, gano);
			gui.setPuntos(puntos);
			setVisible(false);
			gui.setVisible();
			control.reiniciarJuego();
		} else {
			setVisible(false);
			control.setVisibleMenu();
			control.reiniciarJuego();
		}
	}

	public void ralphGolpea() {
		mJuego.golpear();
	}

	public void setTiempo(int tiempo) {
		lblMinutos.setText("" + tiempo);
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public void felixMorir() {
		mJuego.felixMuerto();
	}
}
