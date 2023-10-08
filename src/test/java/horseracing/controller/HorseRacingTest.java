package horseracing.controller;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import horseracing.view.InputView;
import horseracing.view.OutputView;

class HorseRacingTest {
	final InputStream originalInputStream = System.in;
	OutputStream out;

	@BeforeEach
	void setUp() {
		// 출력 메시지를 저장하는 스트림 설정
		out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
	}

	@AfterEach
	void tearDown() {
		System.setIn(originalInputStream);
	}

	/**
	 * 각 베팅 방식에 대한 테스트를 완료하였으므로, 단승식 하나에 대해서만 게임이 정상적으로 진행되는지 확인합니다.
	 * 랜덤 넘버를 제공하는 getNumberForRace를 재정의하여 테스트합니다.
	 */
	@DisplayName("게임이 정상적으로 작동하고, 종료되는지 확인합니다.")
	@Test
	void startGame() {
		// 처음 잔고는 10만원이고, 단승식으로 두 번 50000원씩 파이팅대디에게 베팅하도록 설정
		String input = "상돌이\n100000\n단승식\n파이팅대디\n50000\nY\n단승식\n파이팅대디\n50000\nN";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		// 단승식에 대한 배당률
		final double odd = 6.6;

		final int balanceBeforeGame = 100000;
		final int betAmount = 50000;

		// 입력에 넣을 파이팅대디가 계속 우승하도록 설정합니다.
		HorseRacing horseRacing = new HorseRacing(new InputView(), new OutputView()) {
			@Override
			protected List<Integer> getNumberForRace() {
				return List.of(0, 0, 5, 0, 0, 0, 0, 0, 0, 0);
			}
		};
		horseRacing.startGame();

		// 입력의 파이팅대디가 우승했을 때의 예상 상금입니다.
		int reward = (int)(betAmount * odd);

		// 첫 경기를 우승했을 때의 예상 잔고입니다.
		int balanceAfterFirstGame = (balanceBeforeGame - betAmount) + reward;

		// 두 번째 경기에서 이겼을 때의 예상 잔고입니다.
		int balanceAfterSecondGame = (balanceAfterFirstGame - betAmount) + reward;

		// 입력에 따라 정확하게 메시지를 출력하는지 확인합니다.
		assertThat(out.toString()).contains(
			"경마 게임에 오신것을 환영합니다",
			("승리하였습니다! 획득 상금 : " + reward),
			("상돌이님의 현재 잔고는 " + balanceAfterFirstGame + "원 입니다."),
			"다시 시도하려면 Y, 종료하려면 N를 입력해주세요",
			("승리하였습니다! 획득 상금 : " + reward),
			("상돌이님의 현재 잔고는 " + balanceAfterSecondGame + "원 입니다."),
			"감사합니다. 안녕히 가세요.");
	}

	/**
	 * HorseRacing 클래스에서의 예외 발생에 대해 테스트합니다.
	 */

	@DisplayName("1000원 미만의 잔고를 입력하였을 때, 예외를 발생시키는지 확인합니다.")
	@Test
	void startGame_WithBalanceUnder1000_ThrowsException() {
		// 처음에 잔고를 1000원 미만으로 입력하도록 설정함.
		String input = "상돌이\n999";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		// 게임을 실행하고 예외가 발생하는지 확인
		HorseRacing horseRacing = new HorseRacing(new InputView(), new OutputView());
		assertThatThrownBy(horseRacing::startGame)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}


	@DisplayName("Y / N을 제외한 다른 값을 입력했을 때, 예외를 발생시키는지 확인합니다.")
	@Test
	void startGame_WittNoYorN_ThrowsException() {
		// 재시작 여부를 물을 때, Y / N이 아닌 X를 입력하도록 설정
		String input = "상돌이\n100000\n단승식\n파이팅대디\n50000\nX";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		// 게임을 실행하고 예외가 발생하는지 확인
		HorseRacing horseRacing = new HorseRacing(new InputView(), new OutputView());
		assertThatThrownBy(horseRacing::startGame)
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("[ERROR]");
	}
}