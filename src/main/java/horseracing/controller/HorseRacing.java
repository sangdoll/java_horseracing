package horseracing.controller;

import java.util.List;

import horseracing.domain.GameStatus;
import horseracing.domain.RaceResult;
import horseracing.domain.User;
import horseracing.domain.UserBetInfo;
import horseracing.domain.horse.RaceHorses;
import horseracing.domain.strategy.BettingStrategy;
import horseracing.domain.util.RandomNumbersList;
import horseracing.view.InputView;
import horseracing.view.OutputView;

public class HorseRacing {
	private static final String HORSE_LIST = "티즈에너지,원더풀키티,파이팅대디,라온퍼스트,퀵실버키든,"
		+ "참좋은친구,스마트전사,파워에치드,한강에이스,강철드래곤";
	private static final int TOTAL_HORSES = 10;
	private static final int MOVE_PER_GAME = 20;
	private GameStatus gameStatus;
	private RaceHorses raceHorses;
	private User user;

	public HorseRacing() {
		this.raceHorses = RaceHorses.getFromNames(HORSE_LIST);
	}

	public void startGame() {
		OutputView.printWelcomeMessage();
		receiveUserInfo();
		gameOn();

		play();
	}

	private void gameOn() {
		gameStatus = GameStatus.ON;
	}

	private void gameOff() {
		gameStatus = GameStatus.OFF;
	}

	private void receiveUserInfo() {
		String name = InputView.getUserName();
		int balance = InputView.getBalance();
		user = new User(name, balance);
	}

	private void play() {
		while (GameStatus.isOn(gameStatus)) {
			// 게임 방식을 정합니다.
			String strategy = InputView.getStrategy();
			final BettingStrategy bettingStrategy = BettingStrategy.from(strategy);

			// 말 리스트와, 베팅 금액을 받아 베팅 정보를 설정합니다.
			final String picks = InputView.getUserChoicesByStrategy(HORSE_LIST, strategy);
			final int betAmount = InputView.getBetAmount();
			final UserBetInfo betInfo = UserBetInfo.getUserBetInfo(picks, betAmount);

			// 경주를 시작한 뒤, 경주 결과를 저장합니다.
			RaceResult result = raceAndMakeResult();

			// 경주 결과와, 유저의 베팅 정보를 바탕으로 유저의 잔고를 업데이트한뒤 출력합니다.
			user = bettingStrategy.updateUserByBetStrategy(user, betInfo, result);
			OutputView.printMessage(bettingStrategy.getResultMessage(betInfo, result));
			OutputView.printUserInfo(user.getUserName(), user.getBalance());

			// 유저의 잔고가 1000원 이하이면 바로 종료. 아니면 재시작 여부를 묻습니다.
			restartOrExit();
		}
	}

	protected List<Integer> getNumberForRace() {
		return RandomNumbersList.getRandomNumbers(TOTAL_HORSES);
	}

	private RaceResult raceAndMakeResult() {
		for (int i = 0; i < MOVE_PER_GAME; i++) {
			raceHorses.moveAllHorses(getNumberForRace());
			OutputView.printMessage(raceHorses.getPrintResult());
		}
		return RaceResult.from(raceHorses.getRankResult());
	}

	private void restartOrExit() {
		if (user.getBalance() < 1000) {
			OutputView.printNotEnoughBalanceMessage();
			OutputView.printEndMessage();
			gameOff();
			return;
		}
		decideIfRestart();
	}

	private void decideIfRestart() {
		String restartMessage = InputView.getRestartMessage();
		if (restartMessage.equals("Y")) {
			raceHorses = RaceHorses.getFromNames(HORSE_LIST);
			return;
		}
		if (restartMessage.equals("N")) {
			OutputView.printEndMessage();
			gameOff();
			return;
		}
		OutputView.printMessage("유효하지 않은 입력입니다. 다시 입력해주세요.");
		decideIfRestart();
	}
}
