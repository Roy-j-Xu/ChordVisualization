package cdvis;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import cdvis.app.AppFrame;
import cdvis.app.AppPanel;
import cdvis.app.ControlPanel;
import cdvis.component.ChordLabel;
import cdvis.component.MusicalNet;
import cdvis.component.Tonnetz;
import cdvis.listener.ControlListener;
import cdvis.listener.TonnetzController;
import cdvis.listener.TonnetzMover;
import cdvis.menu.MenuBar;
import cdvis.menu.SettingsMenu;
import cdvis.sound.NotePlayer;

public class Main {
	private AppPanel aPanel;
	private ControlPanel cPanel;
	private ChordLabel cLabel;
	private MusicalNet net;
	private NotePlayer player;
	private AppFrame aFrame;

	private TonnetzController TController;
	private TonnetzMover TMover;
	private ControlListener CListener;

	public Main() {
		init();
	}

	public void init() {
		net = new Tonnetz();
		try {
			player = new NotePlayer(net);
		} catch (MidiUnavailableException e) {
			throw new RuntimeException(e);
		}
		aPanel = new AppPanel(net);
		cPanel = new ControlPanel();
		cLabel = new ChordLabel(net);
		aFrame = new AppFrame(aPanel, cPanel, cLabel);
		
		addListeners();
		addMenu();

	}

	public void addMenu() {
		SettingsMenu settingsMenu = new SettingsMenu(aPanel, cPanel, CListener, TMover,
				TController, player, cLabel);

		MenuBar menuBar = new MenuBar();
		menuBar.add(settingsMenu);

		aFrame.setJMenuBar(menuBar);
	}
	
	public void addListeners() {
		TController = new TonnetzController(net, aPanel, player, cLabel);
		aPanel.addMouseListener(TController);

		TMover = new TonnetzMover(net, aPanel);
		aPanel.addMouseListener(TMover);
		aPanel.addMouseMotionListener(TMover);
		aPanel.addMouseWheelListener(TMover);

		CListener = new ControlListener(net, aPanel, player, cLabel);
		for (JButton b : cPanel.getButtons()) {
			b.addActionListener(CListener);
		}

		aFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				player.stop();
			}
		});
	}

	public static void main(String[] args) {
		new Main();
	}

}
