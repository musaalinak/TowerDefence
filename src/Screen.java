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
	public static boolean isWin = false;
	public static int myWidth, myHeight;
	public static int coinage = 10, health = 100;
	public static int killed = 0, killsToWin = 0, level = 1, maxlevel = 3;
	public static int winT�me = 4000, winFrame = 0;
	public static Image[] tileset_ground = new Image[100];
	public static Image[] tileset_air = new Image[100];
	public static Image[] tileset_res = new Image[100];
	public static Image[] tileset_mob = new Image[100];

	public static Room room = new Room();
	public static Save save;
	public static Point mse = new Point(0, 0);
	public static Store store;
	public static Mob[] mobs = new Mob[30];

	public Screen(final Frame frame) {
		frame.addMouseListener(new KeyHandel());
		frame.addMouseMotionListener(new KeyHandel());
		thread.start();

	}

	public static void hasWon() {
		if (killed == killsToWin) {
			isWin = true;
			killed = 0;
			coinage = 0;
			// level+=1;

		}
	}

	public void define() {
		room = new Room();
		save = new Save();
		store = new Store();
		coinage=10;
		health=10;

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
		save.loadSave(new File("save/mission" + level + ".ulixava"));
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
		if (isWin) {
			g.setColor(new Color(255, 255, 255));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("Courier New", Font.BOLD, 14));
			if (level == maxlevel) {
				g.drawString("You won the whole game! You 1  1  1  1  1  1  1  1  1  1  1  1  may now close the window down", 10, 10);
			} else {
				g.drawString("You won! Congratulations! Please wait and the window will close", 10, 10);

			}
		}

	}

	public int spawnTime = 2500;
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
			if (!isFirst && health > 0 && !isWin) {
				room.physic();
				mobSpawner();
				for (int i = 0; i < mobs.length; i++) {
					if (mobs[i].inGame) {
						mobs[i].physic();
					}
				}

			} else {
				if (isWin) {
					if (winFrame >= winT�me) {
						if (level == maxlevel) {
							System.exit(0);

						} else {
							level += 1;
							define();
							isWin = false;
							winFrame = 0;
						}
					} else {
						winFrame += 1;
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
