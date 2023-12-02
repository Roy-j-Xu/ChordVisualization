package cdvis.menu;

import cdvis.app.AppPanel;
import cdvis.app.ChordLabel;
import cdvis.app.ControlPanel;
import cdvis.listener.ControlListener;
import cdvis.listener.TonnetzController;
import cdvis.listener.TonnetzMover;
import cdvis.sound.MidiPlayer;
import cdvis.sound.NotePlayer;

import javax.swing.*;

public class SettingsMenu extends JMenu {
    private final AppPanel aPanel;
    private final ControlPanel cPanel;
    private final ControlListener cListener;
    private final TonnetzMover tMover;
    private final TonnetzController tController;
    private final NotePlayer player;
    private final MidiPlayer midiPlayer;
    private final ChordLabel cLabel;

    public SettingsMenu(AppPanel a, ControlPanel c, ControlListener cl,
                        TonnetzMover t, TonnetzController tc, NotePlayer p, MidiPlayer m,
                        ChordLabel clb) {
        super("Settings");
        aPanel = a;
        cPanel = c;
        cListener = cl;
        tMover = t;
        tController = tc;
        player = p;
        midiPlayer = m;
        cLabel = clb;

        JMenuItem instrumentMenuItem = new JMenuItem("Instrument");
        instrumentMenuItem.addActionListener(e -> new SwitchInstrumentFrame(p));
        add(instrumentMenuItem);

        JMenuItem netMenuItem = new JMenuItem("Change net");
        netMenuItem.addActionListener(e -> new SwitchNetFrame(aPanel,cPanel,cListener,tMover,
                tController,player,midiPlayer,cLabel));
        add(netMenuItem);
    }

}
