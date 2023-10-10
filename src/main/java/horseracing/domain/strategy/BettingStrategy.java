package horseracing.domain.strategy;

import java.util.List;

import horseracing.domain.RaceResult;
import horseracing.domain.User;
import horseracing.domain.horse.Horse;

public interface BettingStrategy {
	String WIN = "단승식";
	String QUINELLA = "복승식";
	String PLACE = "연승식";
	String EXACTA = "쌍승식";
	String QUINELLA_PLACE = "복연승식";
	String TRIO = "삼복승식";
	String TRIFECTA = "삼쌍승식";

	int getReward(List<Horse> userPicks, int betAmount, RaceResult result);

	default User updateUserBalance(User user, List<Horse> userPicks, int betAmount, RaceResult result) {
		final int balanceAfterBet = user.getBalance() - betAmount;
		final int reward = getReward(userPicks, betAmount, result);

		return new User(user.getUserName(), balanceAfterBet + reward);
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
