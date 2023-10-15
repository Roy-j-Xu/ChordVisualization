package cdvis.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.jfugue.player.Player;

import cdvis.app.Config;
import cdvis.util.PlotUtil;

public class Button {
	
	private int x, y;
	private String text;
	private boolean pressed = false;

	public Button(int x, int y , String text) {
		this.x = x;
		this.y = y;
		this.text = text;
	}
	
	public void plot(Graphics2D g) {
		Color color;
		if (pressed) {
			color = new Color(100,170,100);
		} else color = new Color(100,200,100);
		g.setColor(color);
		
		g.fillOval(x - Config.BUTTON_SIZE/2, y - Config.BUTTON_SIZE/2, Config.BUTTON_SIZE, Config.BUTTON_SIZE);
		g.setFont(new Font("Arial", Font.BOLD, Config.BUTTON_SIZE/5 * 3));
		g.setColor(new Color(0, 130, 0));
		PlotUtil.drawCenteredText(g, x, y, text);
	}
	
	public void press(int X, int Y) {
		if( (x-X)*(x-X) + (y-Y)*(y-Y) < Config.BUTTON_SIZE * Config.BUTTON_SIZE ) {
			this.pressed = ! this.pressed;
		}
	}
	
	public boolean isPressed() {
		return this.pressed;
	}
	
	public void setXY(int X, int Y) {
		x = X;
		y = Y;
	}

}
