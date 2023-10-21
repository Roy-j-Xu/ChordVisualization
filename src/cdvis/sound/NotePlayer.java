package cdvis.sound;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.*;

public class NotePlayer implements Runnable{
	private Player player;
	private Pattern pattern;
	private Thread playerThread;

	public NotePlayer() {
		player = new Player();
		pattern = new Pattern();
		pattern.setTempo(200);
//		pattern.setInstrument("Violin");
		
		playerThread = new Thread(this);
		playerThread.start();
	}
	
	public void setNotes(String newNotes) {
		pattern.clear();
		pattern.add(newNotes);
		
	}

	@Override
	public void run() {
		while (true) {
			player.play(pattern);
		}
	}

}
