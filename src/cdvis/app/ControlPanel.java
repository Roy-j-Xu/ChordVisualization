package cdvis.app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
		
		this.setLayout(new GridLayout(5,2));
		
		buttons = new JButton[9];
		buttons[0] = addButton("Move Down by P5", "-7");
		buttons[1] = addButton("Move Up by P5", "7");
		buttons[2] = addButton("Move Down by m3", "-3");
		buttons[3] = addButton("Move Up by m3", "3");
		buttons[4] = addButton("Move Down by M3", "-4");
		buttons[5] = addButton("Move Up by M3", "4");
		buttons[6] = addButton("Rotate counterclockwise", "1");
		buttons[7] = addButton("Rotate clockwise", "-1");
		buttons[8] = addButton("Clear", "0");

	}
	
	private JButton addButton(String text, String command) {
		JButton b = new JButton("<html><div style='padding: 10px;'>" + text + "</div><html>");
		b.setFont(new Font("Arial", Font.BOLD, 14));
		b.setActionCommand(command);
		b.setContentAreaFilled(false);
		this.add(b);
		return b;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
	}
	
	public JButton[] getButtons() {
		return buttons;
	}

}
