package cdvis.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;



class MusicUtilTest {

	@Test
	void recognizeChordTest() {
		Set<Integer> set = new HashSet<>();
		set.add(1);
		set.add(5);
		set.add(14);
		set.add(16);

		String result = MusicUtil.recognizeChord(set);
		System.out.println(result);
	}

}
