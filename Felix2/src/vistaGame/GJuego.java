package vistaGame;

import java.util.LinkedList;
import controlador.Control;
import modelo.Patos;

public class GJuego {

	private GFelix mJugador;
	private GRalph mRalph;
	private Control control;
	private Edificio gui;

	// --------------------------------------------------------
	private LinkedList<GLadrillo> ladrillos;
	private LinkedList<GPato> patos;
	private boolean felixMuerto;

	/**
	 * @wbp.parser.entryPoint
	 */
	public GJuego(Edificio gui, Control control) {
		// Creo el jugador y lo agrego el grafico a la gui.
		ladrillos = new LinkedList<GLadrillo>();
		patos = new LinkedList<GPato>();
		felixMuerto = false;

		this.control = control;
		this.mJugador = new GFelix(control, control.getFelix(), this);
		this.mRalph = new GRalph(control, control.getRalph(), this);
		this.gui = gui;
		// DIBUJA FELIX Y RALPH
		this.gui.getContentPane().add(this.mJugador.getGrafico());
		this.gui.getContentPane().add(this.mRalph.getGrafico());
		gui.getContentPane().setComponentZOrder(mJugador.getGrafico(), 0);
		gui.getContentPane().setComponentZOrder(mRalph.getGrafico(), 0);
		// DIBUJO PATOS

		iniciarPatos();
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void moverFelix(int dir) {
		this.mJugador.mover(dir);
	}

	public void moverRalph() {
		this.mRalph.mover();
	}

	public int martillar() {
		int ret = mJugador.martillar();
		gui.refrescar();
		return ret;
	}

	public void felixReposo() {
		mJugador.reposo();
	}

	public void golpear() {

		GLadrillo aux = mRalph.golpear();
		ladrillos.add(aux);
		gui.getContentPane().add(aux.getGrafico());
		gui.getContentPane().setComponentZOrder(aux.getGrafico(), 0);
	}

	public void moverLadrillos() {

		LinkedList<GLadrillo> elim = new LinkedList<GLadrillo>();
		for (GLadrillo p : ladrillos) {
			p.mover();
			if (p.getPosicion().y > 500)
				elim.add(p);
		}

	}

	public void iniciarPatos() {
		LinkedList<Patos> posPatos = control.getPatos();

		if (!patos.isEmpty()) {
			for (int i = 0; i < patos.size(); i++) {
				patos.removeFirst().borrar();

			}
		}

		for (Patos p : posPatos) {

			patos.addLast(new GPato(control, p, this));
		}

		for (GPato pos : patos) {
			gui.getContentPane().add(pos.getGrafico());
			gui.getContentPane().setComponentZOrder(pos.getGrafico(), 0);
		}

	}

	public void moverPatos() {
		for (GPato p : patos) {
			p.mover();
		}

	}

	public boolean felixMuerto() {
		mJugador.morir();
		return felixMuerto;
	}

	public void reposicionarPersonajes() {
		iniciarPatos();

		this.gui.getContentPane().add(this.mJugador.getGrafico());
		this.gui.getContentPane().add(this.mRalph.getGrafico());
		gui.getContentPane().setComponentZOrder(mJugador.getGrafico(), 0);
		gui.getContentPane().setComponentZOrder(mRalph.getGrafico(), 0);
	}
}
