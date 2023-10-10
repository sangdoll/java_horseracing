package horseracing.domain.strategy;

import java.util.List;

import horseracing.domain.RaceResult;
import horseracing.domain.horse.Horse;

public final class Exacta implements BettingStrategy {
	private static final double ODDS = 12.7;

	@Override
	public int getReward(List<Horse> userPicks, int betAmount, RaceResult result) {
		if (userPicks.equals(result.getWinnerAndSecond())) {
			return (int)(betAmount * ODDS);
		}
		return 0;
	}
}
