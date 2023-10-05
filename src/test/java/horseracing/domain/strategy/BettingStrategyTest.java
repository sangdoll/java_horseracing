package horseracing.domain.strategy;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import horseracing.domain.RaceResult;
import horseracing.domain.User;
import horseracing.domain.UserBetInfo;
import horseracing.domain.horse.Horse;
import horseracing.domain.horse.RaceHorses;

class BettingStrategyTest {
	BettingStrategy bettingStrategy;
	User user;
	UserBetInfo userBetInfo;
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

	@BeforeEach
	void setUp() {
		// 유저 이름은 상돌이, 초기 금액은 1억으로 설정
		user = new User("상돌이", 100000000);

		// 베팅 금액은 무조건 올인
		betAmount = 100000000;

		// 말 5마리를 준비하고, 첫 번째 말부터 각각 1,2,3,4,5 번 전진시킴
		RaceHorses raceHorses = RaceHorses.getFromHorseList(List.of(
			Horse.getFromNameAndLocation("일번마임요", 1),
			Horse.getFromNameAndLocation("이번마임요", 2),
			Horse.getFromNameAndLocation("삼번마임요", 3),
			Horse.getFromNameAndLocation("사번마임요", 4),
			Horse.getFromNameAndLocation("오번마임요", 5)
		));

		// 이를 기반으로, RaceResult 객체를 설정함.
		raceResult = RaceResult.from(raceHorses.getRankResult());
	}

	@DisplayName("단승식 게임에 대해 테스트합니다.")
	@Test
	void getReward_WhenStrategyIsWIN_CalculateCorrectly() {
		bettingStrategy = BettingStrategy.from("단승식");
		userBetInfo = UserBetInfo.getUserBetInfo("오번마임요", betAmount);

		// 게임에서 승리했을 시에, 예상되는 잔고
		int expectedBalance = user.getBalance() - betAmount + (int)(betAmount * 6.6);

		assertThat(bettingStrategy.updateUserByBetStrategy(user, userBetInfo, raceResult))
			.isEqualTo(new User(user.getUserName(), expectedBalance));
	}

	@DisplayName("복승식 게임에 대해 테스트합니다.")
	@Test
	void getReward_WhenStrategyIsQUINELLA_CalculateCorrectly() {
		bettingStrategy = BettingStrategy.from("복승식");
		userBetInfo = UserBetInfo.getUserBetInfo("오번마임요,사번마임요", betAmount);

		// 게임에서 승리했을 시에, 예상되는 잔고
		int expectedBalance = user.getBalance() - betAmount + (int)(betAmount * 4.1);

		assertThat(bettingStrategy.updateUserByBetStrategy(user, userBetInfo, raceResult))
			.isEqualTo(new User(user.getUserName(), expectedBalance));
	}

	@DisplayName("연승식 게임에 대해 테스트합니다. 1,2,3등마를 맞추는 모든 경우를 확인합니다.")
	@ParameterizedTest
	@CsvSource(value = {"오번마임요/1.7", "사번마임요/1.3", "삼번마임요/2.0"}, delimiter = '/')
	void getReward_WhenStrategyIsPLACE_CalculateCorrectly(String horseName, double oddsIfWin) {
		bettingStrategy = BettingStrategy.from("연승식");
		userBetInfo = UserBetInfo.getUserBetInfo(horseName, betAmount);

		// 게임에서 승리했을 시에, 예상되는 잔고
		int expectedBalance = user.getBalance() - betAmount + (int)(betAmount * oddsIfWin);

		assertThat(bettingStrategy.updateUserByBetStrategy(user, userBetInfo, raceResult))
			.isEqualTo(new User(user.getUserName(), expectedBalance));
	}

	@DisplayName("쌍승식 게임에 대해 테스트합니다.")
	@Test
	void getReward_WhenStrategyIsEXACTA_CalculateCorrectly() {
		bettingStrategy = BettingStrategy.from("쌍승식");
		userBetInfo = UserBetInfo.getUserBetInfo("오번마임요,사번마임요", betAmount);

		// 게임에서 승리했을 시에, 예상되는 잔고
		int expectedBalance = user.getBalance() - betAmount + (int)(betAmount * 12.7);

		assertThat(bettingStrategy.updateUserByBetStrategy(user, userBetInfo, raceResult))
			.isEqualTo(new User(user.getUserName(), expectedBalance));
	}

	@DisplayName("복연승식 게임에 대해 테스트합니다.")
	@ParameterizedTest
	@CsvSource(value = {"오번마임요,사번마임요/2.3", "오번마임요,삼번마임요/5.2", "사번마임요,삼번마임요/2.8"}, delimiter = '/')
	void getReward_WhenStrategyIsQUINELLAPLACE_CalculateCorrectly(String horseNames, double oddsIfWin) {
		bettingStrategy = BettingStrategy.from("복연승식");
		userBetInfo = UserBetInfo.getUserBetInfo(horseNames, betAmount);

		// 게임에서 승리했을 시에, 예상되는 잔고
		int expectedBalance = user.getBalance() - betAmount + (int)(betAmount * oddsIfWin);

		assertThat(bettingStrategy.updateUserByBetStrategy(user, userBetInfo, raceResult))
			.isEqualTo(new User(user.getUserName(), expectedBalance));
	}

	@DisplayName("삼복승식 게임에 대해 모든 가능한 순서를 넣어 테스트합니다. ")
	@ParameterizedTest
	@ValueSource(strings = {"오번마임요,사번마임요,삼번마임요", "오번마임요,삼번마임요,사번마임요", "삼번마임요,사번마임요,오번마임요"
	,"삼번마임요,오번마임요,사번마임요", "사번마임요,오번마임요,삼번마임요", "사번마임요,삼번마임요,오번마임요"})
	void getReward_WhenStrategyIsTRIO_CalculateCorrectly(String horseNames) {
		bettingStrategy = BettingStrategy.from("삼복승식");
		userBetInfo = UserBetInfo.getUserBetInfo(horseNames, betAmount);

		// 게임에서 승리했을 시에, 예상되는 잔고
		int expectedBalance = user.getBalance() - betAmount + (int)(betAmount * 6.9);

		assertThat(bettingStrategy.updateUserByBetStrategy(user, userBetInfo, raceResult))
			.isEqualTo(new User(user.getUserName(), expectedBalance));
	}

	@DisplayName("삼쌍승식 게임에 대해 테스트합니다.")
	@Test
	void getReward_WhenStrategyIsTRIFECTA_CalculateCorrectly() {
		bettingStrategy = BettingStrategy.from("삼쌍승식");
		userBetInfo = UserBetInfo.getUserBetInfo("오번마임요,사번마임요,삼번마임요", betAmount);

		// 게임에서 승리했을 시에, 예상되는 잔고
		int expectedBalance = user.getBalance() - betAmount + (int)(betAmount * 56.1);

		assertThat(bettingStrategy.updateUserByBetStrategy(user, userBetInfo, raceResult))
			.isEqualTo(new User(user.getUserName(), expectedBalance));
	}

}