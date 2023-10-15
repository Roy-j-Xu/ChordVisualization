package cdvis.sound;

import org.jfugue.pattern.Pattern;
import org.jfugue.player.*;

public class NotePlayer implements Runnable{
	private Player player;
	private String notes = "";
	private Pattern pattern;
	private Thread playerThread;

	public NotePlayer() {
		player = new Player();
		pattern = new Pattern();
		pattern.setInstrument("Violin");
		
		playerThread = new Thread(this);
		playerThread.start();
	}
	
	public void setNotes(String newNotes) {
		notes = newNotes;
		pattern.clear();
		pattern.add(notes);
		
	}

	@Override
	public void run() {
		while (true) {
			player.play(pattern);
		}
	}

}
