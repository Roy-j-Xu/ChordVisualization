package cdvis.sound;

import cdvis.component.MusicalNet;

import javax.sound.midi.*;


public class NotePlayer {
	private MusicalNet net;
	private final Synthesizer synth;
	private final MidiChannel channel;
	private int instrument = 73;

	public NotePlayer(MusicalNet n) throws MidiUnavailableException {
		net = n;
		synth = MidiSystem.getSynthesizer();
		synth.open();
		channel = synth.getChannels()[0];
		channel.programChange(instrument);
	}

	public void start() throws MidiUnavailableException {
		synth.open();
	}

	public void stop() {
		synth.close();
	}
	
	public void setNotes() {
		channel.allNotesOff();
		for (int[] note : net.getSoundInformation()) {
			channel.noteOn(note[0], note[1]);
		}
	}

	public void changeMusicalNet(MusicalNet p) {
		net = p;
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
