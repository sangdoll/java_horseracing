package horseracing.domain.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocationToUIParserTest {

	@DisplayName("음수값을 넣었을 때, 예외를 발생시키는지 확인합니다.")
	@Test
	void parse_WithNegativeInput_ThrowsException() {
		assertThatThrownBy(() -> LocationToUIParser.parse(-1))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}

	@DisplayName("유효한 값을 넣었을 때, 정확한 값을 리턴하는지 확인합니다.")
	@Test
	void parse_WithValidInput_ParseCorrectly() {
		String expected = "----------";

		assertThat(LocationToUIParser.parse(10)).isEqualTo(expected);
	}
}