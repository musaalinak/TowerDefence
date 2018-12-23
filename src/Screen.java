import java.awt.Graphics;
import java.awt.image.*;
import java.io.File;

import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel implements Runnable {
	public Thread thread = new Thread(this);
	public static boolean isFirst = true;
	public static int myWidth, myHeight;
	public static Image[] tileset_ground=new Image[100];
	public static Image[] tileset_air=new Image[100];
	public static Image[] tileset_res=new Image[100];

	public static Room room = new Room();
	public static Save save;
	public static Point mse=new Point(0,0);
	public static Store store;

	public Screen(Frame frame) {
		frame.addMouseListener(new KeyHandel());
		frame.addMouseMotionListener(new KeyHandel());
		thread.start();

	}

	public void define() {
		room = new Room();
		save= new Save();
		store=new Store();
		for(int i=0;i<tileset_ground.length;i++)
		{
			tileset_ground[i]=new ImageIcon("res/tileset_ground.png").getImage();
			tileset_ground[i]=createImage(new FilteredImageSource(tileset_ground[i].getSource(),new CropImageFilter(0,26*i,26,26)));
		}
		for(int i=0;i<tileset_air.length;i++)
		{
			tileset_air[i]=new ImageIcon("res/tileset_air.png").getImage();
			tileset_air[i]=createImage(new FilteredImageSource(tileset_ground[i].getSource(),new CropImageFilter(0,26*i,26,26)));
		}
		tileset_res[0]=new ImageIcon("res/cell.png").getImage();
		save.loadSave(new File("save/mission1.ulixava"));
	}

	@Override
	public void paintComponent(final Graphics g) {
		if (isFirst) {
			myWidth = getWidth();
			myHeight = getHeight();
			define();
			isFirst = false;

		}
		g.setColor(new Color(75,75,75));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(0,0,0));
		g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1, room.block[room.worldHeight-1][0].y+room.blockSize);//left line
		g.drawLine(room.block[0][room.worldWidth-1].x+room.blockSize, 0, room.block[0][room.worldWidth-1].x+room.blockSize, room.block[room.worldHeight-1][0].y+room.blockSize);//right line
		g.drawLine(room.block[0][0].x, room.block[room.worldHeight-1][0].y+room.blockSize, room.block[0][room.worldWidth-1].x+room.blockSize, room.block[room.worldHeight-1][0].y+room.blockSize);//bottom line
		room.draw(g);// Drawing the room.
		store.draw(g);//darwing the store
	}

	@Override
	public void run() {
		while (true) {
			if (!isFirst) {
				room.physic();

			}
			repaint();

			try {
				Thread.sleep(1);
			} catch (final InterruptedException e) {

			}
		}
	}

}
