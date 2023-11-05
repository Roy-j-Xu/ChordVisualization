package cdvis.component;

import cdvis.app.Config;
import cdvis.util.MusicUtil;
import cdvis.util.PlotUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class InfiniteTonnetz implements MusicalNet{
	private final int noteRange = 12;
	private final int[] relNoteX, relNoteY;
	private final int[] noteX, noteY;
	private final int[] expandX = new int[2], expandY = new int[2];
	int gap;
	private final Set<Integer> pressedKey;
	private int netX = -100, netY = 60;


	public InfiniteTonnetz() {
		pressedKey = new HashSet<>();
		noteX = new int[noteRange];
		noteY = new int[noteRange];
		relNoteX = new int[noteRange];
		relNoteY = new int[noteRange];

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
		gap = Config.BUTTON_SIZE * 11/10;
		expandX[0] = 7*gap;
		expandX[1] = -3*gap;
		expandY[0] = 433*gap/250;
		expandY[1] = 1299*gap/250;
		for (int note = 0; note < noteRange; note++) {
			noteX[note] = netX + relNoteX[note] * gap;
			noteY[note] = netY + relNoteY[note] * gap * 433 / 250;
		}
	}
	
    public void plot(Graphics2D g2d) {
    	layout();  	
    	plotEdges(g2d);
    	plotNodes(g2d);
    }
    
    private void plotEdges(Graphics2D g2d) {
    	for (int i = 0; i < noteRange; i++) {
			plotEdgeClass(g2d, i);
		}
    }

	private void plotEdgeClass(Graphics2D g2d, int note) {
		int buttonSize = Config.BUTTON_SIZE;
		Color unpressedColor = new Color(200,200,200);
		Color pressedColor = new Color(80,180,80);
		int[] direction = {-1,1};

		for (int repeatX = -7; netX+repeatX*gap < Config.SCREEN_WIDTH; repeatX++) {
			for (int repeatY = -7; netY+repeatY*gap < Config.SCREEN_HEIGHT; repeatY++) {
				int x = noteX[note]+repeatX*expandX[0]+repeatY*expandX[1];
				int y = noteY[note]+repeatX*expandY[0]+repeatY*expandY[1];
				for (int dir:direction) {
					if (pressedKey.contains(note) && pressedKey.contains((12+note+dir*7)%12)) {
						g2d.setStroke(new BasicStroke((float) buttonSize /6));
						g2d.setColor(pressedColor);
					}
					else {
						g2d.setStroke(new BasicStroke((float) buttonSize /16));
						g2d.setColor(unpressedColor);
					}
					g2d.drawLine(x, y, x + dir*2*gap, y);

					if (pressedKey.contains(note) && pressedKey.contains((12+note+dir*4)%12)) {
						g2d.setStroke(new BasicStroke((float) buttonSize /6));
						g2d.setColor(pressedColor);
					}
					else {
						g2d.setStroke(new BasicStroke((float) buttonSize /16));
						g2d.setColor(unpressedColor);
					}
					g2d.drawLine(x, y, x + dir*gap, y-dir*433*gap/250);

					if (pressedKey.contains(note) && pressedKey.contains((12+note+dir*3)%12)) {
						g2d.setStroke(new BasicStroke((float) buttonSize /6));
						g2d.setColor(pressedColor);
					}
					else {
						g2d.setStroke(new BasicStroke((float) buttonSize /16));
						g2d.setColor(unpressedColor);
					}
					g2d.drawLine(x, y, x + dir*gap, y+dir*433*gap/250);
				}
			}
		}
	}

	private void plotNoteClass(Graphics2D g2d, int note) {
		int buttonSize = Config.BUTTON_SIZE;
		Color color;
		if (!pressedKey.contains(note)) color = new Color(130,200,130);
		else color = new Color(0,80,0);
		g2d.setFont(new Font("Arial", Font.PLAIN, buttonSize/2));

		for (int repeatX = -7; netX+repeatX*gap < Config.SCREEN_WIDTH; repeatX++) {
			for (int repeatY = -7; netY+repeatY*gap < Config.SCREEN_HEIGHT; repeatY++) {
				int x = noteX[note]+repeatX*expandX[0]+repeatY*expandX[1];
				int y = noteY[note]+repeatX*expandY[0]+repeatY*expandY[1];
				if (!pressedKey.contains(note)) {
					PlotUtil.drawBall(g2d, color, x, y, buttonSize);
				}
				else {
					PlotUtil.drawBall(g2d, color, x, y, buttonSize);
				}
				g2d.setColor(new Color(0, 130, 0));
				PlotUtil.drawCenteredText(g2d, x, y, MusicUtil.pitchClass(note));
			}
		}
	}
    
    
    private void plotNodes(Graphics2D g2d) {
    	for (int i = 0; i < noteRange; i++) {
			plotNoteClass(g2d, i);
    	}
    }
    
    public boolean press(int x, int y) {
    	int buttonSize = Config.BUTTON_SIZE;
    	for (int note = 0; note < noteRange; note++) {
			for (int repeatX = -7; netX+repeatX*gap < Config.SCREEN_WIDTH; repeatX++) {
				for (int repeatY = -7; netY + repeatY * gap < Config.SCREEN_HEIGHT; repeatY++) {
					int X = noteX[note]+repeatX*expandX[0]+repeatY*expandX[1];
					int Y = noteY[note]+repeatX*expandY[0]+repeatY*expandY[1];
					if (Math.abs(x - X) < buttonSize / 2 && Math.abs(y - Y) < buttonSize / 2) {
						if (pressedKey.contains(note)) pressedKey.remove(note);
						else pressedKey.add(note);
						return true;
					}
				}
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
    }
	
	public void clearNote() {
		pressedKey.clear();
	}

	@Override
	public LinkedList<int[]> getSoundInformation() {
		LinkedList<int[]> soundInfo = new LinkedList<>();
		int dis;
		for (int i: pressedKey) {
			dis = Math.abs(i - 6);
			soundInfo.add(new int[]{i+24, 100-20/(dis+1)});
			soundInfo.add(new int[]{i+48, 100-40/(dis+1)});
			soundInfo.add(new int[]{i+60, 100-20/(dis+1)});
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
		while (netX < 0 || netX > Config.SCREEN_WIDTH || netY < 0 || netY > Config.SCREEN_HEIGHT) {
			adjustNetLocation();
		}
	}

	public void adjustNetLocation() {
		if (netX < 0) {
			if (netY > 1299*gap/250) {
				netX += 3*gap;
				netY -= 1299*gap/250;
			}
			else {
				netX += 7*gap;
				netY += 433*gap/250;
			}
		}
		if (netY < 0) {
			if (netX > 3*gap) {
				netX -= 3*gap;
				netY += 1299*gap/250;
			}
			else {
				netX += 7*gap;
				netY += 433*gap/250;
			}
		}
		if (netX > Config.SCREEN_WIDTH) {
			if (netY > 433*gap/250) {
				netX -= 7 * gap;
				netY -= 433 * gap / 250;
			}
			else {
				netX -= 3*gap;
				netY += 1299*gap/250;
			}
		}
		if (netY > Config.SCREEN_HEIGHT) {
			if (netX > 7*gap) {
				netX -= 7 * gap;
				netY -= 433 * gap / 250;
			}
			else {
				netX += 3*gap;
				netY -= 1299*gap/250;
			}
		}
	}

	public int getNetX() {
		return netX;
	}
	
	public int getNetY() {
		return netY;
	}



	public void rotateNotes(int direction) {
	}

	public void setRotationCenter(int x, int y) {
	}
}
