package cdvis.menu;

import cdvis.sound.NotePlayer;

import javax.swing.*;

public class MenuBar extends JMenuBar {

    public MenuBar(NotePlayer p) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);

        JMenu settingMenu = new JMenu("Settings");
        JMenuItem instrumentMenuItem = new JMenuItem("Instrument");
        instrumentMenuItem.addActionListener(e -> new SwitchInstrumentFrame(p));
        settingMenu.add(instrumentMenuItem);

        add(fileMenu);
        add(settingMenu);
    }

}
