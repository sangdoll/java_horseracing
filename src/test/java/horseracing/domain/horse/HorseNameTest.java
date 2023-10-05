package horseracing.domain.horse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HorseNameTest {

	@DisplayName("한글 5글자가 아닌 다른 이름을 넣었을 때, 예외를 발생시키는지 확인합니다.")
	@ParameterizedTest
	@ValueSource(strings = {"상돌이12","안녕요","가나다라마바","말달리자","horse","horse123"})
	void from_WithInvalidInput_ThrowsException(String name) {
		assertThatThrownBy(() -> HorseName.from(name))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}
}