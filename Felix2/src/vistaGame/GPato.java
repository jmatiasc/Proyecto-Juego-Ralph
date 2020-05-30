package vistaGame;

import javax.swing.*;
import controlador.Control;
import modelo.Patos;

public class GPato {

	protected JLabel mGrafico;
	protected int mWidth;
	protected int mHeight;

	// felix en reposo en 0-------felix moviendose en 1-----felix martillando en
	// 2------felix muere en 3
	protected Icon imagenes[];
	protected Control control;
	protected GJuego juego;
	private int mov;
	private String moverDirec;
	private Patos pato;

	public GPato(Control control, Patos pato, GJuego juego) {
		moverDirec = "IZQUIERDA";
		mov = 1;
		this.juego = juego;
		this.control = control;
		mWidth = 34;
		mHeight = 23;
		this.pato = pato;

		imagenes = new Icon[4];

		// IZQUIERDA
		imagenes[0] = new ImageIcon((this.getClass().getResource("/img/patoI1.png")));
		imagenes[1] = new ImageIcon((this.getClass().getResource("/img/patoI2.png")));
		// DERECHA
		imagenes[2] = new ImageIcon((this.getClass().getResource("/img/patoD1.png")));
		imagenes[3] = new ImageIcon((this.getClass().getResource("/img/patoD2.png")));

		mGrafico = new JLabel(imagenes[0]);
		mGrafico.setBounds((pato.getPosicion().x * 54), (pato.getPosicion().y * 100), mWidth, mHeight);

	}

	/////////////////////////////////////////////////

	public void mover() {

		if (pato.getPosicion().x == 7)
			moverDirec = "IZQUIERDA";
		else if (pato.getPosicion().x == -2)
			moverDirec = "DERECHA";

		if (moverDirec.equals("IZQUIERDA")) {

			mGrafico.setIcon(imagenes[mov]);
			if (mov == 1)
				mov--;
			else
				mov++;
		} else {
			mGrafico.setIcon(imagenes[mov + 2]);
			if (mov == 1)
				mov--;
			else
				mov++;
		}
		mWidth = 34;
		mHeight = 23;
		mGrafico.setBounds((pato.getPosicion().x) * 54, (pato.getPosicion().y * 100), mWidth, mHeight);

	}

	// retorna la etiqueta con la imagen actual de nuestro personaje
	public JLabel getGrafico() {
		return this.mGrafico;
	}

	public void borrar() {
		ImageIcon iconNada = new ImageIcon((this.getClass().getResource("/img/nada.png")));

		mGrafico.setIcon(iconNada);
		mGrafico.setBounds((pato.getPosicion().x) * 54, (pato.getPosicion().y * 90), mWidth, mHeight);
	}

}