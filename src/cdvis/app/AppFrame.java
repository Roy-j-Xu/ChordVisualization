package cdvis.app;

import javax.swing.JFrame;

public class AppFrame extends JFrame implements Runnable{
	
	private AppPanel panel;
	
	private Thread panelThread;
	
	public AppFrame() {
		this.panel = new AppPanel();
		this.add(panel);
		this.setTitle("Chord Visualization");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		try {
			this.setLocationRelativeTo(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startPanelLoop();
	}
	
	public void startPanelLoop() {
		panelThread = new Thread(this);
		panelThread.start();
	}
	
	@Override
	public void run() {
		long lastFrame = 0;
		long currentTime = 0;
		while(true) {
			currentTime = System.currentTimeMillis();
			if (currentTime - lastFrame >= 10) {
				panel.repaint();
				lastFrame = currentTime;
			}
		}
	}

}
