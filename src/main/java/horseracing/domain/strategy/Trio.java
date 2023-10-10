package horseracing.domain.strategy;

import java.util.List;

import horseracing.domain.RaceResult;
import horseracing.domain.horse.Horse;

public final class Trio implements BettingStrategy{
	private static final double ODDS = 6.9;

	@Override
	public int getReward(List<Horse> userPicks, int betAmount, RaceResult result) {
		List<Horse> winnerToThird = result.getWinnerToThird();

		if (isContainsAll(userPicks, winnerToThird)) {
			return (int)(betAmount * ODDS);
		}

		return 0;
	}

	private boolean isContainsAll(List<Horse> userPicks, List<Horse> winnerToThird) {
		Horse first = userPicks.get(0);
		Horse second = userPicks.get(1);
		Horse third = userPicks.get(2);

		return winnerToThird.contains(first) && winnerToThird.contains(second) &&
			winnerToThird.contains(third);
	}
}
