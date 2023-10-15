package cdvis.util;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class PlotUtil {

	private PlotUtil() {
	}
	
	public static void drawCenteredText(Graphics2D g2d, int x, int y, String text) {
		FontMetrics fm = g2d.getFontMetrics();
		int textWidth = fm.stringWidth(text);
		int textHeight = fm.getHeight();
		g2d.drawString(text, x-textWidth/2, y+textHeight/3);
	}

}
