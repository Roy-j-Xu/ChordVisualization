package cdvis.app;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class AppFrame extends JFrame {
	
	private AppPanel aPanel;
	private ControlPanel cPanel;
	
	public AppFrame(AppPanel a, ControlPanel c) {
		setUp(a, c);
	}
	
	
	public void setUp(AppPanel a, ControlPanel c) {
		setTitle("Chord Visualization");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		aPanel = a;
		cPanel = c;
		
		add(aPanel, BorderLayout.EAST);
		add(cPanel, BorderLayout.WEST);
		pack();
		setVisible(true);
		
		try {
			setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
//	@Override
//	public void run() {
//		long lastFrame = 0;
//		long currentTime = 0;
//		while(true) {
//			currentTime = System.currentTimeMillis();
//			if (currentTime - lastFrame >= 15) {
//				aPanel.repaint();
//				lastFrame = currentTime;
//			}
//		}
//	}

}
