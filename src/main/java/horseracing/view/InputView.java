package horseracing.view;

import java.util.Scanner;

public class InputView {
	private static final Scanner SCANNER = new Scanner(System.in);
	private static final String BAR = " | ";

	public static String getUserName() {
		System.out.print("이름을 입력하세요 : ");
		return SCANNER.nextLine();
	}

	public static int getBalance() {
		System.out.print("현재 잔고를 입력하세요 : ");
		return Integer.parseInt(SCANNER.nextLine());
	}

	public static String getStrategy() {
		System.out.println("단승식, 연승식, 복승식, 쌍승식, 복연승식, 삼복승식, 삼쌍승식이 있습니다.");
		System.out.print("게임 방식을 입력해주세요 : ");
		return SCANNER.nextLine();
	}

	public static int getBetAmount() {
		System.out.print("베팅할 금액을 입력하세요 : ");
		return Integer.parseInt(SCANNER.nextLine());
	}

	public static String getUserChoicesByStrategy(String horseList, String strategy) {
		String reformatted = horseList.replaceAll(",", BAR);
		System.out.println("오늘의 경주마 목록입니다.");
		System.out.println(reformatted);
		printMessageByStrategy(strategy);
		return SCANNER.nextLine();
	}

	public static void printMessageByStrategy(String strategy) {
		final String choiceOneHorse = "단승식 / 연승식";
		final String choiceTwoHorses = "복승식 / 쌍승식 / 복연승식";
		final String choiceThreeHorses = "삼복승식 / 삼쌍승식";

		if (choiceOneHorse.contains(strategy)) {
			System.out.println("한 마리의 말을 선택해주세요. 입력 예시 : 원더풀키티");
			return;
		}
		if (choiceTwoHorses.contains(strategy)) {
			System.out.println("두 마리의 말을 선택해주세요. 입력 예시 : 원더풀키티,파이팅대디");
			return;
		}
		if (choiceThreeHorses.contains(strategy)) {
			System.out.println("세 마리의 말을 선택해주세요. 입력 예시 : 티즈에너지,파이팅대디,한강드래곤");
		}
	}

	public static String getRestartMessage() {
		System.out.println("다시 시도하려면 Y, 종료하려면 N를 입력해주세요");
		return SCANNER.nextLine();
	}
}
