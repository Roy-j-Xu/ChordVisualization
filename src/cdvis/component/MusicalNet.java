package cdvis.component;

import java.awt.*;
import java.util.ArrayList;

public interface MusicalNet {

    String getChord();

    ArrayList<Integer> getPressedPitchClasses();

    void plot(Graphics2D g2d);

    void moveNet(int x, int y);

    int getNetX();

    int getNetY();
}
