package horseracing.domain.strategy;

import java.util.List;

import horseracing.domain.RaceResult;
import horseracing.domain.horse.Horse;

public final class Trifecta implements BettingStrategy{
	private static final double ODDS = 56.1;

	@Override
	public int getReward(List<Horse> userPicks, RaceResult result, int betAmount) {
		if (userPicks.equals(result.getWinnerToThird())) {
			return (int)(betAmount * ODDS);
		}
		return 0;
	}
}
