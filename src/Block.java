import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends Rectangle {
	public Rectangle towerSquare;
	public int towerSquareSize = 130;
	public int groundID;
	public int airID;
	public int loseTime = 100, loseFrame = 0;

	public int shotMob = -1;
	public boolean shooting = false;

	public Block(final int x, final int y, final int width, final int height, final int groundID, final int airID) {
		setBounds(x, y, width, height);
		this.groundID = groundID;
		this.airID = airID;
		towerSquare = new Rectangle(x - towerSquareSize / 2, y - towerSquareSize / 2, width + towerSquareSize,
				height + towerSquareSize);
	}

	public void draw(final Graphics g) {
		g.drawImage(Screen.tileset_ground[groundID], x, y, width, height, null);

		if (airID != Value.airAir) {
			g.drawImage(Screen.tileset_air[airID], x, y, width, height, null);
			if (airID == Value.airTowerLaser) {
				g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
			}
		}
	}

	public void physic() {
		if (shotMob != -1 && towerSquare.intersects(Screen.mobs[shotMob])) {
			shooting = true;
		} else {
			shooting = false;
		}
		if (!shooting) {
			if (airID == Value.airTowerLaser) {
				for (int i = 0; i < Screen.mobs.length; i++) {
					if (Screen.mobs[i].inGame) {
						if (towerSquare.intersects(Screen.mobs[i])) {
							shooting = true;
							shotMob = i;
						}
					}
				}
			}
		}

		if (shooting) {
			if (loseFrame >= loseTime) {
				Screen.mobs[shotMob].loseHealth(1);
				loseFrame = 0;
			} else {
				loseFrame += 1;
			}

			if (Screen.mobs[shotMob].isDead()) {
				getMoney(Screen.mobs[shotMob].mobID);

				shooting = false;
				shotMob = -1;
				Screen.killed += 1;
				Screen.hasWon();
			}
		}

	}

	public void getMoney(final int mobID) {
		Screen.coinage += Value.deathReward[mobID];
	}

	public void fight(final Graphics g) {
		if (Screen.isDebug) {
			if (airID == Value.airTowerLaser) {
				g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
			}
		}
		if (shooting) {
			g.setColor(new Color(10, 10, 250));
			g.drawLine(x + (width / 2), y + (height / 2), Screen.mobs[shotMob].x + (Screen.mobs[shotMob].width / 2),
					Screen.mobs[shotMob].y + (Screen.mobs[shotMob].height / 2));
		}

	}
}