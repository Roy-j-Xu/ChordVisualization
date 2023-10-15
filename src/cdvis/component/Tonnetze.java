package cdvis.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import cdvis.app.Config;
import cdvis.util.MusicUtil;

public class Tonnetze{
	private int noteRange = 88;
	private Button[] noteList;
	private int[] noteX, noteY;
	private int netX = 60;
	private int netY = 60;
	

	public Tonnetze() {
		noteList = new Button[88];
		noteX = new int[noteRange];
		noteY = new int[noteRange];
		layoutNet();
		generateButtons();
	}
	
	private void layoutNet() {
		for (int note = 0; note < noteRange; note++) {
			noteY[note] = (3 + 2 * (note % 7)) % 7;
			if (note % 7 == 6 || note % 7 == 0 || note % 7 == 1) {
				noteX[note] = ((note + 1)/7) * 2;
			} else {
				noteX[note] = (note/7) * 2 + 1;
			}
		}

		int gap = Config.BUTTON_SIZE * 2;
		
		for (int note = 0; note < noteRange; note++) {
			noteX[note] = noteX[note] * (gap/2);
			noteY[note] = (int) (noteY[note] * 0.866 * gap);
		}
		
	}
	
	private void generateButtons() {
		for (int note = 0; note < noteRange; note++) {
			noteList[note] = new Button(netX + noteX[note], netY + noteY[note], MusicUtil.pitchClass(note));
		}
	}
	
	public void plot(Graphics2D g) {
    	g.setStroke(new BasicStroke(Config.BUTTON_SIZE/6));
    	g.setPaint(new Color(180,180,180));
    	int mod = 0;
		for (int note = 0; note + 3 < noteRange; note++) {
			mod = note % 7;
			if (mod != 2) {
				g.drawLine(netX + noteX[note], netY + noteY[note], netX + noteX[note+3], netY + noteY[note+3]);
			}
			if (note + 4 < noteRange && mod != 5) {
				g.drawLine(netX + noteX[note], netY + noteY[note], netX + noteX[note+4], netY + noteY[note+4]);
			}
			if (note + 7 < noteRange) {
				g.drawLine(netX + noteX[note], netY + noteY[note], netX + noteX[note+7], netY + noteY[note+7]);
			}

		}
		
		for (Button b : noteList) {
			b.plot(g);
		}
	}
	
	public void moveNet(int X, int Y) {
		netX = X;
		netY = Y;
		for (int note = 0; note < noteRange; note++) {
			noteX[note] = noteX[note] + netX;
			noteY[note] = noteY[note] + netY;
			noteList[note].setXY(noteX[note], noteY[note]);
		}
	}
	
	public void press(int X, int Y) {
		for (Button b : noteList) {
			b.press(X, Y);
		}
	}

}
