package cdvis.sound;

import java.util.Set;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.*;

import cdvis.util.MusicUtil;


public class NotePlayer implements Runnable{
	private Set<Integer> pressedKey;
	private Player player;
	private Pattern pattern;
	private Thread playerThread;

	public NotePlayer(Set<Integer> n) {
		pressedKey = n;
		player = new Player();
		pattern = new Pattern();
		pattern.setTempo(200);
		
		playerThread = new Thread(this);
		playerThread.start();
	}
	
	public void setNotes() {
		pattern.clear();
		pattern.add(MusicUtil.chordString(pressedKey));
		
	}

	@Override
	public void run() {
		while (true) {
			player.play(pattern);
		}
	}

}
