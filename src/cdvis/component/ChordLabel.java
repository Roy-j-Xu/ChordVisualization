package cdvis.component;

import cdvis.app.Config;
import cdvis.util.MusicUtil;
import cdvis.util.PlotUtil;

import javax.swing.*;
import java.awt.*;

public class ChordLabel extends JLabel {

    private MusicalNet net;
    private int diameter;
    private final int[] X = new int[12];
    private final int[] Y = new int[12];

    public ChordLabel(MusicalNet n) {
        net = n;
        setBounds(0,0,Config.SCREEN_WIDTH/4, Config.SCREEN_HEIGHT*2/5);
        layoutNotes();
    }

    private void layoutNotes() {
        Dimension dim = getSize();
        int centerX = dim.width/2;
        int centerY = dim.height/2;
        diameter = Math.min(dim.width, dim.height) * 4 / 5;
        for (int fifth = 0; fifth < 12; fifth++) {
            X[(7*fifth)%12] = centerX + (int) (diameter / 2 * Math.cos(fifth * Math.PI / 6));
            Y[(7*fifth)%12] = centerY + (int) (diameter / 2 * Math.sin(fifth * Math.PI / 6));
        }
    }

    private void plotCircle(Graphics2D g2d) {
        Dimension dim = getSize();
        int centerX = dim.width/2;
        int centerY = dim.height/2;
        int dotSize = diameter / 5;

        Color pressedColor = new Color(140,180,255);
        Color unpressedColor = new Color(180,180,180);
        g2d.setFont(new Font("Serif", Font.BOLD, dotSize/2));
        for (int i = 0; i < 12; i++) {
            if (net.getPressedPitchClasses().contains(i)) {
                g2d.setColor(pressedColor);
                PlotUtil.drawBall(g2d, pressedColor, X[i], Y[i], dotSize);
                g2d.setColor(new Color(60, 90, 120));
                PlotUtil.drawCenteredText(g2d, X[i], Y[i], MusicUtil.pitchClass(i));
            }
            else {
                PlotUtil.drawBall(g2d, unpressedColor, X[i], Y[i], dotSize);
                g2d.setColor(Color.gray);
                PlotUtil.drawCenteredText(g2d, X[i], Y[i], MusicUtil.pitchClass(i));
            }
        }

        g2d.setColor(Color.darkGray);
        g2d.setFont(new Font("Serif", Font.BOLD, dotSize));
        PlotUtil.drawCenteredText(g2d, centerX, centerY, net.getChord());
    }

    private void plotLines(Graphics2D g2d) {
        g2d.setColor(new Color(200, 200, 255));
        g2d.setStroke(new BasicStroke((float) diameter /60));
        for (int firstNote = 0; firstNote < 11; firstNote++) {
            if (!net.getPressedPitchClasses().contains(firstNote)) continue;
            for (int secondNote = firstNote+1; secondNote < 12; secondNote++) {
                if (net.getPressedPitchClasses().contains(secondNote)) {
                    g2d.drawLine(X[firstNote], Y[firstNote], X[secondNote], Y[secondNote]);
                }
            }
        }
    }

    public void changeMusicalNet(MusicalNet n) {
        net = n;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        plotLines(g2d);
        plotCircle(g2d);
    }


}
