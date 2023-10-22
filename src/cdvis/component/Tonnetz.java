package cdvis.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import cdvis.app.Config;
import cdvis.util.MusicUtil;
import cdvis.util.PlotUtil;

public class Tonnetz extends Graph{
	private int noteRange = 7*11;
	private int[] relNoteX, relNoteY;
	private int[] noteX, noteY;
	private Set<Integer> pressedKey;
	private int netX = -100, netY = 60;
	

	public Tonnetz() {
		pressedKey = new HashSet<>();
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

		int gap = Config.BUTTON_SIZE * 11 / 5;
		
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
    	
    	for (int i = 0; i < noteRange; i++) {
    		noteX[i] = netX + relNoteX[i];
    		noteY[i] = netY + relNoteY[i];
    	}
    	
    	g2d.setStroke(new BasicStroke(buttonSize/7));
    	Color unpressedColor = new Color(190,190,190);
    	Color pressedColor = Color.gray;
    	for (int i = 0; i < noteRange; i++) {
    		for (int j : this.getNeighbors(i)) {
        		if (pressedKey.contains(i) && pressedKey.contains(j)) {
        			g2d.setPaint(pressedColor);
        		} else {
        			g2d.setPaint(unpressedColor);
        		}
    			g2d.drawLine(noteX[i], noteY[i], noteX[j], noteY[j]);
    		}
    	}

    	unpressedColor = new Color(100,200,100);
    	pressedColor = new Color(80,150,80);
    	g2d.setPaint(unpressedColor);
    	for (int i = 0; i < noteRange; i++) {
    		if (!pressedKey.contains(i)) {
    			g2d.fillOval(noteX[i] - buttonSize/2, noteY[i] - buttonSize/2, buttonSize, buttonSize);
    		}
    	}
    	g2d.setPaint(pressedColor);
    	for (int i : pressedKey) {
    		g2d.fillOval(noteX[i] - buttonSize/2, noteY[i] - buttonSize/2, buttonSize, buttonSize);
    	}
    	
    	g2d.setFont(new Font("Arial", Font.BOLD, buttonSize/5 * 3));
    	g2d.setColor(new Color(0, 130, 0));
    	for (int i = 0; i < noteRange; i++) {
    		PlotUtil.drawCenteredText(g2d, noteX[i], noteY[i], MusicUtil.pitchClass(i));
    	}
    	
    }
    
    public void press(int x, int y) {
    	int buttonSize = Config.BUTTON_SIZE;
    	for (int i = 0; i < noteRange; i++) {
    		if (Math.abs(x-noteX[i]) < buttonSize/2 && Math.abs(y-noteY[i]) < buttonSize/2) {
    			if (pressedKey.contains(i)) pressedKey.remove(i);
    			else pressedKey.add(i);
    			return;
    		}
    	}
    }
    
    public void moveNotes(int halfSteps) {
    	LinkedList<Integer> newKey = new LinkedList<>();
    	for (int i : pressedKey) {
    		newKey.add((i+halfSteps+noteRange) % noteRange);
    	}
    	pressedKey.clear();
    	for (int i : newKey) {
    		pressedKey.add(i);
    	}
    }
	
	public Set<Integer> getPressedKey() {
		return pressedKey;
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

}
