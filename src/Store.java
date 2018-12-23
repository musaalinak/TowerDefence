import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Store {
	public static int shopWidth=8;
	public static int buttonSize=50;
	public static int cellSpace=2;
	public static int awayFrom=29;
	public Rectangle[] button=new Rectangle[shopWidth];;
	public Store()
	{
		define();
	}
	public void define()
	{
		for(int i=0;i<button.length;i++)
		{
			button[i] = new Rectangle((Screen.myWidth/2)-((shopWidth*(buttonSize+cellSpace))/2)+((buttonSize+cellSpace)*i),(Screen.room.block[Screen.room.worldHeight-1][0].y)+Screen.room.blockSize+cellSpace+awayFrom,buttonSize,buttonSize);
		}
	}
	public void draw (Graphics g)
	{
		for(int i=0;i<button.length;i++)
		{
			if(button[i].contains(Screen.mse))
			{
				g.setColor(new Color(255,255,255,100));
				g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
			}
			g.drawImage(Screen.tileset_res[0],button[i].x, button[i].y, button[i].width, button[i].height,null);
		}
		
	}

}
