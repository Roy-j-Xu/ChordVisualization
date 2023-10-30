package cdvis.listener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import cdvis.app.AppPanel;
import cdvis.app.Config;
import cdvis.component.ChordLabel;
import cdvis.component.Tonnetz;
import cdvis.sound.NotePlayer;


public class TonnetzController implements MouseListener, MouseMotionListener, MouseWheelListener{
	private final Tonnetz net;
	private final AppPanel aPanel;
	private final NotePlayer player;
	private final ChordLabel cLabel;
	
	private Point dragStart;
	private int lastNetX, lastNetY;
	
	private long lastFrame = 0;

	public TonnetzController(Tonnetz n, AppPanel a, NotePlayer p, ChordLabel c) {
		net = n;
		aPanel = a;
		player = p;
		cLabel = c;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			net.press(e.getX(), e.getY());
			player.setNotes();
			cLabel.repaint();
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
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		net.moveNet(lastNetX + e.getX()-dragStart.x, lastNetY + e.getY()-dragStart.y);

		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFrame >= 10) {
			aPanel.repaint();
			lastFrame = currentTime;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		Config.BUTTON_SIZE += notches*3;
		if (Config.BUTTON_SIZE < 0) {
			Config.BUTTON_SIZE = 0;
			return;
		}

		Point mousePoint = e.getPoint();
		int X = net.getNetX();
		int Y = net.getNetY();
		double ratio = (double)Config.BUTTON_SIZE/(Config.BUTTON_SIZE - notches*3);
		X = (int) (ratio*(X - mousePoint.x)) + mousePoint.x;
		Y = (int) (ratio*(Y - mousePoint.y)) + mousePoint.y;
		net.moveNet(X,Y);

		aPanel.repaint();
	}

}
