package horseracing.domain.strategy;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import horseracing.domain.BetProcessor;
import horseracing.domain.RaceResult;
import horseracing.domain.User;
import horseracing.domain.horse.Horse;
import horseracing.domain.horse.RaceHorses;
import horseracing.domain.util.NamesToHorseListParser;

class BettingStrategyTest {
	BettingStrategy bettingStrategy;
	User user;
	BetProcessor betProcessor;
	RaceResult raceResult;
	int betAmount;

	@DisplayName("정해진 게임 방식 외에, 다른 이름을 입력하였을 때 오류를 발생시키는지 확인합니다.")
	@ParameterizedTest
	@ValueSource(strings = {"안녕식", "상돌식", "내맘대로식", "기타등등식"})
	void from_WithInvalidInput_ThrowsException(String strategy) {
		assertThatThrownBy(() -> BettingStrategy.from(strategy))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}

	/**
	 * 아래부터의 테스트 코드는, 게임에 승리하였을 때, 각 베팅 방식에 따라 상금을 기반으로
	 * 유저의 잔고를 정확하게 업데이트 하는지에 대한 테스트입니다.
	 */

	@BeforeEach
	void setUp() {
		// 유저 이름은 상돌이, 초기 금액은 1억으로 설정
		user = new User("상돌이", 100000000);

		// 베팅 금액은 무조건 올인
		betAmount = 100000000;

		// 말 5마리를 준비하고, 첫 번째 말부터 각각 1,2,3,4,5 번 전진시킴
		RaceHorses raceHorses = RaceHorses.getFromHorseList(List.of(
			Horse.getFromNameAndLocation("오등입니다", 1),
			Horse.getFromNameAndLocation("사등입니다", 2),
			Horse.getFromNameAndLocation("삼등입니다", 3),
			Horse.getFromNameAndLocation("이등입니다", 4),
			Horse.getFromNameAndLocation("일등입니다", 5)
		));

		// 이를 기반으로, RaceResult 객체를 설정함.
		raceResult = RaceResult.from(raceHorses.getRankResult());
	}

	/**
	 * 모든 베팅 방식에 대한 1차적인 테스트를 진행합니다
	 */
	@DisplayName("모든 베팅 방식이 정확하게 결과를 리턴하는지 확인합니다.")
	@ParameterizedTest
	@CsvSource(value = {"단승식/일등입니다/6.6", "복승식/일등입니다,이등입니다/4.1", "쌍승식/일등입니다,이등입니다/12.7",
		"연승식/일등입니다/1.7", "연승식/이등입니다/1.3", "연승식/삼등입니다/2.0",
		"복연승식/일등입니다,이등입니다/2.3", "복연승식/일등입니다,삼등입니다/5.2", "복연승식/이등입니다,삼등입니다/2.8",
		"삼복승식/일등입니다,이등입니다,삼등입니다/6.9", "삼쌍승식/일등입니다,이등입니다,삼등입니다/56.1"}, delimiter = '/')
	void updateUserByBetStrategy_ForAllStrategy_ReturnCorrectResult(String strategy, String horseNames, double odd) {
		bettingStrategy = BettingStrategy.from(strategy);
		List<Horse> usersPick = NamesToHorseListParser.parse(horseNames);

		// 게임에서 승리했을 때, 예상되는 유저의 잔고
		int reward = (int)(betAmount * odd);
		int expectedBalance = (user.getBalance() - betAmount) + reward;

		// 메서드가 정확히 승리를 기반으로 유저의 정보를 업데이트 하여 리턴하는지 확인
		assertThat(bettingStrategy.updateUserBalance(user, usersPick, betAmount, raceResult))
			.isEqualTo(new User(user.getUserName(), expectedBalance));
	}

	/**
	 * 복승식, 복연승식, 삼복승식은 순서를 고려하지 않으므로,
	 * 모든 순서를 넣어 정상적으로 작동하는지 다시 한 번 테스트하였습니다.
	 */

	@DisplayName("모든 가능한 순서를 넣어, 복승식에 대해 테스트합니다.")
	@ParameterizedTest
	@ValueSource(strings = {"일등입니다,이등입니다", "이등입니다,일등입니다"})
	void updateUserByBetStrategy_ForAllCaseInQUINELLA_ReturnCorrectResult(String horseNames) {
		final double odd = 4.1;
		bettingStrategy = BettingStrategy.from("복승식");
		List<Horse> usersPick = NamesToHorseListParser.parse(horseNames);

		// 게임에서 승리했을 때, 예상되는 유저의 잔고
		int reward = (int)(betAmount * odd);
		int expectedBalance = (user.getBalance() - betAmount) + reward;

		// 메서드가 정확히 승리를 기반으로 유저의 정보를 업데이트 하여 리턴하는지 확인
		assertThat(bettingStrategy.updateUserBalance(user, usersPick, betAmount, raceResult))
			.isEqualTo(new User(user.getUserName(), expectedBalance));
	}

	@DisplayName("모든 가능한 순서를 넣어, 복연승식에 대해 테스트합니다.")
	@ParameterizedTest
	@CsvSource(value = {"일등입니다,이등입니다/2.3", "이등입니다,일등입니다/2.3",
		"일등입니다,삼등입니다/5.2", "삼등입니다,일등입니다/5.2",
		"이등입니다,삼등입니다/2.8", "삼등입니다,이등입니다/2.8"}, delimiter = '/')
	void updateUserByBetStrategy_ForAllCaseInQUINELLAPLACE_ReturnCorrectResult(String horseNames, double odd) {
		bettingStrategy = BettingStrategy.from("복연승식");
		List<Horse> usersPick = NamesToHorseListParser.parse(horseNames);

		// 게임에서 승리했을 때, 예상되는 유저의 잔고
		int reward = (int)(betAmount * odd);
		int expectedBalance = (user.getBalance() - betAmount) + reward;

		// 메서드가 정확히 승리를 기반으로 유저의 정보를 업데이트 하여 리턴하는지 확인
		assertThat(bettingStrategy.updateUserBalance(user, usersPick, betAmount, raceResult))
			.isEqualTo(new User(user.getUserName(), expectedBalance));
	}

	@DisplayName("모든 가능한 순서를 넣어, 삼복승식에 대해 테스트합니다.")
	@ParameterizedTest
	@ValueSource(strings = {"일등입니다,이등입니다,삼등입니다", "일등입니다,삼등입니다,이등입니다",
		"이등입니다,일등입니다,삼등입니다", "이등입니다,삼등입니다,일등입니다",
		"삼등입니다,일등입니다,이등입니다", "삼등입니다,이등입니다,일등입니다"})
	void updateUserByBetStrategy_ForAllCaseInTRIO_ReturnCorrectResult(String horseNames) {
		final double odd = 6.9;
		bettingStrategy = BettingStrategy.from("삼복승식");
		List<Horse> usersPick = NamesToHorseListParser.parse(horseNames);

		// 게임에서 승리했을 때, 예상되는 유저의 잔고
		int reward = (int)(betAmount * odd);
		int expectedBalance = (user.getBalance() - betAmount) + reward;

		// 메서드가 정확히 승리를 기반으로 유저의 정보를 업데이트 하여 리턴하는지 확인
		assertThat(bettingStrategy.updateUserBalance(user, usersPick, betAmount, raceResult))
			.isEqualTo(new User(user.getUserName(), expectedBalance));
	}
}