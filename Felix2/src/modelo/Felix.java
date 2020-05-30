package modelo;

import java.awt.Point;

import controlador.Control;

public class Felix {

	public static final int MOVIMIENTO_IZQUIERDA = 1;
	public static final int MOVIMIENTO_DERECHA = 2;
	public static final int MOVIMIENTO_ABAJO = 3;
	public static final int MOVIMIENTO_ARRIBA = 4;

//-------------------------------------------------------------ATRIBUTOS----------------------------------------------------------------	

	private int puntos;
	private Point posicion;
	private int cantVidas;
	private Juego manager;
	private boolean inmune;
	private Control control;
	private boolean murio;

//------------------------------------------------------------CONSTRUCTOR----------------------------------------------------------------	

	public Felix(Point posicion, Juego manager) {
		murio = false;
		this.manager = manager;
		puntos = 0;
		this.posicion = posicion;
		cantVidas = 3;
		inmune = false;
	}

//---------------------------------------------------------------METODOS----------------------------------------------------------------

	/**
	 * Mueve a izquierda si es 1, a derecha si es 2, abajo si es 3, arriba si es 4.
	 * 
	 * @param direccion indica que direccion debe moverse
	 * 
	 */

//	public void  mover (String direccion )

	public void mover(int direccion) {

		switch (direccion) {
		case MOVIMIENTO_IZQUIERDA:
			posicion.setLocation(posicion.getX() - 1, posicion.getY());
			break;
		case MOVIMIENTO_DERECHA:
			posicion.setLocation(posicion.getX() + 1, posicion.getY());
			break;
		case MOVIMIENTO_ARRIBA:
			posicion.setLocation(posicion.getX(), posicion.getY() - 1);
			break;
		case MOVIMIENTO_ABAJO:
			posicion.setLocation(posicion.getX(), posicion.getY() + 1);
			break;
		}

	}

	/**
	 * Manda al Manager del juego que arregle la ventana que se encuentra en la
	 * posicion actual.
	 * 
	 */
	public void arreglar() {
		manager.arreglar();
		// PARA PRIMER ENTREGA
		// System.out.println("Felix Jr. da un martillazo ");

	}

	/**
	 * Activa su inmunidad.
	 * 
	 */

	public void activarInmunidad() {
		inmune = true;
	}

	/**
	 * Desactiva su inmunidad.
	 * 
	 */
	public void desactivarInmunidad() {
		inmune = false;
	}

	/**
	 * Suma a la cantidad de puntos actual, la cantidad de puntos que se pasa por
	 * parametro
	 * 
	 * @param puntos son los puntos que gano felix al arreglar
	 */
	public void sumarPuntos(int puntos) {
		this.puntos += puntos;
	}

	/**
	 * Cuando se termina de reparar por completo una seccion, felix cambia su
	 * posicion a la posicion pasada por parametro.
	 * 
	 * @param posicion es la posicion a la que se debe reubicar felix
	 */

	public void cambiarPosicion(int x, int y) {
		this.posicion.x = x;
		this.posicion.y = y;

	}

	/**
	 * Felix decrementa una vida
	 * 
	 */

	public void morir() {
		// PARA PRIMER ENTREGA
		// System.out.println("Felix Jr. pierde una vida ");
		control.felixPerdioVida();
		cantVidas--;
		if (cantVidas == 0)
			murio = true;

	}

//----------------------------------------------------GETTER/SETTERS----------------------------------------------------------------
	public void setControl(Control control) {
		this.control = control;
	}

	public Point getPosicion() {

		return posicion;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getCantVidas() {
		return cantVidas;
	}

	public void setCantVidas(int cantVidas) {
		this.cantVidas = cantVidas;
	}

	public boolean esInmune() {
		return inmune;
	}

	public boolean murio() {
		return murio;
	}

}
