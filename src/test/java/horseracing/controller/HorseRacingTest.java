package horseracing.controller;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HorseRacingTest {
	InputStream in;
	OutputStream out;

	@BeforeEach
	void setUp() {
		String input = "상돌이\n100000\n단승식\n파이팅대디\n100000\nY\n단승식\n파이팅대디\n100000\nN";
		in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);

		out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
	}

	/**
	 * 각 베팅 방식에 대한 테스트를 완료하였으므로, 단승식 하나에 대해서만 게임이 정상적으로 진행되는지 확인합니다.
	 */
	@DisplayName("게임이 정상적으로 작동하고, 종료되는지 확인합니다.")
	@Test
	void startGame() {
		// 입력에 넣을 파이팅대디가 계속 우승하도록 설정
		HorseRacing horseRacing = new HorseRacing() {
			@Override
			protected List<Integer> getNumberForRace() {
				return List.of(0,0,5,0,0,0,0,0,0,0);
			}
		};

		horseRacing.startGame();
		int reward = (int)(100000 * 6.6);
		int finalBalance = (int)(100000 * 6.6) + (reward - 100000);

		assertThat(out.toString()).contains(
			("승리하였습니다! 획득 상금 : " + reward),
			("상돌이님의 현재 잔고는 " + reward + "원 " + "입니다."),
			("승리하였습니다! 획득 상금 : " + reward),
			("상돌이님의 현재 잔고는 " + finalBalance + "원 " + "입니다."),
			"감사합니다. 안녕히 가세요.");
	}
}