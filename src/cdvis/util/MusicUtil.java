package cdvis.util;

import java.util.Set;

public class MusicUtil {

	private MusicUtil() {
	}
	
	public static String pitchClass(int n) {
		switch (n%12) {
		case 0: return "A";
		case 1: return "Bb";
		case 2: return "B";
		case 3: return "C";
		case 4: return "C#";
		case 5: return "D";
		case 6: return "Eb";
		case 7: return "E";
		case 8: return "F";
		case 9: return "F#";
		case 10: return "G";
		case 11: return "G#";
		}
		return "";
	}
	
	public static String pitchName(int n) {
		String pitchClass = pitchClass(n);
		int register = n/12 + 1;
		if (pitchClass != "A" && pitchClass != "Bb" && pitchClass != "B") {
			register++;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(pitchClass).append(register).append("wh");
		return sb.toString();
	}
	
	public static String chordString(Set<Integer> n) {
		StringBuilder sb = new StringBuilder();
		
		for (int i:n) {
			sb.append(pitchName(i)).append("+");
		}
		
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length()-1);
		}
		
		return sb.toString();
	}

}
