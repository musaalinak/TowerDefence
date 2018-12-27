import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Mob extends Rectangle {
	public int xC, yC;
	public int mobSize = 52;
	public int mobWalk = 0;
	public int healthSpace=3,healthHeight=6;
	public int upward = 0, downward = 1, right = 2 , left=3;
	public int direction = right;
	public int mobID = Value.mobAir;
	public boolean inGame = false;
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasLeft = false;
	public boolean hasRight = false;
	public int health;
	
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
		this.health=mobSize;
	}

				public void deleteMob() {
					inGame = false;
					direction = right;
					mobWalk = 0 ;
					Screen.room.block[0][0].getMoney(mobID);
			}
			
				public void looseHealth() {
				
					Screen.health -=1;
			}
	
	public int walkFrame = 0;
	public int walkSpeed = 30;

	public void physic() {
		if (walkFrame >= walkSpeed) {
			if (direction == right) {
				x = x + 1;
			} else if (direction == upward) {
				y -= 1;
			} else if (direction == downward) {
				y += 1;
			}
			else if (direction == left) {
				x -= 1;
			}
			mobWalk += 1;
			if (mobWalk == Screen.room.blockSize) {
				if (direction == right) {
					xC += 1;
					hasRight = true;
				} else if (direction == upward) {
					yC -= 1;
					hasUpward = true;
				} else if (direction == downward) {
					yC += 1;
					hasDownward = true;
				} else if(direction == left) {
					xC -=1;
					hasLeft = true;
				}
		if(!hasUpward) {
				try {
				if (Screen.room.block[yC + 1][xC].groundID == Value.groundRoad) {
						direction = downward;
							}
					} catch (final Exception e) {} 
				}
		if(!hasDownward) {
				try {
				if (Screen.room.block[yC - 1][xC].groundID == Value.groundRoad) {
						direction = upward;
							}
					} catch (final Exception e) {}
				}
		if(!hasLeft) {
				try {
				if (Screen.room.block[yC][xC + 1].groundID == Value.groundRoad) {
						direction = right;
							}
					} catch (final Exception e) {}
				}
		if(!hasRight) {
			try {
			if (Screen.room.block[yC][xC - 1].groundID == Value.groundRoad) {
					direction = left;
						}
				} catch (final Exception e) {}
			}
		
				if(Screen.room.block[yC][xC].airID== Value.airCave) {
					deleteMob();
					looseHealth();
				}
					
				hasUpward = false;
				hasDownward = false;
				hasLeft = false;
				hasRight = false;
				mobWalk = 0;
			}
			walkFrame = 0;
		} else {
			walkFrame += 1;
		}

	}
	
	public void loseHealth(int amo) {
		health -= amo;
		
		checkDeath();
		
	}
	public void checkDeath() {
		if(health == 0) {
			deleteMob();
		}
	}
	public boolean isDead() {
		if(inGame) {
			return false;
		}else {
			return true;
		}
	}

	public void draw(final Graphics g) {
		g.drawImage(Screen.tileset_mob[mobID], x, y, width, height, null);
		
		//Health Bar..
		g.setColor(new Color (10,250,10));
		g.fillRect(x, y-(healthSpace+healthHeight), width, healthHeight);
		
		g.setColor(new Color (250,10,10));
		g.fillRect(x, y-(healthSpace+healthHeight), health, healthHeight);
		
		g.setColor(new Color(0,0,0));
		g.drawRect(x, y-(healthSpace+healthHeight), health-1, healthHeight-1);

	}
}
