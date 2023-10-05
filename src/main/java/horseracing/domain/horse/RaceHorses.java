package horseracing.domain.horse;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import horseracing.domain.util.LocationToUIParser;
import horseracing.domain.util.NamesToHorseListParser;

public final class RaceHorses {
	private static final String NEW_LINE = System.getProperty("line.separator");
	private static final String COLON_WITH_SPACE = " : ";
	private static final String SPACE = " ";
	private final List<Horse> horseList;

	private RaceHorses(List<Horse> horseList) {
		this.horseList = horseList;
	}
	private RaceHorses(String horseNames) {
		this.horseList = NamesToHorseListParser.parse(horseNames);
	}

	public static RaceHorses getFromNames(String horseNames) {
		return new RaceHorses(horseNames);
	}

	public static RaceHorses getFromHorseList(List<Horse> horseList) {
		return new RaceHorses(horseList);
	}

	public void moveAllHorses(List<Integer> numbers) {
		IntStream.range(0, horseList.size())
			.forEach(horseNumber -> horseList.get(horseNumber).move(numbers.get(horseNumber)));
	}

	public List<Horse> getRankResult() {
		return horseList.stream()
			.sorted(Comparator.comparingInt(Horse::getLocation).reversed()
				.thenComparing(Horse::getName))
			.collect(Collectors.toList());
	}

	public String getPrintResult() {
		return horseList.stream()
			.map(horse -> horse.getName()
				+ COLON_WITH_SPACE
				+ SPACE
				+ LocationToUIParser.parse(horse.getLocation())
				+ NEW_LINE
			)
			.collect(Collectors.joining());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		RaceHorses that = (RaceHorses)o;
		return Objects.equals(horseList, that.horseList);
	}

	@Override
	public int hashCode() {
		return Objects.hash(horseList);
	}
}
