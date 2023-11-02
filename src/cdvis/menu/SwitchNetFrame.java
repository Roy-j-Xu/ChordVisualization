package cdvis.menu;

import cdvis.app.AppPanel;
import cdvis.component.ChordLabel;
import cdvis.component.DualTonnetz;
import cdvis.component.MusicalNet;
import cdvis.component.Tonnetz;
import cdvis.listener.ControlListener;
import cdvis.listener.TonnetzController;
import cdvis.listener.TonnetzMover;
import cdvis.sound.NotePlayer;


import javax.swing.*;
import java.awt.*;

public class SwitchNetFrame extends JFrame {
    private final AppPanel aPanel;
    private final ControlListener cListener;
    private final TonnetzMover tMover;
    private final TonnetzController tController;
    private final NotePlayer player;
    private final ChordLabel cLabel;
    JComboBox<String> comboBox;

    public SwitchNetFrame(AppPanel a, ControlListener cl , TonnetzMover t, TonnetzController tc, NotePlayer p, ChordLabel clb) {
        aPanel = a;
        cListener = cl;
        tMover = t;
        tController = tc;
        player = p;
        cLabel = clb;

        setTitle("Change Visualization");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setResizable(false);
        try {
            setLocationRelativeTo(null);
        } catch (Exception ignored) {
        }

        String[] options = {"Tonnetz", "Dual-Tonnetz"};
        comboBox = new JComboBox<>(options);

        add(comboBox);
        addButtons();
        setVisible(true);
    }


    public void addButtons() {
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setContentAreaFilled(false);
        confirmButton.addActionListener(e -> {
            MusicalNet net;
            switch (comboBox.getSelectedIndex()) {
                case 0:
                    net = new Tonnetz();
                    break;
                case 1:
                    net = new DualTonnetz();
                    break;
                default: return;
            }
            aPanel.changeMusicalNet(net);
            cListener.changeMusicalNet(net);
            tMover.changeMusicalNet(net);
            tController.changeMusicalNet(net);
            player.setPressedKey(net.getPressedKey());
            cLabel.changeMusicalNet(net);

            aPanel.repaint();
            player.setNotes();

            this.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setContentAreaFilled(false);
        cancelButton.addActionListener(e -> this.dispose());

        add(confirmButton, BorderLayout.EAST);
        add(cancelButton, BorderLayout.SOUTH);
    }

}
