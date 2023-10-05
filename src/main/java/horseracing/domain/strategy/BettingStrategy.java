package horseracing.domain.strategy;

import java.util.List;

import horseracing.domain.RaceResult;
import horseracing.domain.User;
import horseracing.domain.UserBetInfo;
import horseracing.domain.horse.Horse;

public interface BettingStrategy {

	String win = "단승식";
	String quinella = "복승식";
	String place = "연승식";
	String exacta = "쌍승식";
	String quinellaPlace = "복연승식";
	String trio = "삼복승식";
	String trifecta = "삼쌍승식";

	int getReward(List<Horse> userPicks, RaceResult result, int betAmount);

	default User updateUserByBetStrategy(User user, UserBetInfo betInfo, RaceResult result) {
		final int betAmount = betInfo.getBetAmount();
		final List<Horse> userPicks = betInfo.getUserPicks();
		final int balanceAfterBet = user.getBalance() - betAmount;
		final int reward = getReward(userPicks, result, betAmount);

		return new User(user.getUserName(), balanceAfterBet + reward);
	}

	default String getResultMessage(UserBetInfo betInfo, RaceResult result) {
		int reward = getReward(betInfo.getUserPicks(), result, betInfo.getBetAmount());

		if (reward == 0) {
			return "아쉽게도 맞추지 못하였습니다. 다음에 다시 도전해주세요 !";
		}
		return "승리하였습니다! 획득 상금 : " + reward;
	}

	static BettingStrategy from(String strategy) {
		if (win.equals(strategy)) {
			return new Win();
		}
		if (quinella.equals(strategy)) {
			return new Quinella();
		}
		if (place.equals(strategy)) {
			return new Place();
		}
		if (exacta.equals(strategy)) {
			return new Exacta();
		}
		if (quinellaPlace.equals(strategy)) {
			return new QuinellaPlace();
		}
		if (trio.equals(strategy)) {
			return new Trio();
		}
		if (trifecta.equals(strategy)) {
			return new Trifecta();
		}
		throw new IllegalArgumentException("[ERROR] 정확한 베팅 방식을 입력해주세요");
	}
}
