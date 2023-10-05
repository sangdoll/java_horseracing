package horseracing.domain.strategy;

import java.util.List;

import horseracing.domain.RaceResult;
import horseracing.domain.horse.Horse;

public final class QuinellaPlace implements BettingStrategy {
	private static final double ODDS_FIRST_AND_SECOND = 2.3;
	private static final double ODDS_FIRST_AND_THIRD = 5.2;
	private static final double ODDS_SECOND_AND_THIRD = 2.8;

	@Override
	public int getReward(List<Horse> userPicks, RaceResult result, int betAmount) {
		Horse first = userPicks.get(0);
		Horse second = userPicks.get(1);

		if (isWinnerAndSecond(first, second, result.getWinnerAndSecond())) {
			return (int)(betAmount * ODDS_FIRST_AND_SECOND);
		}
		if (isWinnerAndThird(first, second, result.getWinnerAndThird())) {
			return (int)(betAmount * ODDS_FIRST_AND_THIRD);
		}
		if (isSecondAndThird(first, second, result.getSecondAndThird())) {
			return (int)(betAmount * ODDS_SECOND_AND_THIRD);
		}
		return 0;
	}

	public boolean isWinnerAndSecond(Horse first, Horse second, List<Horse> winnerAndSecond) {
		return winnerAndSecond.contains(first) && winnerAndSecond.contains(second);
	}

	public boolean isWinnerAndThird(Horse first, Horse second, List<Horse> winnerAndThird) {
		return winnerAndThird.contains(first) && winnerAndThird.contains(second);
	}

	public boolean isSecondAndThird(Horse first, Horse second, List<Horse> secondAndThird) {
		return secondAndThird.contains(first) && secondAndThird.contains(second);
	}
}
