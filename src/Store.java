import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Store {
	public static int shopWidth = 8;
	public static int buttonSize = 50;
	public static int cellSpace = 2;
	public static int awayFrom = 29;
	public static int iconSize = 20;
	public static int iconSpace = 6;
	public static int icoTextY = 13;
	public Rectangle[] button = new Rectangle[shopWidth];;
	public Rectangle buttonHealth;
	public Rectangle buttonCoins;

	public Store() {
		define();
	}

	public void define() {
		for (int i = 0; i < button.length; i++) {
			button[i] = new Rectangle(
					(Screen.myWidth / 2) - ((shopWidth * (buttonSize + cellSpace)) / 2)
							+ ((buttonSize + cellSpace) * i),
					(Screen.room.block[Screen.room.worldHeight - 1][0].y) + Screen.room.blockSize + cellSpace
							+ awayFrom,
					buttonSize, buttonSize);
		}
		buttonHealth = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y, iconSize, iconSize);
		buttonCoins = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y + button[0].height - iconSize, iconSize,
				iconSize);
	}

	public void draw(final Graphics g) {
		for (int i = 0; i < button.length; i++) {
			if (button[i].contains(Screen.mse)) {
				g.setColor(new Color(255, 255, 255, 100));
				g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
			}
			g.drawImage(Screen.tileset_res[0], button[i].x, button[i].y, button[i].width, button[i].height, null);
		}
		g.drawImage(Screen.tileset_res[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height,
				null);
		g.drawImage(Screen.tileset_res[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null);
		g.setFont(new Font("Courier new", Font.BOLD, 14));
		g.setColor(new Color(255, 255, 255));
		g.drawString("" + Screen.health, buttonHealth.x + buttonHealth.width + iconSpace, buttonHealth.y + icoTextY);
		g.drawString("" + Screen.coinage, buttonCoins.x + buttonCoins.width + iconSpace, buttonCoins.y + icoTextY);
	}

}
