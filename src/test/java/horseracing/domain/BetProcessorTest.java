package horseracing.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BetProcessorTest {
	String strategy;

	@BeforeEach
	void setUp() {
		strategy = "단승식";
	}

	@DisplayName("유저가 말을 선택하지 않았을 때, 예외를 발생시키는지 확인합니다.")
	@Test
	void getUserBetInfo_WithNoHorseChoice_ThrowsException() {
		final String usersPick = "";
		int betAmount = 1000;

		assertThatThrownBy(() -> BetProcessor.of(strategy, usersPick, betAmount))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}

	@DisplayName("천원 미만을 베팅했을 때, 예외를 발생시키는지 확인합니다.")
	@Test
	void getUserBetInfo_WithBetAmountUnder1000_ThrowsException() {
		final String usersPick = "파이팅대디";
		int betAmount = 999;

		assertThatThrownBy(() -> BetProcessor.of(strategy, usersPick, betAmount))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}
}