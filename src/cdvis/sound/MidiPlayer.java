package cdvis.sound;

import cdvis.app.AppPanel;
import cdvis.app.ChordLabel;
import cdvis.component.MusicalNet;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class MidiPlayer {
    private Sequencer sequencer;
    private MusicalNet net;
    private String fileName;

    private final AppPanel aPanel;
    private final ChordLabel cLabel;

    public MidiPlayer(MusicalNet n, AppPanel a, ChordLabel c, NotePlayer player) {
        net = n;
        aPanel = a;
        cLabel = c;
        try {
            sequencer = MidiSystem.getSequencer();
        } catch (Exception ignored) {
        }
        sequencer.addMetaEventListener(meta -> {
            if (meta.getType() == 47) { // 47 indicates the end of track event
                try {
                    player.start();
                } catch (MidiUnavailableException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private class MyMidiReceiver implements Receiver {
        @Override
        public void send(MidiMessage message, long timeStamp) {
            if (!sequencer.isRunning()) return;
            if (message instanceof ShortMessage shortMessage) {
                int command = shortMessage.getCommand();
                int note = shortMessage.getData1();
                int velocity = shortMessage.getData2();

                synchronized (this) {
                    if (command == ShortMessage.NOTE_ON && velocity > 0) {
                        net.pressNote(note + 3);
                    } else if (command == ShortMessage.NOTE_OFF || velocity == 0) {
                        net.releaseNote(note + 3);
                    }
                    aPanel.repaint();
                    cLabel.repaint();
                }
            }
        }

        @Override
        public void close() { }
    }

    public void chooseFile(String path) throws MidiUnavailableException, InvalidMidiDataException, IOException {
        sequencer.open();
        File midiFile = new File(path);
        fileName = midiFile.getName();
        Sequence sequence = MidiSystem.getSequence(midiFile);

        sequencer.setSequence(sequence);
        Transmitter transmitter = sequencer.getTransmitter();

        transmitter.setReceiver(new MyMidiReceiver());
    }

    public void changeMusicalNet(MusicalNet n) {
        net = n;
    }

    public double getPositionPercentage() {
        return (double) sequencer.getMicrosecondPosition()/sequencer.getMicrosecondLength();
    }

    public void setPositionPercentage(double percentage) {
        if (sequencer.isOpen()) {
            sequencer.setMicrosecondPosition((long) (percentage * sequencer.getMicrosecondLength()));
        }
    }

    public String getFileName() {
        return fileName;
    }

    public long getMicrosecondPosition() {
        return sequencer.getMicrosecondPosition();
    }

    public long getMicrosecondLength() {
        return sequencer.getMicrosecondLength();
    }

    public void stop() {
        sequencer.close();
    }

    public void pause() {
        sequencer.stop();
    }

    public void start() {
        sequencer.start();
    }

    public boolean isOpen() {
        return sequencer.isOpen();
    }

}
