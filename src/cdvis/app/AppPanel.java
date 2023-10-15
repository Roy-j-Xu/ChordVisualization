package cdvis.app;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import cdvis.component.Button;
import cdvis.component.Tonnetze;
import cdvis.listener.TonnetzeController;



public class AppPanel extends JPanel{
	
	private Tonnetze net;

	public AppPanel() {
		net = new Tonnetze();
		this.setPreferredSize(new Dimension(Config.SCREEN_HEIGHT,Config.SCREEN_WIDTH));
		
		this.addMouseListener(new TonnetzeController(net));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		net.plot(g2d);
	}

}
