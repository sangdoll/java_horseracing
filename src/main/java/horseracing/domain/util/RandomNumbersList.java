package horseracing.domain.util;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public final class RandomNumbersList {
	private static final int MIN = 0;
	private static final int MAX = 9;
	private static final Random RANDOM = new Random();

	public static List<Integer> getRandomNumbers(int count) {
		return RANDOM.ints(MIN, MAX + 1)
			.limit(count)
			.boxed()
			.collect(Collectors.toList());
	}
}
