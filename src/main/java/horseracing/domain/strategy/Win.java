package horseracing.domain.strategy;

import java.util.List;

import horseracing.domain.RaceResult;
import horseracing.domain.horse.Horse;

public final class Win implements BettingStrategy {
	private static final double ODDS = 6.6;

	@Override
	public int getReward(List<Horse> userPicks, RaceResult result, int betAmount) {
		if (userPicks.equals(result.getWinner())) {
			return (int)(betAmount * ODDS);
		}
		return 0;
	}
}
