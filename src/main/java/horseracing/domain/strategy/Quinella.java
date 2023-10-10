package horseracing.domain.strategy;

import java.util.List;

import horseracing.domain.RaceResult;
import horseracing.domain.horse.Horse;

public final class Quinella implements BettingStrategy{
	private static final double ODDS = 4.1;

	@Override
	public int getReward(List<Horse> userPicks, int betAmount, RaceResult result) {
		List<Horse> winnerAndSecond = result.getWinnerAndSecond();
		Horse first = userPicks.get(0);
		Horse second = userPicks.get(1);

		if (winnerAndSecond.equals(userPicks)) {
			return (int)(betAmount * ODDS);
		}
		if (winnerAndSecond.equals(List.of(second, first))) {
			return (int)(betAmount * ODDS);
		}

		return 0;
	}
}
