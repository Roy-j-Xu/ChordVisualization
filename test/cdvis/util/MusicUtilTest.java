package cdvis.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class MusicUtilTest {
	
	@Test
	void testPitchName() {
		System.out.println(MusicUtil.pitchName(63));
		System.out.println(MusicUtil.pitchName(60));
	}

	@Test
	void testChordString() {
		boolean[] b = {true, true, false, false, true};
		assertEquals(MusicUtil.chordString(b), "A0wh+Bb0wh+C#1wh");
	}

}
