package cdvis.sound;

import java.util.Set;

import javax.sound.midi.*;


public class NotePlayer {
	private final Set<Integer> pressedKey;
	private final MidiChannel channel;
	private int instrument = 73;

	public NotePlayer(Set<Integer> n) throws MidiUnavailableException {
		pressedKey = n;
		Synthesizer synth = MidiSystem.getSynthesizer();
		synth.open();
		channel = synth.getChannels()[0];
		channel.programChange(instrument);
	}

	public void stop() {
		channel.allNotesOff();
	}
	
	public void setNotes() {
		channel.allNotesOff();
		for (int i : pressedKey) {
			channel.noteOn(i+12, 100);
		}
	}

	public void setInstrument(int instr) {
		instrument = instr;
		channel.programChange(instrument);
		setNotes();
	}

	public int getInstrument() {
		return instrument;
	}

}
