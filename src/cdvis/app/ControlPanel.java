package cdvis.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ControlPanel extends JPanel{
	private final JButton[] buttons;

	public ControlPanel() {
		this.setBounds(0, Config.SCREEN_HEIGHT*2/5, Config.SCREEN_WIDTH/4, Config.SCREEN_HEIGHT/2);
		this.setBackground(new Color(225,225,225));
		
		this.setLayout(new GridLayout(5,2));
		
		buttons = new JButton[9];
		buttons[0] = addButton("Move Down by P5", "-7");
		buttons[1] = addButton("Move Up by P5", "7");
		buttons[2] = addButton("Move Down by M3", "-4");
		buttons[3] = addButton("Move Up by M3", "4");
		buttons[4] = addButton("Move Down by m3", "-3");
		buttons[5] = addButton("Move Up by m3", "3");
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
	
	public JButton[] getButtons() {
		return buttons;
	}

}
