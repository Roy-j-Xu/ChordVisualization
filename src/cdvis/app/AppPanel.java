package cdvis.app;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import cdvis.component.Tonnetz;
import cdvis.listener.TonnetzController;



public class AppPanel extends JPanel{
	
	private Tonnetz net;

	public AppPanel() {
		net = new Tonnetz();
		this.setPreferredSize(new Dimension(Config.SCREEN_HEIGHT,Config.SCREEN_WIDTH));
		
		TonnetzController TController = new TonnetzController(net);
		this.addMouseListener(TController);
		this.addKeyListener(TController);
		this.addMouseMotionListener(TController);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		net.plot(g2d);
	}

}
