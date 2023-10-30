package cdvis.component;

import cdvis.app.Config;
import cdvis.util.MusicUtil;
import cdvis.util.PlotUtil;

import javax.swing.*;
import java.awt.*;

public class ChordLabel extends JLabel {

    private final Tonnetz net;

    public ChordLabel(Tonnetz n) {
        net = n;
        setBounds(0,0,Config.SCREEN_WIDTH/4, Config.SCREEN_HEIGHT*2/5);
    }

    public void plotCircle(Graphics2D g2d) {
        Dimension dim = getSize();
        int centerX = dim.width/2;
        int centerY = dim.height/2;
        int diameter = Math.min(dim.width, dim.height) * 4 / 5;
        int dotSize = diameter / 5;

        Color pressedColor = new Color(100,200,100);
        Color unpressedColor = new Color(180,180,180);
        g2d.setFont(new Font("Serif", Font.BOLD, dotSize/2));
        int locationX, locationY;
        for (int fifth = 0; fifth < 12; fifth++) {
            locationX = centerX + (int) (diameter/2 * Math.cos(7*fifth*Math.PI/6));
            locationY = centerY + (int) (diameter/2 * Math.sin(7*fifth*Math.PI/6));
            if (net.getPressedPitchClasses().contains(fifth % 12)) {
                g2d.setColor(pressedColor);
                g2d.fillOval(locationX - dotSize/2, locationY - dotSize/2, dotSize, dotSize);
                g2d.setColor(new Color(0, 130, 0));
                PlotUtil.drawCenteredText(g2d, locationX, locationY, MusicUtil.pitchClass(fifth));
            }
            else {
                g2d.setColor(unpressedColor);
                g2d.fillOval(locationX - dotSize/2, locationY - dotSize/2, dotSize, dotSize);
                g2d.setColor(Color.gray);
                PlotUtil.drawCenteredText(g2d, locationX, locationY, MusicUtil.pitchClass(fifth));
            }
        }

        g2d.setColor(Color.darkGray);
        g2d.setFont(new Font("Serif", Font.BOLD, dotSize));
        PlotUtil.drawCenteredText(g2d, centerX, centerY, net.getChord());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        plotCircle(g2d);
    }


}
