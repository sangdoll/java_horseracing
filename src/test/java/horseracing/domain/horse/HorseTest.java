package horseracing.domain.horse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class HorseTest {

	@DisplayName("유효한 입력에 대해 말을 정확히 전진시키는지 확인합니다.")
	@Test
	void move_WithValidInput_MoveCorrectly() {
		Horse horse = Horse.getFromName("상돌하이루");
		// move 메서드에 0부터 9까지의 값을 넣으면, 총 5번 전진해야함
		for (int number = 0; number < 10; number++) {
			horse.move(number);
		}

		// Equals, hashcode가 정확히 재정의되어있는지도 확인가능
		assertThat(horse).isEqualTo(Horse.getFromNameAndLocation("상돌하이루", 5));
	}
}