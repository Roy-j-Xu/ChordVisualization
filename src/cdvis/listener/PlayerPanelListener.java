package cdvis.listener;

import cdvis.app.PlayerPanel;

import java.awt.event.*;


public class PlayerPanelListener implements MouseListener, MouseMotionListener {
	private final PlayerPanel pPanel;

//	private Point dragStart;
//	private int lastNetX, lastNetY;
//
//	private long lastFrame = 0;

	public PlayerPanelListener(PlayerPanel p) {
		pPanel = p;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
        pPanel.click(e.getX(),e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		dragStart = e.getPoint();
//		lastNetX = net.getNetX();
//		lastNetY = net.getNetY();
//		lastFrame = System.currentTimeMillis();
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
//		net.moveNet(lastNetX + e.getX()-dragStart.x, lastNetY + e.getY()-dragStart.y);
//
//		long currentTime = System.currentTimeMillis();
//		if (currentTime - lastFrame >= 10) {
//			pPanel.repaint();
//			lastFrame = currentTime;
//		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
