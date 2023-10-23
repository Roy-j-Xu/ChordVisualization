package cdvis.app;

import javax.swing.JButton;

import cdvis.component.Tonnetz;
import cdvis.listener.ControlListener;
import cdvis.listener.TonnetzController;
import cdvis.sound.NotePlayer;

public class Main {
	private AppPanel aPanel;
	private ControlPanel cPanel;
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
		new AppFrame(aPanel, cPanel);
		
		addListeners();
	}
	
	public void addListeners() {
		TonnetzController TController = new TonnetzController(net, aPanel, player);
		aPanel.addMouseListener(TController);
		aPanel.addMouseMotionListener(TController);
		
		ControlListener CListener = new ControlListener(net, aPanel , player);
		for (JButton b : cPanel.getButtons()) {
			b.addActionListener(CListener);
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
