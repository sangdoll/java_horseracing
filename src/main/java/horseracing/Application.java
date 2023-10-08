package horseracing;

import horseracing.controller.HorseRacing;
import horseracing.view.InputView;
import horseracing.view.OutputView;

public class Application {
	public static void main(String[] args) {
		HorseRacing horseRacing = new HorseRacing(new InputView(), new OutputView());
		horseRacing.startGame();
	}
}
