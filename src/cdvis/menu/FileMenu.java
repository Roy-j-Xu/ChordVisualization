package cdvis.menu;

import cdvis.app.AppFrame;
import cdvis.app.PlayerPanel;
import cdvis.sound.MidiPlayer;
import cdvis.sound.NotePlayer;

import javax.swing.*;
import java.io.File;

public class FileMenu extends JMenu {
    private final NotePlayer player;
    private final MidiPlayer midiPlayer;
    private final PlayerPanel pPanel;
    private final AppFrame aFrame;

    public FileMenu(NotePlayer p, MidiPlayer m, PlayerPanel pp, AppFrame a) {
        super("File");

        player = p;
        midiPlayer = m;
        pPanel = pp;
        aFrame = a;

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
                    aFrame.setTitle("ChordVisualization - " + midiPlayer.getFileName());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                pPanel.repaint();
            }
            chooseFileFrame.dispose();
        });
        add(openMenuItem);
    }

}
