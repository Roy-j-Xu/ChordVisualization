package cdvis.util;

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

}
