package cdvis.menu;

import cdvis.sound.NotePlayer;
import cdvis.util.InstrumentDict;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashSet;

public class SwitchInstrumentFrame extends JFrame {
    private final NotePlayer player;
    private final DefaultTableModel model;
    private final JTable table;
    private final JPanel panel;

    public SwitchInstrumentFrame(NotePlayer p) {
        player = p;

        setTitle("Chord Visualization");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setResizable(false);
        try {
            setLocationRelativeTo(null);
        } catch (Exception e) {

        }

        model = new DefaultTableModel();
        model.addColumn("Instruments");
        addInstrumentsToTable();

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        table.setRowSelectionInterval(player.getInstrument(), player.getInstrument());

        JScrollPane scrollPane = new JScrollPane(table);

        panel = new JPanel();
        panel.add(scrollPane);
        add(panel, BorderLayout.CENTER);
        addButtons();

        setVisible(true);
    }


    public void addInstrumentsToTable() {
        InstrumentDict.loadInstrumentData();
        for (int i = 0; i < InstrumentDict.MidiInstrument.size(); i++) {
            model.addRow(new Object[]{i + " " + InstrumentDict.MidiInstrument.get(i)});
        }

    }

    public void addButtons() {
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setContentAreaFilled(false);
        confirmButton.addActionListener(e -> {
            player.setInstrument(table.getSelectedRow());
            this.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setContentAreaFilled(false);
        cancelButton.addActionListener(e -> this.dispose());

        panel.add(confirmButton, BorderLayout.EAST);
        panel.add(cancelButton, BorderLayout.EAST);
    }

    public static void main(String[] args) throws MidiUnavailableException {
        new SwitchInstrumentFrame(new NotePlayer(new HashSet<>()));
    }

}
