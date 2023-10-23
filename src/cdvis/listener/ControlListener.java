package cdvis.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cdvis.app.AppPanel;
import cdvis.component.Tonnetz;
import cdvis.sound.NotePlayer;

public class ControlListener implements ActionListener {
	private final Tonnetz net;
	private final AppPanel aPanel;
	private final NotePlayer player;


	public ControlListener(Tonnetz n, AppPanel a, NotePlayer p) {
		net = n;
		aPanel = a;
		player = p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int command = Integer.parseInt(e.getActionCommand());
		if (command * command == 1) {
			net.rotateNotes(command);
		}
		else if (command == 0) {
			net.clearNote();
		}
		else {
			net.moveNotes(command);
		}
		
		aPanel.repaint();
		player.setNotes();
	}

}
