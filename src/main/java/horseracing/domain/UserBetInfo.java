package horseracing.domain;

import java.util.List;

import horseracing.domain.horse.Horse;
import horseracing.domain.util.NamesToHorseListParser;

public class UserBetInfo {
	private final List<Horse> userPicks;
	private final int betAmount;

	private UserBetInfo(List<Horse> userPicks, int betAmount, int userBalance) {
		if (userPicks.isEmpty()) {
			throw new IllegalArgumentException("[ERROR] 적어도 한 마리의 말은 선택해야합니다.");
		}
		if (betAmount < 1000) {
			throw new IllegalArgumentException("[ERROR] 적어도 천 원 이상 베팅해야합니다.");
		}
		if (betAmount > userBalance) {
			throw new IllegalArgumentException("[ERROR] 잔고를 초과하는 금액을 베팅할 수 없습니다.");
		}
		this.userPicks = userPicks;
		this.betAmount = betAmount;
	}

	public static UserBetInfo getUserBetInfo(String pick, int betAmount, int userBalance) {
		List<Horse> userPicks = NamesToHorseListParser.parse(pick);
		return new UserBetInfo(userPicks, betAmount, userBalance);
	}

	public int getBetAmount() {
		return betAmount;
	}

	public List<Horse> getUserPicks() {
		return userPicks;
	}
}
