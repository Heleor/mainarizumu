package creator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Utiltiy classes
 */
public class Sets {
	private Sets() {}
	
	public static Set<Integer> options(int size) {
		Set<Integer> set = new HashSet<>();
		for (int i = 1; i <= size; i++) {
			set.add(i);
		}
		return set;
	}

	public static Set<Integer> single(int i) {
		return Collections.singleton(i);
	}
}
