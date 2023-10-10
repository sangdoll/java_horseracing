package horseracing.domain.strategy;

import java.util.List;

import horseracing.domain.RaceResult;
import horseracing.domain.horse.Horse;

public final class Place implements BettingStrategy{
	private static final double ODDS_WINNER = 1.7;
	private static final double ODDS_SECOND = 1.3;
	private static final double ODDS_THIRD = 2.0;

	@Override
	public int getReward(List<Horse> userPicks, int betAmount, RaceResult result) {
		if (userPicks.equals(result.getWinner())) {
			return (int)(betAmount * ODDS_WINNER);
		}
		if (userPicks.equals(result.getSecond())) {
			return (int)(betAmount * ODDS_SECOND);
		}
		if (userPicks.equals(result.getThird())) {
			return (int)(betAmount * ODDS_THIRD);
		}
		return 0;
	}
}
