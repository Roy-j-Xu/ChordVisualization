package cdvis.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cdvis.app.AppPanel;
import cdvis.component.ChordLabel;
import cdvis.component.Tonnetz;
import cdvis.sound.NotePlayer;


public class TonnetzController implements MouseListener{
	private final Tonnetz net;
	private final AppPanel aPanel;
	private final NotePlayer player;
	private final ChordLabel cLabel;

	public TonnetzController(Tonnetz n, AppPanel a, NotePlayer p, ChordLabel c) {
		net = n;
		aPanel = a;
		player = p;
		cLabel = c;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (net.press(e.getX(), e.getY())) player.setNotes();
			cLabel.repaint();
		}
		else {
			net.setRotationCenter(e.getX(), e.getY());
		}
		aPanel.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
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


}
