import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {
	public Thread thread = new Thread(this);
	public static boolean isFirst = true;
	public static boolean isDebug = false;
	public static int myWidth, myHeight;
	public static int coinage = 10, health = 100;
	public static Image[] tileset_ground = new Image[100];
	public static Image[] tileset_air = new Image[100];
	public static Image[] tileset_res = new Image[100];
	public static Image[] tileset_mob = new Image[100];

	public static Room room = new Room();
	public static Save save;
	public static Point mse = new Point(0, 0);
	public static Store store;
	public static Mob[] mobs = new Mob[100];

	public Screen(final Frame frame) {
		frame.addMouseListener(new KeyHandel());
		frame.addMouseMotionListener(new KeyHandel());
		thread.start();

	}

	public void define() {
		room = new Room();
		save = new Save();
		store = new Store();

		for (int i = 0; i < tileset_ground.length; i++) {
			tileset_ground[i] = new ImageIcon("res/tileset_ground.png").getImage();
			tileset_ground[i] = createImage(
					new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
		}
		for (int i = 0; i < tileset_air.length; i++) {
			tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
			tileset_air[i] = createImage(
					new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
		}
		tileset_res[0] = new ImageIcon("res/cell.png").getImage();
		tileset_res[1] = new ImageIcon("res/heart.png").getImage();
		tileset_res[2] = new ImageIcon("res/coin.png").getImage();
		tileset_mob[0] = new ImageIcon("res/mob.png").getImage();
		save.loadSave(new File("save/mission1.ulixava"));
		for (int i = 0; i < mobs.length; i++) {
			mobs[i] = new Mob();

		}
	}

	@Override
	public void paintComponent(final Graphics g) {
		if (isFirst) {
			myWidth = getWidth();
			myHeight = getHeight();
			define();
			isFirst = false;

		}
		g.setColor(new Color(75, 75, 75));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(0, 0, 0));
		g.drawLine(room.block[0][0].x - 1, 0, room.block[0][0].x - 1,
				room.block[room.worldHeight - 1][0].y + room.blockSize);// left line
		g.drawLine(room.block[0][room.worldWidth - 1].x + room.blockSize, 0,
				room.block[0][room.worldWidth - 1].x + room.blockSize,
				room.block[room.worldHeight - 1][0].y + room.blockSize);// right line
		g.drawLine(room.block[0][0].x, room.block[room.worldHeight - 1][0].y + room.blockSize,
				room.block[0][room.worldWidth - 1].x + room.blockSize,
				room.block[room.worldHeight - 1][0].y + room.blockSize);// bottom line
		room.draw(g);// Drawing the room.
		for (int i = 0; i < mobs.length; i++) {
			if (mobs[i].inGame) {
				mobs[i].draw(g);
			}
		}

		store.draw(g);// drawing the store

		if (health < 1) {
			g.setColor(new Color(240, 20, 20));
			g.fillRect(0, 0, myWidth, myHeight);
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("Courier New", Font.BOLD, 14));
			g.drawString("Game over,Unlucky", 10, 10);
		}
	}

	public int spawnTime = 1000;
	public int spawnFrame = 0;

	public void mobSpawner() {
		if (spawnFrame >= spawnTime) {
			for (int i = 0; i < mobs.length; i++) {
				if (!mobs[i].inGame) {
					mobs[i].spawnMob(Value.mobGreeny);
					break;
				}
			}
			spawnFrame = 0;
		} else {
			spawnFrame += 1;
		}
	}

	@Override
	public void run() {
		while (true) {
			if (!isFirst && health > 0) {
				room.physic();
				mobSpawner();
				for (int i = 0; i < mobs.length; i++) {
					if (mobs[i].inGame) {
						mobs[i].physic();
					}
				}

			}
			repaint();

			try {
				Thread.sleep(1);
			} catch (final InterruptedException e) {

			}
		}
	}

}
