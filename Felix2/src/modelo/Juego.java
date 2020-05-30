package modelo;

import java.util.LinkedList;
import java.util.Random;

import controlador.Control;
import controlador.controladorArchivo;
import excepciones.ErrorAlAbrirArchivo;

import java.awt.Point;

public class Juego {

	public static final int MOVIMIENTO_IZQUIERDA = 1;
	public static final int MOVIMIENTO_DERECHA = 2;
	public static final int MOVIMIENTO_ABAJO = 3;
	public static final int MOVIMIENTO_ARRIBA = 4;

	// -------------------------------------------------------------ATRIBUTOS----------------------------------------------------------------
	private LinkedList<Patos> patos;

	private Ralph ralph;
	private Felix felix;
	private Niceland edificio;
	private LinkedList<Ladrillo> ladrillos;
	private String nombreJug;
	private String[][] datos;
	private controladorArchivo ctrlArch;
	private Control control;

	// ------------------------------------------------------------CONSTRUCTOR----------------------------------------------------------------

	public Juego() throws ErrorAlAbrirArchivo {
		ctrlArch = new controladorArchivo();
		datos = ctrlArch.cargarDatos();
		this.felix = new Felix(new Point(2, 3), this);
		this.ralph = new Ralph(new Point(4, 0));
		patos = new LinkedList<Patos>();
		ladrillos = new LinkedList<Ladrillo>();
		edificio = new Niceland(this);
		inicializarPatos();

	}

	// ---------------------------------------------------------------METODOS----------------------------------------------------------------
	public void actualizarTurno() {
		moverRalph();
		moverPatos();
		moverLadrillos();
	}

	public void setControl(Control control) {
		this.control = control;
		felix.setControl(control);
	}

	/**
	 * Coloca a los personajes en la posicion inicial para la seccion,
	 * 
	 */
	public void reposicionarPersonajes() {
		felix.cambiarPosicion(2, 3);
		ralph.cambiarPosicion(4, 0);

	}

	/**
	 * Le envia al edificio "Niceland" la posicion de la ventana que debe ser
	 * reparada
	 * 
	 * @param posicion es la posicion de la ventana que se debe arreglar
	 * 
	 */

	public int arreglar() {
		int p = edificio.arreglar(felix.getPosicion());
		felix.sumarPuntos(p);
		if (p == 500) {

			// finJuego();
		}
		return felix.getPuntos();
	}

	/**
	 * Mueve a felix dependiendo de la direccion,izquierda si direccion =1, derecha
	 * si direccion = 2,abajo si direccion=3 y arriba si direccion =4 siempre y
	 * cuando es posible tambien realiza un chequeo de colision entre felix y los
	 * objetos que pueden matarlo
	 * 
	 * @param direccion es la direccion hacia donde se debe mover felix
	 * 
	 */
	public boolean moverFelix(int direccion) {

		boolean acceso = edificio.accesible(felix.getPosicion(), direccion);

		if (acceso) {

			felix.mover(direccion);
			chequeoColisionPatosFelix();
			chequeoColisionLadrilloFelix();
		}
		return acceso;

	}

	/**
	 * Felix activa la inmunidad por lo que no puede morir ante colisiones
	 * 
	 * 
	 */
	public void felixPastelAct() {
		felix.activarInmunidad();
	};

	/**
	 * Desactiva la inmunidad de felix
	 * 
	 */
	public void felixPastelDesact() {
		felix.desactivarInmunidad();
	};

	/**
	 * Chequea que felix que felix haya chocado con algun objeto que podria matarlo
	 * 
	 */
	public boolean chequeoColisionLadrilloFelix() {

		if (!felix.esInmune()) {
			// recorro la lista de ladrillos hasta encontrar una colision o hasta llegar al
			// final
			boolean choco = false;
			int p = 0;

			while (p < ladrillos.size() && !choco) {
				choco = felix.getPosicion().equals(ladrillos.get(p).getPosicion());
				p++;
			}

			if (choco) {
				// PARA PRIMER ENTREGA
				// System.out.println("Una roca impacto a Felix Jr. ");
				felix.morir();

				return true;
			}

		}
		return false;

	}

	/**
	 * Mueve a ralph dependiendo de la direccion,izquierda si direccion =1, derecha
	 * si direccion = 2 siempre y cuando es posible
	 * 
	 * @param direccion es la direccion hacia donde se debe mover ralph
	 * 
	 */
	public boolean moverRalph() {

		Random rnd = new Random();
		int dire = rnd.nextInt(5);

		boolean acceso;
		if (dire == 4 || dire == 5)
			acceso = false;
		if (dire == MOVIMIENTO_IZQUIERDA && ralph.getPosicion().x == 0)
			acceso = false;
		else if ((dire == MOVIMIENTO_DERECHA) && ralph.getPosicion().x == 4)
			acceso = false;
		else if (dire == 3) {
			golpea();
			acceso = false;
		} else
			acceso = true;

		if (acceso) {

			ralph.mover(dire);
		}
		return acceso;

	}

