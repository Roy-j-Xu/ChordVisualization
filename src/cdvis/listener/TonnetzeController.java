package cdvis.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cdvis.component.Tonnetze;
import cdvis.sound.NotePlayer;

public class TonnetzeController implements MouseListener{
	private final Tonnetze tonnetze;
	private final NotePlayer player;
	
	public TonnetzeController(Tonnetze tonnetze) {
		this.tonnetze = tonnetze;
		this.player = new NotePlayer();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		tonnetze.press(e.getX(), e.getY());
		player.setNotes(tonnetze.pressedKeys());
		
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
