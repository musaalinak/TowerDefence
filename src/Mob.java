import java.awt.Graphics;
import java.awt.Rectangle;

public class Mob extends Rectangle {
	public int xC, yC;
	public int mobSize = 52;
	public int mobWalk = 0;
	public int upward = 0, downward = 1, right = 2;
	public int direction = right;
	public int mobID = Value.mobAir;
	public boolean inGame = false;
	public boolean hasUpward = false;
	public boolean hasDownward = false;

	public Mob() {

	}

	public void spawnMob(final int mobID) {
		for (int y = 0; y < Screen.room.block.length; y++) {
			if (Screen.room.block[y][0].groundID == Value.groundRoad) {
				setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y, mobSize, mobSize);
				xC = 0;
				yC = y;
			}
		}
		this.mobID = mobID;
		inGame = true;
	}

	public int walkFrame = 0;
	public int walkSpeed = 40;

	public void physic() {
		if (walkFrame >= walkSpeed) {
			if (direction == right) {
				x = x + 1;
			} else if (direction == upward) {
				y -= 1;
			} else if (direction == downward) {
				y += 1;
			}
			mobWalk += 1;
			if (mobWalk == Screen.room.blockSize) {
				if (direction == right) {
					xC += 1;
				} else if (direction == upward) {
					yC -= 1;
					hasUpward = true;
				} else if (direction == downward) {
					yC += 1;
					hasDownward = true;
				}
				try {
					if (Screen.room.block[yC + 1][xC].groundID == Value.groundRoad) {
						direction = downward;
					}
				} catch (final Exception e) {
				}
				try {
					if (Screen.room.block[yC - 1][xC].groundID == Value.groundRoad) {
						direction = upward;
					}
				} catch (final Exception e) {
				}
				try {
					if (Screen.room.block[yC][xC + 1].groundID == Value.groundRoad) {
						direction = right;
					}
				} catch (final Exception e) {
				}
				mobWalk = 0;
			}
			walkFrame = 0;
		} else {
			walkFrame += 1;
		}

	}

	public void draw(final Graphics g) {
		g.drawImage(Screen.tileset_mob[mobID], x, y, width, height, null);

	}
}
