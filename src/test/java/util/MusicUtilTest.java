package util;

import java.util.HashSet;
import java.util.Set;

import cdvis.util.MusicUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MusicUtilTest {

	@Test
	void recognizeChordTest() {
		Set<Integer> set = new HashSet<>();
		set.add(1);
		set.add(5);
		set.add(11);
		set.add(14);

		String result = MusicUtil.recognizeChord(set);
		assertEquals(result, "Bb7b9");
	}

}
