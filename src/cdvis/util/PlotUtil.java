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
	
	public static int bounce(int value, double ratio, double completeness) {
		completeness = Math.min(completeness, 1);
		int result = (int) ( value * (1 + ratio*Math.sin(completeness*Math.PI)) );
		return result;
	}
	
	public static int smoothTransition(int start, int end, double completeness) {
		completeness = Math.min(completeness, 1);
		int result = (int) (start + (end-start)*Math.sin(completeness*Math.PI/2));
		return result;
	}

}
