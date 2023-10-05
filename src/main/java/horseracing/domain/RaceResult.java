package horseracing.domain;

import java.util.List;

import horseracing.domain.horse.Horse;

public final class RaceResult {
	private static final int WINNER = 0;
	private static final int SECOND = 1;
	private static final int THIRD = 2;
	private final List<Horse> result;

	private RaceResult(List<Horse> result) {
		this.result = result;
	}

	public static RaceResult from(List<Horse> result) {
		return new RaceResult(result);
	}

	public List<Horse> getWinner() {
		return List.of(result.get(WINNER));
	}

	public List<Horse> getSecond() {
		return List.of(result.get(SECOND));
	}

	public List<Horse> getThird() {
		return List.of(result.get(THIRD));
	}

	public List<Horse> getWinnerAndSecond() {
		return List.of(result.get(WINNER), result.get(SECOND));
	}

	public List<Horse> getWinnerAndThird() {
		return List.of(result.get(WINNER), result.get(THIRD));
	}

	public List<Horse> getSecondAndThird() {
		return List.of(result.get(SECOND), result.get(THIRD));
	}

	public List<Horse> getWinnerToThird() {
		return List.of(result.get(WINNER), result.get(SECOND), result.get(THIRD));
	}
}
