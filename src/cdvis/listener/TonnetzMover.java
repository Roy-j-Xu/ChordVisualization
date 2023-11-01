package cdvis.listener;

import cdvis.app.AppPanel;
import cdvis.app.Config;
import cdvis.component.MusicalNet;
import cdvis.component.Tonnetz;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class TonnetzMover implements MouseListener, MouseMotionListener, MouseWheelListener{
	private final MusicalNet net;
	private final AppPanel aPanel;

	private Point dragStart;
	private int lastNetX, lastNetY;

	private long lastFrame = 0;

	public TonnetzMover(Tonnetz n, AppPanel a) {
		net = n;
		aPanel = a;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
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
