package horseracing.domain.strategy;

import java.util.List;

import horseracing.domain.RaceResult;
import horseracing.domain.User;
import horseracing.domain.UserBetInfo;
import horseracing.domain.horse.Horse;

public interface BettingStrategy {
	String WIN = "단승식";
	String QUINELLA = "복승식";
	String PLACE = "연승식";
	String EXACTA = "쌍승식";
	String QUINELLA_PLACE = "복연승식";
	String TRIO = "삼복승식";
	String TRIFECTA = "삼쌍승식";

	int getReward(List<Horse> userPicks, RaceResult result, int betAmount);

	default User updateUserByBetStrategy(User user, UserBetInfo betInfo, RaceResult result) {
		final int betAmount = betInfo.getBetAmount();
		final List<Horse> userPicks = betInfo.getUserPicks();
		final int balanceAfterBet = user.getBalance() - betAmount;
		final int reward = getReward(userPicks, result, betAmount);

		return new User(user.getUserName(), balanceAfterBet + reward);
	}

	default String getResultMessage(UserBetInfo betInfo, RaceResult result) {
		final int reward = getReward(betInfo.getUserPicks(), result, betInfo.getBetAmount());
		final String messageIfWin = "승리하였습니다! 획득 상금 : " + reward;
		final String messageIfLose = "아쉽게도 맞추지 못하였습니다. 다음에 다시 도전해주세요 !";

		if (reward == 0) {
			return messageIfLose;
		}
		return messageIfWin;
	}

	static BettingStrategy from(String strategy) {
		if (WIN.equals(strategy)) {
			return new Win();
		}
		if (QUINELLA.equals(strategy)) {
			return new Quinella();
		}
		if (PLACE.equals(strategy)) {
			return new Place();
		}
		if (EXACTA.equals(strategy)) {
			return new Exacta();
		}
		if (QUINELLA_PLACE.equals(strategy)) {
			return new QuinellaPlace();
		}
		if (TRIO.equals(strategy)) {
			return new Trio();
		}
		if (TRIFECTA.equals(strategy)) {
			return new Trifecta();
		}
		throw new IllegalArgumentException("[ERROR] 정확한 베팅 방식을 입력해주세요");
	}
}
