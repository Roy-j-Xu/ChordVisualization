package cdvis.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import cdvis.component.Tonnetz;
import cdvis.listener.TonnetzController;



public class ControlPanel extends JPanel{
	private final JButton moveButton;

	public ControlPanel() {
		this.setPreferredSize(new Dimension(Config.SCREEN_WIDTH/4, Config.SCREEN_HEIGHT));
		this.setBackground(new Color(225,225,225));
		
		this.setLayout(new GridLayout(4,2));
		this.moveButton = new JButton("Move");
		moveButton.setActionCommand("move");
		this.add(moveButton);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
	}
	
	public JButton getMoveButton() {
		return moveButton;
	}

}
