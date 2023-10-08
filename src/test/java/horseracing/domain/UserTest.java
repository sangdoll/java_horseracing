package horseracing.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

	@DisplayName("음수의 잔고를 입력할 때, 예외를 발생시키는지 확인합니다.")
	@Test
	void constructor_WithBalanceUnder1000_ThrowsException() {
		assertThatThrownBy(() -> new User("상돌이", -1))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}
}