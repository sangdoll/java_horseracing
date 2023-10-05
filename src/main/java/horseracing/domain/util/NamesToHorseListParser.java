package horseracing.domain.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import horseracing.domain.horse.Horse;

public final class NamesToHorseListParser {
	private NamesToHorseListParser() {
	}

	private static void validateNames(String horseNames) {
		if (!horseNames.matches("([가-힣]{5},?)+")) {
			throw new IllegalArgumentException("[ERROR] 유효한 입력 형태가 아닙니다.");
		}
	}

	public static List<Horse> parse(String horseNames) {
		validateNames(horseNames);
		return Arrays.stream(horseNames.split(","))
			.map(Horse::getFromName)
			.collect(Collectors.toList());
	}
}
