import java.awt.Graphics;

import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {
	public Thread thread = new Thread(this);
	public static boolean isFirst = true;
	public static int myWidth, myHeight;

	public static Room room = new Room();

	public Screen() {
		thread.start();

	}

	public void define() {
		room = new Room();
	}

	@Override
	public void paintComponent(final Graphics g) {
		if (isFirst) {
			define();
			isFirst = false;

		}
		g.clearRect(0, 0, getWidth(), getHeight());
		room.draw(g);// Drawing the room.
	}

	@Override
	public void run() {
		while (true) {
			if (!isFirst) {

			}
			repaint();

			try {
				Thread.sleep(1);
			} catch (final InterruptedException e) {

			}
		}
	}

}
