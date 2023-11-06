package cdvis.menu;

import cdvis.sound.MidiPlayer;
import cdvis.sound.NotePlayer;

import javax.swing.*;
import java.io.File;

public class FileMenu extends JMenu {
    private final NotePlayer player;
    private final MidiPlayer midiPlayer;

    public FileMenu(NotePlayer p, MidiPlayer m) {
        super("File");

        player = p;
        midiPlayer = m;

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            JFrame chooseFileFrame = new JFrame("Choose Midi File");
            chooseFileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            int returnVal = fileChooser.showOpenDialog(chooseFileFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    midiPlayer.chooseFile(selectedFile.getAbsolutePath());
                    player.stop();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            chooseFileFrame.dispose();
        });
        add(openMenuItem);

    }


}
