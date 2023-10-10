package horseracing.controller;

import java.util.List;

import horseracing.domain.GameStatus;
import horseracing.domain.RaceResult;
import horseracing.domain.User;
import horseracing.domain.BetProcessor;
import horseracing.domain.horse.RaceHorses;
import horseracing.domain.util.RandomNumbersList;
import horseracing.view.InputView;
import horseracing.view.OutputView;

public class HorseRacing {
	private static final String HORSE_LIST = "티즈에너지,원더풀키티,파이팅대디,라온퍼스트,퀵실버키든,"
		+ "참좋은친구,스마트전사,파워에치드,한강에이스,강철드래곤";
	private static final int TOTAL_HORSES = 10;
	private static final int MOVE_PER_GAME = 20;
	private final InputView inputView;
	private final OutputView outputView;
	private GameStatus gameStatus;
	private RaceHorses raceHorses;
	private User user;

	public HorseRacing(InputView inputView, OutputView outputView) {
		this.inputView = inputView;
		this.outputView = outputView;
		this.raceHorses = RaceHorses.getFromNames(HORSE_LIST);
	}

	public void startGame() {
		outputView.printWelcomeMessage();
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
		String name = inputView.getUserName();
		int balance = inputView.getBalance();
		if (balance < 1000) {
			throw new IllegalArgumentException("[ERROR] 잔고는 1000원 이상부터 가능합니다");
		}
		user = new User(name, balance);
	}

	private void play() {
		while (GameStatus.isOn(gameStatus)) {
			// 베팅 정보를 입력받아 BetProcessor를 설정합니다.
			BetProcessor betProcessor = receiveBetInfo();

			// 경주를 시작한 뒤, 경주 결과를 저장합니다.
			RaceResult result = raceAndMakeResult();

			// 경주 결과와, 유저의 베팅 정보를 바탕으로 유저의 잔고를 업데이트합니다.
			user = betProcessor.updateUserWithResult(user, result);

			// 베팅 결과와, 유저의 현재 잔고를 출력합니다.
			outputView.printMessage(betProcessor.getResultMessage(result));
			outputView.printUserInfo(user.getUserName(), user.getBalance());

			// 유저의 잔고가 1000원 이하이면 바로 종료. 아니면 재시작 여부를 묻습니다.
			restartOrExit();
		}
	}

	private BetProcessor receiveBetInfo() {
		String strategy = inputView.getStrategy();

		// 말 리스트와, 베팅 금액을 받아 베팅 정보를 설정합니다.
		final String picks = inputView.getUserChoicesByStrategy(HORSE_LIST, strategy);
		final int betAmount = inputView.getBetAmount();

		if (betAmount > user.getBalance()) {
			throw new IllegalArgumentException("[ERROR] 잔고를 초과하는 금액을 베팅할 수 없습니다.");
		}

		return BetProcessor.of(strategy, picks, betAmount);
	}

	// 테스트를 위해 랜덤 넘버를 받아오는 메서드를 별도로 설정하였습니다.
	protected List<Integer> getNumberForRace() {
		return RandomNumbersList.getRandomNumbers(TOTAL_HORSES);
	}

	private RaceResult raceAndMakeResult() {
		for (int i = 0; i < MOVE_PER_GAME; i++) {
			raceHorses.moveAllHorses(getNumberForRace());
			outputView.printMessage(raceHorses.getPrintResult());
		}
		return RaceResult.from(raceHorses.getRankResult());
	}

	private void restartOrExit() {
		if (user.isBalanceUnderThousand()) {
			outputView.printNotEnoughBalanceMessage();
			outputView.printEndMessage();
			gameOff();
			return;
		}
		decideIfRestart();
	}

	private void decideIfRestart() {
		String restartMessage = inputView.getRestartMessage();

		if (restartMessage.equals("Y")) {
			raceHorses = RaceHorses.getFromNames(HORSE_LIST);
			return;
		}
		if (restartMessage.equals("N")) {
			outputView.printEndMessage();
			gameOff();
			return;
		}

		throw new IllegalArgumentException("[ERROR] Y / N 만 입력 가능합니다.");
	}
}
