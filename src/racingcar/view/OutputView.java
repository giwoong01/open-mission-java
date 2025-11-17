package racingcar.view;

import annotations.Component;

@Component
public class OutputView {

    private static final String OUTPUT_MESSAGE_RACE_START = "실행 결과";
    private static final String OUTPUT_MESSAGE_WINNER_NAMES = "최종 우승자 : %s";

    private OutputView() {
    }

    public void printRaceStartMessage() {
        System.out.println();
        System.out.println(OUTPUT_MESSAGE_RACE_START);
    }

    public void printRoundStatus(String message) {
        System.out.println(message);
        System.out.println();
    }

    public void printWinnerNames(String message) {
        System.out.printf(OUTPUT_MESSAGE_WINNER_NAMES, message);
    }

}
