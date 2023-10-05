package horseracing.domain.horse;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HorseLocationTest {

	@DisplayName("음수 위치값을 넣었을 때 예외를 발생시키는지 확인합니다.")
	@Test
	void from_WithInvalidInput_ThrowsException() {
		assertThatThrownBy(() -> HorseLocation.from(-1))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}

	@DisplayName("0부터 9까지의 값이 아닌 다른 숫자를 넣었을 때 예외를 발생시키는지 확인합니다.")
	@ParameterizedTest
	@ValueSource(ints = {-1, 10})
	void increaseLocation_WithInvalidInput_ThrowsException(int value) {
		HorseLocation horseLocation = HorseLocation.from(0);
		assertThatThrownBy(() -> horseLocation.increaseLocation(value))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}

	@DisplayName("조건에 맞추어 정확하게 전진하는지 확인합니다.")
	@Test
	void increaseLocation_WithValidInput_CorrectlyIncrease() {
		HorseLocation horseLocation = HorseLocation.from(0);

		// 0부터 9까지 모든 숫자에 대해 테스트. 이동 기준이 입력값이 5 이상이므로, 총 5번 전진하여야 합니다..
		for (int number = 0; number < 10; number++) {
			horseLocation = horseLocation.increaseLocation(number);
		}

		assertThat(horseLocation).isEqualTo(HorseLocation.from(5));
	}
}