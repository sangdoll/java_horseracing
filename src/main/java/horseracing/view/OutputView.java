package horseracing.view;

public class OutputView {
	private static final String DASH = "-";
	private static final String BAR = "|";
	private static final String SPACE = " ";
	private static final String WELCOME_MESSAGE = "경마 게임에 오신것을 환영합니다";

	public static void printMessage(String message) {
		System.out.println(message);
	}

	public static void printWelcomeMessage() {
		System.out.println(DASH.repeat(27));
		System.out.println(BAR + SPACE.repeat(25) + BAR);
		System.out.print(BAR + SPACE);
		System.out.print(WELCOME_MESSAGE);
		System.out.println(SPACE + BAR);
		System.out.println(BAR + SPACE.repeat(25) + BAR);
		System.out.println(DASH.repeat(27));
	}

	public static void printUserInfo(String userName, int balance) {
		System.out.printf("%s님의 현재 잔고는 %d원 입니다.\n", userName, balance);
	}

	public static void printEndMessage() {
		System.out.println("감사합니다. 안녕히 가세요.");
	}

	public static void printNotEnoughBalanceMessage() {
		System.out.println("잔고가 부족하여 재시작할 수 없습니다. 게임을 종료합니다.");
	}
}
