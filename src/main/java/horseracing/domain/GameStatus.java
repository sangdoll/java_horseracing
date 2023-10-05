package horseracing.domain;

public enum GameStatus {
	ON,
	OFF;

	public static boolean isOn(GameStatus gameStatus) {
		return gameStatus.equals(ON);
	}
}
