package cdvis.listener;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import cdvis.component.Tonnetz;
import cdvis.sound.NotePlayer;

public class TonnetzController implements MouseListener, MouseMotionListener{
	private final Tonnetz tonnetz;
	private final NotePlayer player;
	private Point dragStart;
	private int lastNetX, lastNetY;
	
	public TonnetzController(Tonnetz tonnetz, NotePlayer player) {
		this.tonnetz = tonnetz;
		this.player = player;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		tonnetz.press(e.getX(), e.getY());
		player.setNotes(tonnetz.pressedKeys());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		dragStart = e.getPoint();
		lastNetX = tonnetz.getNetX();
		lastNetY = tonnetz.getNetY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
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
	public void mouseDragged(MouseEvent e) {
		tonnetz.moveNet(lastNetX + e.getX()-dragStart.x, lastNetY + e.getY()-dragStart.y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

}
