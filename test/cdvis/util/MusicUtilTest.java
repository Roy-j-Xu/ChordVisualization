package cdvis.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



class MusicUtilTest {
	
	@Test
	void testPitchName() {
		System.out.println(MusicUtil.pitchName(63));
		System.out.println(MusicUtil.pitchName(60));
	}

}