	/**
	 * Ralph eliminara un ladrillo de su lista y se guarda en la lista de ladrillos
	 * de juego(los ladrillos de la lista de juego, son los que actualmente se estan
	 * cayendo)
	 * 
	 */
	public void golpea() {
		Ladrillo l = ralph.golpear();
		ladrillos.add(l);
		control.agregarLadrillo();
	}

	/**
	 * Mueve los ladrillos de la lista una posicion hacia abajo, ya que solo caen.
	 * si los ladrillos tiene una posicion y<0(pasaron el piso ) estos dejan de
	 * existir.
	 * 
	 * 
	 */
	public boolean moverLadrillos() {

		LinkedList<Ladrillo> elim = new LinkedList<Ladrillo>();
		for (Ladrillo p : ladrillos) {
			p.caer();
			if (p.getPosicion().y > 5)
				elim.add(p);
		}

		boolean ret = chequeoColisionLadrilloFelix();

		while (!elim.isEmpty())
			ladrillos.remove(elim.removeLast());
		return ret;

	}

	/**
	 * 				 
	 *           
	 */
	public void moverPatos() {

		for (Patos p : patos) {
			p.mover();
		}

		chequeoColisionPatosFelix();

	}

	private boolean chequeoColisionPatosFelix() {

		if (!felix.esInmune()) {
			// recorro la lista de ladrillos hasta encontrar una colision o hasta llegar al
			// final
			boolean choco = false;
			int p = 0;

			while (p < patos.size() && !choco) {

				choco = felix.getPosicion().equals(patos.get(p).getPosicion());
				p++;
			}

			if (choco) {
				felix.morir();
				return true;
			}

		}
		return false;
	}

	/**
	 * Sube de nivel a ralph y el edifio "Niceland". Ademas se encarga de
	 * reposicionar a los personajes
	 * 
	 */
	public void subirNivel() {
		reposicionarPersonajes();
		ralph.subirNivel();
		edificio.subirNivel();

	}

	/**
	 * Revisa si en el archivo hay algun puntaje menor que el pasado por parametro,
	 * de ser asi lo reemplaza y le pide el nombre del jugador
	 * 
	 * @param puntos puntaje que obtuvo felix
	 * 
	 */
	public void puntajes(int puntos) {
		// if(chequeoPuntos(puntos)){
		ctrlArch.modificarPuntajes(puntos, nombreJug);
		// }

	}

	public boolean chequeoPuntos(int puntos) {
		boolean encontre = false;
		int i = 0;
		while (!encontre && i < 5 && datos[i][1] != null) {

			if (Integer.parseInt(datos[i][1]) < puntos) {

				encontre = true;
			} else
				i++;
		}
		if (i >= 5)
			return false;
		else if (datos[i][1] != null)
			return encontre;
		return true;

	}

	public Ralph getRalph() {
		return ralph;
	}

	public Felix getFelix() {
		return felix;
	}

	public Niceland getEdificio() {
		return edificio;
	}

	public LinkedList<Ladrillo> getLadrillos() {
		return ladrillos;
	}

	public void setNombreJugador(String nombreJug) {
		this.nombreJug = nombreJug;
	}

	// metodo echo para la segunda entrega
	public void subirNivelEleccion(int nivel) {

		for (int i = 1; i < nivel; i++) {

			edificio.subirNivel();
		}
		/*
		 * reposicionarPersonajes(); ralph.subirNivel();
		 */

	}

	public void inicializarPatos() {

		if (!patos.isEmpty()) {
			for (int i = 0; i < patos.size(); i++) {
				patos.removeFirst();
			}
		}
		// ------------------------------------------------------NIVEL
		Random rnd = new Random();
		int POS;
		int cant = 1;// rnd.nextInt(60);//---------------------------->no implementado aun

		// por ahora se crea un solo pato
		for (int i = 0; i < cant; i++) {
			POS = rnd.nextInt(3);
			Patos aux = new Patos(1);
			aux.setPosicion(new Point(7, 3 - POS));
			patos.add(aux);
		}

	}

	public LinkedList<Patos> getPatos() {
		return patos;
	}

	public void reiniciar() {
		this.felix = new Felix(new Point(2, 3), this);
		this.ralph = new Ralph(new Point(4, 0));
		patos = new LinkedList<Patos>();
		ladrillos = new LinkedList<Ladrillo>();
		edificio = new Niceland(this);
		inicializarPatos();
	}

	public void subirSeccion() {
		reposicionarPersonajes();
		edificio.subirSeccion();

	}

	public int getTiempo() {
		return edificio.getTiempo();
	}

	public int getNivel() {
		return edificio.getNivel();
	}

}
