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
	private final JButton[] buttons;

	public ControlPanel() {
		this.setPreferredSize(new Dimension(Config.SCREEN_WIDTH/4, Config.SCREEN_HEIGHT));
		this.setBackground(new Color(225,225,225));
		
		this.setLayout(new GridLayout(3,2));
		
		buttons = new JButton[6];
		this.buttons[0] = new JButton("Move Down by P5");
		this.buttons[1] = new JButton("Move Up by P5");
		this.buttons[2] = new JButton("Move Down by m3");
		this.buttons[3] = new JButton("Move Up by m3");
		this.buttons[4] = new JButton("Move Down by M3");
		this.buttons[5] = new JButton("Move Up by M3");
		buttons[0].setActionCommand("-7");
		buttons[1].setActionCommand("7");
		buttons[2].setActionCommand("-3");
		buttons[3].setActionCommand("3");
		buttons[4].setActionCommand("-4");
		buttons[5].setActionCommand("4");
		for (JButton b:buttons) {
			this.add(b);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
	}
	
	public JButton[] getButtons() {
		return buttons;
	}

}
