package cdvis.app;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import cdvis.component.Tonnetz;
import cdvis.listener.TonnetzController;



public class AppPanel extends JPanel{
	
	private final Tonnetz net;

	public AppPanel(Tonnetz n) {
		net = n;
		this.setPreferredSize(new Dimension(Config.SCREEN_WIDTH*3/4, Config.SCREEN_HEIGHT));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		net.plot(g2d);
	}

}
