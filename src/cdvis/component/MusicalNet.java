package cdvis.component;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public interface MusicalNet {

    String getChord();

    ArrayList<Integer> getPressedPitchClasses();

    void plot(Graphics2D g2d);

    void moveNet(int x, int y);

    int getNetX();

    int getNetY();

    void rotateNotes(int command);

    void clearNote();

    void moveNotes(int command);

    boolean press(int x, int y);

    void setRotationCenter(int x, int y);

    LinkedList<int[]> getSoundInformation();
}
