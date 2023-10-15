package cdvis.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cdvis.component.Tonnetze;

public class TonnetzeController implements MouseListener{
	private final Tonnetze tonnetze;
	
	public TonnetzeController(Tonnetze tonnetze) {
		this.tonnetze = tonnetze;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		tonnetze.press(e.getX(), e.getY());
		
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
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
