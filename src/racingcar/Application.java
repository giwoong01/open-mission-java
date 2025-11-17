package racingcar;

import context.AppContext;
import racingcar.controller.RacingcarController;
import racingcar.util.Console;

public class Application {

    public static void main(String[] args) {
        try {
            String basePackage = "racingcar";

            AppContext appContext = new AppContext(basePackage);

            RacingcarController racingcarController = appContext.getBean(RacingcarController.class);
            racingcarController.run();
        } finally {
            Console.close();
        }
    }

}
