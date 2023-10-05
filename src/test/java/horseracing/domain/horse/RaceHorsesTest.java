package horseracing.domain.horse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

class RaceHorsesTest {
	RaceHorses raceHorses;
	@BeforeEach
	void setUp() {
		raceHorses = RaceHorses.getFromNames("안녕하세요,상돌이홧팅,경마게임짱");
		// 첫 번째 말은 한번, 두번째와 세번째 말은 총 세번 전진시킴
		raceHorses.moveAllHorses(List.of(5, 6, 7));
		raceHorses.moveAllHorses(List.of(4, 5, 6));
		raceHorses.moveAllHorses(List.of(0, 8, 9));
	}

	@DisplayName("컴마 이외의 구분자 형식이 들어갈 때, 예외를 발생시키는지 확인합니다.")
	@Test
	void getFromNames_WithInvalidInput_ThrowsException() {
		String names = "안녕하세요/상돌이홧팅/경마게임짱";
		assertThatThrownBy(() -> RaceHorses.getFromNames(names))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}

	@DisplayName("모든 말을 입력에 따라 정확히 전진시키는지 확인합니다.")
	@Test
	void moveAllHorses_WithValidInput_MoveCorrectly() {
		// raceHorses의 말 리스트와 동일한 리스트를 설정
		List<Horse> expected = List.of(Horse.getFromNameAndLocation("안녕하세요", 1),
			Horse.getFromNameAndLocation("상돌이홧팅", 3),
			Horse.getFromNameAndLocation("경마게임짱", 3));

		// 두 결과가 같은지 확인
		assertThat(raceHorses).isEqualTo(RaceHorses.getFromHorseList(expected));
	}

	@DisplayName("말을 전진시킨 뒤, 순위를 정확하게 구하는지 확인합니다.")
	@Test
	void getRankResult_AfterMove_CalculateCorrectRank() {
		// 전진시킨 뒤, 결과 순위 리스트를 얻기
		List<Horse> raceResult = raceHorses.getRankResult();

		// 순위 기준은, location 값이 큰 말이 리스트의 앞에 오도록 하며, 값이 같으면 이름의 가나다순으로 결정
		List<Horse> expected = List.of(Horse.getFromNameAndLocation("경마게임짱", 3),
			Horse.getFromNameAndLocation("상돌이홧팅", 3),
			Horse.getFromNameAndLocation("안녕하세요", 1));

		assertThat(raceResult).isEqualTo(expected);
	}

	@DisplayName("경주 상황을 정확하게 출력하는지 확인")
	@Test
	void getPrintResult_AfterMove_ReturnCorrectResult() {
		String expect = ("안녕하세요" + " :  " + "-" + "\n")
			+ ("상돌이홧팅" + " :  " + "---" + "\n")
			+ ("경마게임짱" + " :  " + "---" + "\n");

		assertThat(raceHorses.getPrintResult()).isEqualTo(expect);
	}
}