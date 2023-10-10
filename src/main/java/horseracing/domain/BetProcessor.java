package horseracing.domain;

import java.util.List;

import horseracing.domain.horse.Horse;
import horseracing.domain.strategy.BettingStrategy;
import horseracing.domain.util.NamesToHorseListParser;

public class BetProcessor {
	private final BettingStrategy bettingStrategy;
	private final List<Horse> userPicks;
	private final int betAmount;

	private BetProcessor(String strategy, List<Horse> userPicks, int betAmount) {
		if (userPicks.isEmpty()) {
			throw new IllegalArgumentException("[ERROR] 적어도 한 마리의 말은 선택해야합니다.");
		}
		if (betAmount < 1000) {
			throw new IllegalArgumentException("[ERROR] 적어도 천 원 이상 베팅해야합니다.");
		}

		this.bettingStrategy = BettingStrategy.from(strategy);
		this.userPicks = userPicks;
		this.betAmount = betAmount;
	}

	public static BetProcessor of(String strategy, String pick, int betAmount) {
		List<Horse> userPicks = NamesToHorseListParser.parse(pick);
		return new BetProcessor(strategy, userPicks, betAmount);
	}

	public User updateUserWithResult(User user, RaceResult result) {
		return bettingStrategy.updateUserBalance(user, userPicks, betAmount, result);
	}

	public String getResultMessage(RaceResult result) {
		final int reward = bettingStrategy.getReward(userPicks, betAmount, result);
		final String messageIfWin = "승리하였습니다! 획득 상금 : " + reward;
		final String messageIfLose = "아쉽게도 맞추지 못하였습니다. 다음에 다시 도전해주세요 !";

		if (reward == 0) {
			return messageIfLose;
		}

		return messageIfWin;
	}
}
