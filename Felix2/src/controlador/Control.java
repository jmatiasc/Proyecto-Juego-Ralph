package controlador;

import java.util.LinkedList;

import javax.swing.JTable;
import excepciones.*;
import modelo.*;
import vista.Menu;
import vistaGame.Edificio;
import vistaGame.ThreadCronometro;

public class Control {

	private ThreadCronometro cronometro;
	private Menu menu;
	private Juego logica;
	private controladorArchivo ctrlArch;

	public Control(Menu menu, Juego manager) {
		ctrlArch = new controladorArchivo();

		this.logica = manager;
		logica.setControl(this);

	}

	public void setTurno() {

		if (!logica.getFelix().murio() && menu.getEdificio() != null)

		{
			logica.actualizarTurno();
			Edificio edificio = menu.getEdificio();
			if (edificio != null) {
				edificio.actualizarPosiciones();
			}
		} else {
			if ((menu.getEdificio() != null))
				menu.getEdificio().gameOver();
		}
	}

	public void start() {
		cronometro = new ThreadCronometro(this, logica.getTiempo());
		cronometro.start();
	}

	public void detenerCronometro() {
		cronometro.detenerCronometro();
	}
	// -----------------------------------------------metodos de puntuacion del
	// juego

	public void setNombreJugador(String nombreJug) {
		logica.setNombreJugador(nombreJug);
	}

	public void puntajes(int puntos) {
		logica.getFelix().setPuntos(puntos);
		logica.puntajes(puntos);

	}

	public boolean checkNombre(String njUG) {
		if (njUG.length() > 2) {

			if (njUG.length() > 20) {
				njUG = njUG.substring(0, 20);
			}

			for (int i = 0; i < njUG.length(); i++) {
				if (njUG.charAt(i) == ' ') {
					return false;
				}
			}
			return true;
		}

		return false;
	}

	// acciones de juego

	public int arreglar() {
		return logica.arreglar();

	}

	// ----------------------------------------Metodos para manejo de juego

	public boolean chequeoPuntos(int puntos) {
		return logica.chequeoPuntos(puntos);
	}

	public void subirSeccion() {
		logica.subirSeccion();
	}

	public void abrirJuego() {
		menu.setVisibleJuego();
	}

	public void reiniciarJuego() {
		cronometro.detenerCronometro();
		menu.eliminarEdificio();
		logica.reiniciar();
	}

	public void setVisibleMenu() {
		logica.reiniciar();
		menu.setVisible();
	}

	public void cargarDatos(JTable table) throws VectorFueraDeRangoException, ErrorAlAbrirArchivo {

		String[][] datos = ctrlArch.cargarDatos();

		table.setValueAt(datos[0][0], 1, 1);
		table.setValueAt(datos[0][1], 1, 2);
		table.setValueAt(datos[1][0], 2, 1);
		table.setValueAt(datos[1][1], 2, 2);
		table.setValueAt(datos[2][0], 3, 1);
		table.setValueAt(datos[2][1], 3, 2);
		table.setValueAt(datos[3][0], 4, 1);
		table.setValueAt(datos[3][1], 4, 2);
		table.setValueAt(datos[4][0], 5, 1);
		table.setValueAt(datos[4][1], 5, 2);
	}

	public void selecionarNivel(int nivel) {
		logica.subirNivelEleccion(nivel);
		cronometro = new ThreadCronometro(this, logica.getTiempo());
	}

	// ------------------------------------------------------------Getters-------

	public Seccion[] getSecciones() {
		return logica.getEdificio().getSecciones();
	}

	public Felix getFelix() {
		return logica.getFelix();
	}

	public Ralph getRalph() {
		return logica.getRalph();
	}

	public LinkedList<Patos> getPatos() {
		return logica.getPatos();
	}

	// -----------------------------------------------------------------SETTERS

	public void setMenu(Menu menu) {
		this.menu = menu;
		menu.initialize();
	}

	public boolean moverFelix(int dir) {
		return logica.moverFelix(dir);
	}

	public void agregarLadrillo() {
		menu.getEdificio().ralphGolpea();
	}

	public int getTiempo() {
		return logica.getTiempo();
	}

	public void setTiempo(int t) {

		Edificio edificio = menu.getEdificio();
		if (edificio != null) {
			edificio.setTiempo(t);
		}
	}

	public void subirNivel() {
		cronometro.detenerCronometro();
		logica.subirNivel();
		cronometro.setTiempo(logica.getTiempo());
	}

	public int getNivel() {
		return logica.getNivel();
	}

	public void felixPerdioVida() {
		cronometro.murioFelix();
		menu.getEdificio().felixMorir();
		cronometro.revivioFelix();
	}
}
