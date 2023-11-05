package cdvis.component;

import cdvis.app.Config;
import cdvis.util.MusicUtil;
import cdvis.util.PlotUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class DualTonnetz extends Graph implements MusicalNet{
	private final int chordRange = Config.tonnetzNoteRange-7;
	private final double[] relChordX, relChordY;
	private final int[] chordX, chordY;
	int gap;
	private final Set<Integer> pressedChord;
	private final Set<Integer> pressedKey;
	private int netX = -100, netY = 60;

	private int rotationCenter = 42;


	public DualTonnetz() {
		pressedKey = new HashSet<>();
		pressedChord = new HashSet<>();
		chordX = new int[chordRange*2];
		chordY = new int[chordRange*2];
		relChordX = new double[chordRange*2];
		relChordY = new double[chordRange*2];
		
		generateButtons();
		layoutRelativeLoc();
		layout();
	}
	
	private void layoutRelativeLoc() {
		double[] relNoteX = new double[chordRange];
		double[] relNoteY = new double[chordRange];
		for (int note = 0; note < chordRange; note++) {
			relNoteY[note] = (6 - (3 + 2 * (note % 7)) % 7)*1.732;
			if (note % 7 == 6 || note % 7 == 0 || note % 7 == 1) {
				relNoteX[note] = (double)((note + 1)/7) * 2;
			} else {
				relNoteX[note] = (double)(note/7) * 2 + 1;
			}
		}
		for (int note = 0; note < chordRange; note++) {
			relChordX[note] = relNoteX[note] + 1;
			relChordY[note] = relNoteY[note] - 0.577;
			relChordX[note + chordRange] = relNoteX[note] + 1;
			relChordY[note + chordRange] = relNoteY[note] + 0.577;
		}
	}
	
	private void layout() {
		gap = Config.BUTTON_SIZE * 11 / 10;
		for (int note = 0; note < chordRange*2; note++) {
			chordX[note] = netX + (int) (relChordX[note] * gap);
			chordY[note] = netY + (int) (relChordY[note] * gap);
		}
	}

	private void generateButtons() {
		for (int note = 0; note < chordRange; note++) {
			this.addVertex(note);
			this.addVertex(note+chordRange);
		}
		for (int note = 0; note < chordRange-4; note++){
			this.addEdge(note, note + 4 + chordRange);
		}
		for (int note = 0; note < chordRange-3; note++) {
			this.addEdge(note + chordRange, note + 3);
		}
		for (int note = 0; note < chordRange; note++) {
			this.addEdge(note, note + chordRange);
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
    	Color pressedColor = new Color(120,120,120);
    	for (int i = 0; i < chordRange*2; i++) {
    		for (int j : this.getNeighbors(i)) {
        		if (pressedChord.contains(i) && pressedChord.contains(j)) {
					g2d.setStroke(new BasicStroke((float) buttonSize /6));
        			g2d.setPaint(pressedColor);
        		} else {
					g2d.setStroke(new BasicStroke((float) buttonSize /16));
        			g2d.setPaint(unpressedColor);
        		}
				if (relChordY[i]- relChordY[j] > 2) {
					int imaginaryX = chordX[j] + gap;
					int imaginaryY = chordY[j] + 7 * gap * 433 / 250;
					g2d.drawLine(chordX[i], chordY[i], (imaginaryX + chordX[i])/2, (imaginaryY + chordY[i])/2);
				}
				else if (relChordY[j]- relChordY[i] > 2) {
					int imaginaryX = chordX[j] - gap;
					int imaginaryY = chordY[j] - 7 * gap * 433 / 250;
					g2d.drawLine(chordX[i], chordY[i], (imaginaryX + chordX[i])/2, (imaginaryY + chordY[i])/2);
				}
				else {
					g2d.drawLine(chordX[i], chordY[i], chordX[j], chordY[j]);
				}
    		}
    	}
    }


    private void plotNodes(Graphics2D g2d) {
    	int buttonSize = Config.BUTTON_SIZE *3/4;

		if (rotationCenter > -1) {
			int centerSize = buttonSize * 5/4;
			PlotUtil.drawBall(g2d, new Color(170,170,170), chordX[rotationCenter], chordY[rotationCenter], centerSize);
		}

    	Color unpressedMajColor = new Color(200,140,140);
    	Color pressedMajColor = new Color(120,20,20);
		Color unpressedMinColor = new Color(140,140,200);
		Color pressedMinColor = new Color(20,20,120);
    	for (int i = 0; i < chordRange; i++) {
    		if (!pressedChord.contains(i)) {
    			PlotUtil.drawBall(g2d, unpressedMajColor, chordX[i], chordY[i], buttonSize);
    		}
			else {
				PlotUtil.drawBall(g2d, pressedMajColor, chordX[i], chordY[i], buttonSize);
			}
    	}
		for (int i = chordRange; i < chordRange*2; i++) {
			if (!pressedChord.contains(i)) {
				PlotUtil.drawBall(g2d, unpressedMinColor, chordX[i], chordY[i], buttonSize);
			}
			else {
				PlotUtil.drawBall(g2d, pressedMinColor, chordX[i], chordY[i], buttonSize);
			}
		}

		g2d.setFont(new Font("Arial", Font.PLAIN, buttonSize/2));
		Color majColor = new Color(150, 60,60);
		Color minColor = new Color(60, 60, 150);
    	for (int i = 0; i < chordRange; i++) {
			g2d.setColor(majColor);
    		PlotUtil.drawCenteredText(g2d, chordX[i], chordY[i], MusicUtil.pitchClass(i));
			g2d.setColor(minColor);
			PlotUtil.drawCenteredText(g2d, chordX[i+chordRange], chordY[i+chordRange], MusicUtil.pitchClass(i));
    	}
    }

    public boolean press(int x, int y) {
    	int buttonSize = Config.BUTTON_SIZE*3/4;
    	for (int i = 0; i < chordRange*2; i++) {
    		if (Math.abs(x- chordX[i]) < buttonSize/2 && Math.abs(y- chordY[i]) < buttonSize/2) {
    			if (pressedChord.contains(i)) pressedChord.remove(i);
    			else pressedChord.add(i);
				updatePressedKey();
    			return true;
    		}
    	}
		return false;
    }

    public void moveNotes(int command) {
    	LinkedList<Integer> newKey = new LinkedList<>();
		for (int i : pressedChord) {
			newKey.add(MusicUtil.dualnetMoveNote(i,chordRange,command));
		}
		if (rotationCenter != -1) rotationCenter = MusicUtil.dualnetMoveNote(rotationCenter,chordRange,command);

    	pressedChord.clear();
		pressedChord.addAll(newKey);
		updatePressedKey();
    }

    public void rotateNotes(int direction) {
		if (rotationCenter == -1) return;

		LinkedList<Integer> newKey = new LinkedList<>();
		int note;
		int[] coef;
		for (int i : pressedChord) {
			coef = dualAlgebraicBFS(rotationCenter, i, chordRange);
			note = MusicUtil.dualnetRotation(i, rotationCenter, coef, chordRange, direction);
			newKey.add(note);
		}
		pressedChord.clear();
		pressedChord.addAll(newKey);
		updatePressedKey();
    }

	public void clearNote() {
		pressedChord.clear();
		pressedKey.clear();
	}

	public void setRotationCenter(int x, int y) {
		int buttonSize = Config.BUTTON_SIZE*3/4;
		for (int i = 0; i < chordRange*2; i++) {
			if (Math.abs(x-chordX[i]) < buttonSize/2 && Math.abs(y-chordY[i]) < buttonSize/2) {
				if (rotationCenter == i) rotationCenter = -1;
				else rotationCenter = i;
				return;
			}
		}
	}

	private void updatePressedKey() {
		pressedKey.clear();
		for (int i : pressedChord) {
			pressedKey.add(i%chordRange);
			pressedKey.add(i%chordRange+7);
			if (i < chordRange) pressedKey.add(i+4);
			else pressedKey.add(i%chordRange+3);
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
