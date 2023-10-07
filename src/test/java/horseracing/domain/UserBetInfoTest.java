package horseracing.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserBetInfoTest {
	@DisplayName("유저가 말을 선택하지 않았을 때, 예외를 발생시키는지 확인합니다.")
	@Test
	void getUserBetInfo_WithNoHorseChoice_ThrowsException() {
		int betAmount = 1000;
		int userBalance = 1000;

		assertThatThrownBy(() -> UserBetInfo.getUserBetInfo("", betAmount, userBalance))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}

	@DisplayName("천원 미만을 베팅했을 때, 예외를 발생시키는지 확인합니다.")
	@Test
	void getUserBetInfo_WithBetAmountUnder1000_ThrowsException() {
		int betAmount = 999;
		int userBalance = 1000;

		assertThatThrownBy(() -> UserBetInfo.getUserBetInfo("상돌이", betAmount, userBalance))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}

	@DisplayName("잔고를 초과하는 금액을 베팅했을 때, 예외를 발생시키는지 확인합니다.")
	@Test
	void getUserBetInfo_WithBetAmountUpperBalance_ThrowsException() {
		int betAmount = 1001;
		int userBalance = 1000;

		assertThatThrownBy(() -> UserBetInfo.getUserBetInfo("상돌이", betAmount, userBalance))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}
}