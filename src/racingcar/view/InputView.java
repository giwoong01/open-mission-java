package racingcar.view;

import annotations.Component;
import racingcar.util.Console;

@Component
public class InputView {

    private static final String INPUT_MESSAGE_CAR_NAMES = "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)";
    private static final String INPUT_MESSAGE_TRYCOUNT = "시도할 횟수는 몇 회인가요?";

    private InputView() {
    }

    public String inputCarNames() {
        System.out.println(INPUT_MESSAGE_CAR_NAMES);
        return Console.readLine();
    }

    public String inputTryCount() {
        System.out.println(INPUT_MESSAGE_TRYCOUNT);
        return Console.readLine();
    }

}
