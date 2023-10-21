package cdvis.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import cdvis.app.Config;
import cdvis.util.MusicUtil;
import cdvis.util.PlotUtil;

public class Tonnetz extends Graph{
	private int noteRange = 88;
	private int[] relNoteX, relNoteY;
	private int[] noteX, noteY;
	private boolean[] keyPressed;
	private int netX = 60, netY = 60;
	

	public Tonnetz() {
		keyPressed = new boolean[noteRange];
		noteX = new int[noteRange];
		relNoteX = new int[noteRange];
		noteY = new int[noteRange];
		relNoteY = new int[noteRange];
		generateButtons();
		layoutNet();
	}
	
	private void layoutNet() {
		for (int note = 0; note < noteRange; note++) {
			relNoteY[note] = (3 + 2 * (note % 7)) % 7;
			if (note % 7 == 6 || note % 7 == 0 || note % 7 == 1) {
				relNoteX[note] = ((note + 1)/7) * 2;
			} else {
				relNoteX[note] = (note/7) * 2 + 1;
			}
		}

		int gap = Config.BUTTON_SIZE * 2;
		
		for (int note = 0; note < noteRange; note++) {
			relNoteX[note] = relNoteX[note] * (gap/2);
			relNoteY[note] = (int) (relNoteY[note] * 0.866 * gap);
		}
		
	}
	
	private void generateButtons() {
		for (int note = 0; note < noteRange; note++) {
			this.addVertex(note);
		}
		int mod = 0;
		for (int note = 0; note < noteRange-3; note++){
			mod = note % 7;
			if (mod != 2) {
				this.addEdge(note, note+3);
			}
			if (note + 4 < noteRange && mod != 5) {
				this.addEdge(note, note+4);
			}
			if (note + 7 < noteRange) {
				this.addEdge(note, note+7);
			}
		}
	}
	
    public void plot(Graphics2D g2d) {
    	int buttonSize = Config.BUTTON_SIZE;
    	
    	g2d.setStroke(new BasicStroke(buttonSize/7));
    	g2d.setPaint(Color.gray);
    	
    	for (int i = 0; i < noteRange; i++) {
    		noteX[i] = netX + relNoteX[i];
    		noteY[i] = netY + relNoteY[i];
    	}
    	
    	for (int i = 0; i < noteRange; i++) {
    		for (int j : this.getNeighbors(i)) {
    			g2d.drawLine(noteX[i], noteY[i], noteX[j], noteY[j]);
    		}
    	}

    	Color unpressedColor = new Color(100,200,100);
    	Color pressedColor = new Color(80,150,80);
    	
    	for (int i = 0; i < noteRange; i++) {
    		if (keyPressed[i]) {
    			g2d.setPaint(pressedColor);
    		} else {
    			g2d.setPaint(unpressedColor);
    		}
    		g2d.fillOval(noteX[i] - buttonSize/2, noteY[i] - buttonSize/2, buttonSize, buttonSize);
    		g2d.setFont(new Font("Arial", Font.BOLD, buttonSize/5 * 3));
    	}
    	g2d.setColor(new Color(0, 130, 0));
    	for (int i = 0; i < noteRange; i++) {
    		PlotUtil.drawCenteredText(g2d, noteX[i], noteY[i], MusicUtil.pitchClass(i));
    	}
    	
    }
	
	public void moveNet(int x, int y) {
		netX = x;
		netY = y;
	}
	
	public int getNetX() {
		return netX;
	}
	
	public int getNetY() {
		return netY;
	}
    
    public void press(int x, int y) {
    	int buttonSize = Config.BUTTON_SIZE;
    	for (int i = 0; i < noteRange; i++) {
    		if (Math.abs(x-noteX[i]) < buttonSize && Math.abs(y-noteY[i]) < buttonSize) {
    			keyPressed[i] = !keyPressed[i];
    		}
    	}
    }
	
	public String pressedKeys() {
		return MusicUtil.chordString(keyPressed);
	}

}
