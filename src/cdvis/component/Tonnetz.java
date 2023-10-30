package cdvis.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import cdvis.app.Config;
import cdvis.util.MusicUtil;
import cdvis.util.PlotUtil;

public class Tonnetz extends Graph{
	private final int noteRange = Config.tonnetzNoteRange;
	private final int[] relNoteX, relNoteY;
	private final int[] noteX, noteY;
	private final Set<Integer> pressedKey;
	private int netX = -100, netY = 60;
	
	private int rotationCenter = 42;
	

	public Tonnetz() {
		pressedKey = new HashSet<>();
		noteX = new int[noteRange];
		noteY = new int[noteRange];
		relNoteX = new int[noteRange];
		relNoteY = new int[noteRange];
		
		generateButtons();
		layoutRelativeLoc();
		layout();
	}
	
	private void layoutRelativeLoc() {
		
		for (int note = 0; note < noteRange; note++) {
			relNoteY[note] = 6 - (3 + 2 * (note % 7)) % 7;
			if (note % 7 == 6 || note % 7 == 0 || note % 7 == 1) {
				relNoteX[note] = ((note + 1)/7) * 2;
			} else {
				relNoteX[note] = (note/7) * 2 + 1;
			}
		}
		
	}
	
	private void layout() {
		int gap = Config.BUTTON_SIZE * 11 / 5;
		
		for (int note = 0; note < noteRange; note++) {
			noteX[note] = netX + relNoteX[note] * (gap/2);
			noteY[note] = netY + relNoteY[note] * gap * 433 / 500;
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
    	layout();  	
    	plotEdges(g2d);
    	plotNodes(g2d);
    	
    }
    
    public void plotEdges(Graphics2D g2d) {
    	int buttonSize = Config.BUTTON_SIZE;
    	g2d.setStroke(new BasicStroke(buttonSize/7));
    	Color unpressedColor = new Color(195,195,195);
    	Color pressedColor = new Color(80,180,80);

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
    }
    
    
    public void plotNodes(Graphics2D g2d) {
    	int buttonSize = Config.BUTTON_SIZE;
    	if (rotationCenter > -1) {
    		g2d.setPaint(new Color(80,180,80));
        	int centerSize = buttonSize * 5/4;
        	g2d.fillOval(noteX[rotationCenter] - centerSize/2, noteY[rotationCenter] - centerSize/2, centerSize, centerSize);
    	}
    	
    	Color unpressedColor = new Color(100,200,100);
    	Color pressedColor = new Color(0,80,0);
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
    	MusicUtil.recognizeChord(pressedKey);

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
		pressedKey.addAll(newKey);
    	if (rotationCenter > -1) {
        	rotationCenter += halfSteps + noteRange;
        	rotationCenter %= noteRange;    		
    	}

    }
    
    
    public void rotateNotes(int direction) {
    	if (rotationCenter == -1) return;
    	
    	LinkedList<Integer> newKey = new LinkedList<>();
    	int note;
    	int[] coef;
    	for (int i : pressedKey) {
    		coef = algebraicBFS(rotationCenter, i);
    		note = rotationCenter + MusicUtil.tonnetzPathRotation(coef, direction) + noteRange;
    		note %= noteRange;
    		newKey.add(note);
    	}
    	pressedKey.clear();
		pressedKey.addAll(newKey);
    }
	
	public Set<Integer> getPressedKey() {
		return pressedKey;
	}

	
	public void clearNote() {
		pressedKey.clear();
	}
	
	public void setRotationCenter(int x, int y) {
    	int buttonSize = Config.BUTTON_SIZE;
    	for (int i = 0; i < noteRange; i++) {
    		if (Math.abs(x-noteX[i]) < buttonSize/2 && Math.abs(y-noteY[i]) < buttonSize/2) {
    			if (rotationCenter == i) rotationCenter = -1;
    			else rotationCenter = i;
    			return;
    		}
    	}
	}

	public String getChord() {
		return MusicUtil.recognizeChord(pressedKey);
	}

	public ArrayList<Integer> getPressedPitchClasses() {
		return MusicUtil.getPitchClasses(pressedKey);
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
