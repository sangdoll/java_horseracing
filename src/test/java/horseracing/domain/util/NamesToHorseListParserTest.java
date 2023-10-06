package horseracing.domain.util;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import horseracing.domain.horse.Horse;

class NamesToHorseListParserTest {

	@DisplayName("컴마가 아닌 구분자를 사용하였을 때, 예외를 발생시키는지 확인합니다.")
	@Test
	void parse_WithDelimiterNotComma_ThrowsException() {
		assertThatThrownBy(() -> NamesToHorseListParser.parse("안녕하세요/저는상돌이/입니다방가"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}

	@DisplayName("유효한 값을 넣었을 때, 정확히 파싱하는지 확인합니다.")
	@Test
	void parse_WithValidInput_ParseCorrectly() {
		String input = "안녕하세요,저는상돌이,입니다방가";
		List<Horse> expect = List.of(Horse.getFromName("안녕하세요"),
			Horse.getFromName("저는상돌이"),
			Horse.getFromName("입니다방가"));

		assertThat(NamesToHorseListParser.parse(input)).isEqualTo(expect);
	}
}