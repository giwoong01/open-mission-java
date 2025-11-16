package racingcar;

import camp.nextstep.edu.missionutils.Console;
import racingcar.controller.RacingcarController;
import racingcar.domain.power.RandomPowerGenerator;

public class Application {

    public static void main(String[] args) {
        try {
            RacingcarController racingcarController = new RacingcarController(
                    new RandomPowerGenerator()
            );
            racingcarController.run();
        } finally {
            Console.close();
        }
    }

}
