package cdvis.app;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import cdvis.listener.ControlListener;
import cdvis.listener.TonnetzController;
import cdvis.sound.NotePlayer;

public class AppFrame extends JFrame implements Runnable{
	
	private AppPanel aPanel;
	private ControlPanel cPanel;
	private NotePlayer player;
	
	
	private Thread panelThread;
	
	public AppFrame() {
		setUp();
		addListeners();
		startPanelLoop();
	}
	
	
	public void setUp() {
		this.setTitle("Chord Visualization");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.player = new NotePlayer(); 
		this.aPanel = new AppPanel();
		this.cPanel = new ControlPanel();
		this.add(aPanel, BorderLayout.EAST);
		this.add(cPanel, BorderLayout.WEST);
		this.pack();
		this.setVisible(true);
		
		try {
			this.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void addListeners() {
		TonnetzController TController = new TonnetzController(aPanel.getTonnetz(), player);
		this.aPanel.addMouseListener(TController);
		this.aPanel.addMouseMotionListener(TController);
		new ControlListener(aPanel.getTonnetz(), cPanel , player);
	}
	
	public void startPanelLoop() {
		panelThread = new Thread(this);
		panelThread.start();
	}
	
	@Override
	public void run() {
		long lastFrame = 0;
		long currentTime = 0;
		while(true) {
			currentTime = System.currentTimeMillis();
			if (currentTime - lastFrame >= 12) {
				aPanel.repaint();
				lastFrame = currentTime;
			}
		}
	}

}
