package cdvis;

import javax.swing.JButton;

import cdvis.app.AppFrame;
import cdvis.app.AppPanel;
import cdvis.app.ControlPanel;
import cdvis.component.ChordLabel;
import cdvis.component.Tonnetz;
import cdvis.listener.ControlListener;
import cdvis.listener.TonnetzController;
import cdvis.sound.NotePlayer;

public class Main {
	private AppPanel aPanel;
	private ControlPanel cPanel;
	private ChordLabel cLabel;
	private Tonnetz net;
	private NotePlayer player;
	
	public Main() {
		init();
	}

	public void init() {
		net = new Tonnetz();
		player = new NotePlayer(net.getPressedKey());
		aPanel = new AppPanel(net);
		cPanel = new ControlPanel();
		cLabel = new ChordLabel(net);
		new AppFrame(aPanel, cPanel, cLabel);
		
		addListeners();
	}
	
	public void addListeners() {
		TonnetzController TController = new TonnetzController(net, aPanel, player, cLabel);
		aPanel.addMouseListener(TController);
		aPanel.addMouseMotionListener(TController);
		aPanel.addMouseWheelListener(TController);
		
		ControlListener CListener = new ControlListener(net, aPanel, player, cLabel);
		for (JButton b : cPanel.getButtons()) {
			b.addActionListener(CListener);
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
