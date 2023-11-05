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

public class Tonnetz extends Graph implements MusicalNet{
	private final int noteRange = Config.tonnetzNoteRange;
	private final int[] relNoteX, relNoteY;
	private final int[] noteX, noteY;
	int gap;
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
		gap = Config.BUTTON_SIZE * 11 / 10;
		for (int note = 0; note < noteRange; note++) {
			noteX[note] = netX + relNoteX[note] * gap;
			noteY[note] = netY + relNoteY[note] * gap * 433 / 250;
		}
	}
	
	private void generateButtons() {
		for (int note = 0; note < noteRange; note++) {
			this.addVertex(note);
		}
		for (int note = 0; note < noteRange-3; note++){
			this.addEdge(note, note+3);
			if (note + 4 < noteRange) {
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
    
    private void plotEdges(Graphics2D g2d) {
    	int buttonSize = Config.BUTTON_SIZE;

    	Color unpressedColor = new Color(200,200,200);
    	Color pressedColor = new Color(80,180,80);

    	for (int i = 0; i < noteRange; i++) {
    		for (int j : this.getNeighbors(i)) {
        		if (pressedKey.contains(i) && pressedKey.contains(j)) {
					g2d.setStroke(new BasicStroke((float) buttonSize /6));
        			g2d.setPaint(pressedColor);
        		} else {
					g2d.setStroke(new BasicStroke((float) buttonSize /16));
        			g2d.setPaint(unpressedColor);
        		}
				if (relNoteY[i]-relNoteY[j] > 2) {
					int imaginaryX = noteX[j] + gap;
					int imaginaryY = noteY[j] + 7 * gap * 433 / 250;
					g2d.drawLine(noteX[i], noteY[i], (imaginaryX + noteX[i])/2, (imaginaryY + noteY[i])/2);
				}
				else if (relNoteY[j]-relNoteY[i] > 2) {
					int imaginaryX = noteX[j] - gap;
					int imaginaryY = noteY[j] - 7 * gap * 433 / 250;
					g2d.drawLine(noteX[i], noteY[i], (imaginaryX + noteX[i])/2, (imaginaryY + noteY[i])/2);
				}
				else {
					g2d.drawLine(noteX[i], noteY[i], noteX[j], noteY[j]);
				}
    		}
    	}
    }
    
    
    private void plotNodes(Graphics2D g2d) {
    	int buttonSize = Config.BUTTON_SIZE;
    	if (rotationCenter > -1) {
        	int centerSize = buttonSize * 5/4;
			PlotUtil.drawBall(g2d, new Color(80,180,80), noteX[rotationCenter], noteY[rotationCenter], centerSize);
    	}
    	
    	Color unpressedColor = new Color(130,200,130);
    	Color pressedColor = new Color(0,80,0);
    	for (int i = 0; i < noteRange; i++) {
    		if (!pressedKey.contains(i)) {
    			PlotUtil.drawBall(g2d, unpressedColor, noteX[i], noteY[i], buttonSize);
    		}
			else {
				PlotUtil.drawBall(g2d, pressedColor, noteX[i], noteY[i], buttonSize);
			}
    	}
    	
    	g2d.setFont(new Font("Arial", Font.PLAIN, buttonSize/2));
    	g2d.setColor(new Color(0, 130, 0));
    	for (int i = 0; i < noteRange; i++) {
    		PlotUtil.drawCenteredText(g2d, noteX[i], noteY[i], MusicUtil.pitchClass(i));
    	}
    }
    
    public boolean press(int x, int y) {
    	int buttonSize = Config.BUTTON_SIZE;
    	for (int i = 0; i < noteRange; i++) {
    		if (Math.abs(x-noteX[i]) < buttonSize/2 && Math.abs(y-noteY[i]) < buttonSize/2) {
    			if (pressedKey.contains(i)) pressedKey.remove(i);
    			else pressedKey.add(i);
    			return true;
    		}
    	}
		return false;
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

	@Override
	public LinkedList<int[]> getSoundInformation() {
		LinkedList<int[]> soundInfo = new LinkedList<>();
		for (int i: pressedKey) {
			soundInfo.add(new int[]{i+24, 100});
		}
		return soundInfo;
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
