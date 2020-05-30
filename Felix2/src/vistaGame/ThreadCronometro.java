package vistaGame;

import controlador.Control;

public class ThreadCronometro extends Thread {
	private int segundos;

	private boolean andar;
	private Control control;
	private boolean vivo;

	public ThreadCronometro(Control control, int tiempo) {

		segundos = tiempo;
		this.control = control;
		andar = true;
		vivo = true;
	}

	public void run() {
		while (andar) {
			try {
				Thread.sleep(1000);
				if (vivo)
					control.setTurno();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (segundos != 0) {
				segundos--;
				control.setTiempo(segundos);
			} else {
				detenerCronometro();
				// control.matarFelix();
			}
		}

	}

	public void revivioFelix() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vivo = true;

	}

	public void murioFelix() {
		vivo = false;

	}

	public void detenerCronometro() {
		andar = false;

	}

	public void setTiempo(int tiempo) {
		segundos = tiempo;
	}

}
