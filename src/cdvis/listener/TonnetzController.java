package cdvis.listener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import cdvis.app.AppPanel;
import cdvis.app.Config;
import cdvis.component.Tonnetz;
import cdvis.sound.NotePlayer;

public class TonnetzController implements MouseListener, MouseMotionListener{
	private final Tonnetz net;
	private final AppPanel aPanel;
	private final NotePlayer player;
	
	private Point dragStart;
	private int lastNetX, lastNetY;
	
	private long lastFrame = 0;
	private long currentTime = 0;
	
	public TonnetzController(Tonnetz n, AppPanel a, NotePlayer p) {
		net = n;
		aPanel = a;
		player = p;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			net.press(e.getX(), e.getY());
			player.setNotes();
		}
		else {
			net.setRotationCenter(e.getX(), e.getY());
		}
		
		aPanel.repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		dragStart = e.getPoint();
		lastNetX = net.getNetX();
		lastNetY = net.getNetY();
		lastFrame = System.currentTimeMillis();
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
		net.moveNet(lastNetX + e.getX()-dragStart.x, lastNetY + e.getY()-dragStart.y);

		currentTime = System.currentTimeMillis();
		if (currentTime - lastFrame >= 10) {
			aPanel.repaint();
			lastFrame = currentTime;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

}
