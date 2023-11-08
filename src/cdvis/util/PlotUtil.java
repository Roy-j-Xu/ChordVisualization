package cdvis.util;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Color;

public class PlotUtil {

	private PlotUtil() {
	}
	
	public static void drawCenteredText(Graphics2D g2d, int x, int y, String text) {
		FontMetrics fm = g2d.getFontMetrics();
		int textWidth = fm.stringWidth(text);
		int textHeight = fm.getHeight();
		g2d.drawString(text, x-textWidth/2, y+textHeight/3);
	}

	public static void drawLeftAlignedText(Graphics2D g2d, int x, int y, String text) {
		FontMetrics fm = g2d.getFontMetrics();
		int textWidth = fm.stringWidth(text);
		int textHeight = fm.getHeight();
		g2d.drawString(text, x-textWidth, y+textHeight/3);
	}

	public static void drawBall(Graphics2D g2d, Color color, int x, int y, int size) {
		g2d.setColor(color);
		g2d.fillOval(x-size/2, y-size/2, size, size);
	}

	public static void drawPlayButton(Graphics2D g2d, Color color, int x, int y, int[] size) {
		g2d.setColor(color);
		int[] X = {x-size[0]/2,x-size[0]/2,x+size[0]/2};
		int[] Y = {y-size[1]/2,y+size[1]/2,y};
		g2d.fillPolygon(X,Y,3);
	}

	public static void drawStopButton(Graphics2D g2d, Color color, int x, int y, int[] size) {
		g2d.setColor(color);
		g2d.fillRect(x-size[0]/2, y-size[1]/2, size[0], size[1]);
	}

	public static void drawPauseButton(Graphics2D g2d, Color color, int x, int y, int[] size) {
		g2d.setColor(color);
		g2d.fillRect(x-size[0]/2, y-size[1]/2, size[0]/3, size[1]);
		g2d.fillRect(x+size[0]/6, y-size[1]/2, size[0]/3, size[1]);
	}

	public static String getTimeString(long milliseconds) {
		long totalSeconds = milliseconds / 1000000;
		long seconds = totalSeconds % 60;
		long minutes = (totalSeconds / 60) % 60;
		long hours = totalSeconds / (60 * 60);

		if (hours == 0) {
			return String.format("%01d:%02d", minutes, seconds);
		} else {
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		}
	}

//	public static int bounce(int value, double ratio, double completeness) {
//		completeness = Math.min(completeness, 1);
//		int result = (int) ( value * (1 + ratio*Math.sin(completeness*Math.PI)) );
//		return result;
//	}
//
//	public static int smoothTransition(int start, int end, double completeness) {
//		completeness = Math.min(completeness, 1);
//		int result = (int) (start + (end-start)*Math.sin(completeness*Math.PI/2));
//		return result;
//	}

}
