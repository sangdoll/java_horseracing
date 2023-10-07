package horseracing.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

	@DisplayName("1000원 미만의 잔고를 넣었을 때, 예외를 발생시키는지 확인합니다.")
	@Test
	void constructor_WithBalanceUnder1000_ThrowsException() {
		assertThatThrownBy(() -> new User("상돌이", 999))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}
}