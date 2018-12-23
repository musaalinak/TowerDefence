import java.awt.event.*;
import java.awt.*;
public class KeyHandel implements MouseMotionListener,MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		Screen.mse =new Point((e.getX())+ ((Frame.size.width-Screen.myWidth)/2),(e.getY())+((Frame.size.height-Screen.myHeight)/2));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		Screen.mse =new Point((e.getX())- ((Frame.size.width-Screen.myWidth)/2),(e.getY())-((Frame.size.height-Screen.myHeight)/2));
	}

}
