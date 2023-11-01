package cdvis.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class MusicUtil {

	private MusicUtil() {
	}
	
	public static String pitchClass(int n) {
        return switch (n % 12) {
            case 0 -> "A";
            case 1 -> "Bb";
            case 2 -> "B";
            case 3 -> "C";
            case 4 -> "C#";
            case 5 -> "D";
            case 6 -> "Eb";
            case 7 -> "E";
            case 8 -> "F";
            case 9 -> "F#";
            case 10 -> "G";
            case 11 -> "G#";
            default -> "";
        };
    }
	
	public static int tonnetzPathRotation(int[] coef, int direction) {
		int result;
		if (direction == -1) {
			result = - 4 * coef[0] + 7 * coef[1] + 3 * coef[2];
		} else {
			result = 7 * coef[0] - 3 * coef[1] + 4 * coef[2];
		}
		return result;
		
	}
	
	public static String recognizeChord(Set<Integer> pressedKey) {
		ArrayList<Integer> pitchClasses = getPitchClasses(pressedKey);
		int size = pitchClasses.size();
		Map<String, String> useDict;
		switch (size) {
			case 3:
				useDict = ChordDict.chord3;
				break;
			case 4:
				useDict = ChordDict.chord7;
				break;
			case 5:
				useDict = ChordDict.chord9;
				break;
			default:
				return "";
		}

		StringBuilder intervalStringBuilder = getIntervalString(pitchClasses);

		int index;
		for (Map.Entry<String, String> entry : useDict.entrySet()) {
			index = intervalStringBuilder.indexOf(entry.getKey());
			if (index != -1) {
				return pitchClass(pitchClasses.get(index % size)) + entry.getValue();
			}
		}
		return "";
	}

	public static ArrayList<Integer> getPitchClasses(Set<Integer> pressedKey) {
		ArrayList<Integer> pitchClasses = new ArrayList<>();
		for (int note : pressedKey) {
			if (!pitchClasses.contains(note%12)) {
				pitchClasses.add(note%12);
			}
		}
		Collections.sort(pitchClasses);

		return pitchClasses;
	}

	private static StringBuilder getIntervalString(ArrayList<Integer> pitchClasses) {
		int size = pitchClasses.size();
		int difference;
		StringBuilder intervalStringBuilder = new StringBuilder();
		for (int ind = 0; ind < size+5; ind++) {
			difference = pitchClasses.get((ind + 1) % size) - pitchClasses.get(ind % size);
			difference = (difference + 12)%12;
			intervalStringBuilder.append(difference);
		}
		return intervalStringBuilder;
	}



}
