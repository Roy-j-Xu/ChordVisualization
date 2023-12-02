package cdvis.app;

import cdvis.sound.MidiPlayer;
import cdvis.sound.NotePlayer;
import cdvis.util.PlotUtil;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;

public class PlayerPanel extends JPanel{
    private final MidiPlayer midiPlayer;
    private final NotePlayer player;
    private boolean isRunning = false;

    private final AppFrame aFrame;

    private final int width;
    private final int height;
    private final int barStartX;
    private final int scrollButtonSize;
    private final int[] buttonsX = new int[2];
    private final int buttonsY;
    private final int[] buttonSize;

    public PlayerPanel(NotePlayer p, MidiPlayer midiPlayer, AppFrame a) {
        this.midiPlayer = midiPlayer;
        this.player = p;
        this.aFrame = a;

        width = Config.SCREEN_WIDTH/4;
        height = Config.SCREEN_HEIGHT/10;
        barStartX = width/8;
        scrollButtonSize = barStartX/3;
        buttonsX[0] = barStartX*5/4;
        buttonsX[1] = barStartX*5/2;
        buttonsY = height*5/7;
        buttonSize = new int[]{barStartX/2, barStartX/2};

        setBounds(0, Config.SCREEN_HEIGHT*2/5, Config.SCREEN_WIDTH/4, Config.SCREEN_HEIGHT/10);
        setBackground(new Color(190,210,220));
        setFont(new Font("Arial", Font.PLAIN, buttonSize[0]));
    }

    private class PlayerPannelRunnable implements Runnable {
        private long lastFrame = System.currentTimeMillis();
        @Override
        public void run() {
            while (isRunning) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastFrame > 1000) {
                    repaint();
                    if (midiPlayer.getPositionPercentage() >= 1) pauseMidiPlayer();
                    lastFrame = currentTime;
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color color = (midiPlayer.isOpen()) ? Color.darkGray:Color.gray;

        double percentage = midiPlayer.getPositionPercentage();
        int position = (int) (percentage * (width-2*barStartX));
        PlotUtil.drawBall(g2d, color, barStartX+position, height/3, scrollButtonSize);
        g2d.drawLine(barStartX,height/3,width-barStartX, height/3);

        PlotUtil.drawStopButton(g2d, color, buttonsX[1], buttonsY, buttonSize);
        if (isRunning) {
            PlotUtil.drawPauseButton(g2d, color, buttonsX[0], buttonsY, buttonSize);
        }
        else {
            PlotUtil.drawPlayButton(g2d, color, buttonsX[0], buttonsY, buttonSize);
        }

        String time = PlotUtil.getTimeString(midiPlayer.getMicrosecondPosition());
        time +=  "/" + PlotUtil.getTimeString(midiPlayer.getMicrosecondLength());
        PlotUtil.drawLeftAlignedText(g2d, width-barStartX, buttonsY, time);

    }

    public void click(int x, int y) {
        if (!midiPlayer.isOpen()) return;
        if (Math.abs(y-height/3) < scrollButtonSize/2 && x > barStartX && x < width-barStartX) {
            double percentage = (double)(x-barStartX)/(width-2*barStartX);
            midiPlayer.setPositionPercentage(percentage);
            repaint();
            return;
        }
        if (Math.abs(y-buttonsY)<30) {
            if (Math.abs(x - buttonsX[0]) < buttonSize[0]) {
                if (isRunning) pauseButtonFunction();
                else playButtonFunction();
            }
            if (Math.abs(x - buttonsX[1]) < buttonSize[0]) {
                stopButtonFunction();
            }
        }
    }

    private void pauseButtonFunction() {
        pauseMidiPlayer();
        repaint();
    }

    private void playButtonFunction() {
        if (midiPlayer.getPositionPercentage() >= 1) {
            midiPlayer.setPositionPercentage(0);
        }
        startMidiPlayer();
        repaint();
    }

    private void stopButtonFunction() {
        midiPlayer.setPositionPercentage(0);
        aFrame.setTitle("Chord Visualization");
        stopMidiPlayer();
        repaint();
    }

    public void startMidiPlayer() {
        Thread playerPannelThread = new Thread(new PlayerPannelRunnable());
        playerPannelThread.setDaemon(true);
        playerPannelThread.start();
        midiPlayer.start();
        isRunning = true;
    }

    public void pauseMidiPlayer() {
        midiPlayer.pause();
        isRunning = false;
    }

    public void stopMidiPlayer() {
        midiPlayer.stop();
        isRunning = false;
        try {
            player.start();
        } catch (MidiUnavailableException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
