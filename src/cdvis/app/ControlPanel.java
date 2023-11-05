package cdvis.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ControlPanel extends JPanel{
	private final JButton[] buttons;
	private final String[][] buttonDescriptions = {
					{"Move Down by P5", "Move Up by P5", "Move Down by M3", "Move Up by M3",
							"Move Down by m3", "Move Up by m3", "Rotate counterclockwise", "Rotate clockwise"},
					{"To Upper-left" , "To Upper-right", "To the Left", "To the Right",
							"To Lower-left", "To Lower-right", "Rotate counterclockwise", "Rotate clockwise"},
					{"Move Down by P5", "Move Up by P5", "Move Down by M3", "Move Up by M3",
							"Move Down by m3", "Move Up by m3", "", ""}
			};

	public ControlPanel() {
		this.setBounds(0, Config.SCREEN_HEIGHT*2/5, Config.SCREEN_WIDTH/4, Config.SCREEN_HEIGHT/2);
		this.setBackground(new Color(225,225,225));
		
		this.setLayout(new GridLayout(5,2));
		
		buttons = new JButton[9];
		buttons[0] = addButton("Move Down by m3", "-3");
		buttons[1] = addButton("Move Up by M3", "4");
		buttons[2] = addButton("Move Down by P5", "-7");
		buttons[3] = addButton("Move Up by P5", "7");
		buttons[4] = addButton("Move Down by M3", "-4");
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

	private void changeButtonDescription(String[] description) {
		for (int i = 0; i <= 7; i++) {
			buttons[i].setText("<html><div style='padding: 10px;'>" + description[i] + "</div><html>");
		}
	}

	public void changeMusicalNet(int netIndex) {
				changeButtonDescription(buttonDescriptions[netIndex]);
	}
	
	public JButton[] getButtons() {
		return buttons;
	}

}
