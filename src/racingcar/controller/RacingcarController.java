package racingcar.controller;

import racingcar.domain.Cars;
import racingcar.domain.TryCount;
import racingcar.domain.power.PowerGenerator;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class RacingcarController {

    private final PowerGenerator powerGenerator;

    public RacingcarController(PowerGenerator powerGenerator) {
        this.powerGenerator = powerGenerator;
    }

    public void run() {
        Cars cars = getCars();
        TryCount tryCount = getTryCount();

        runRace(cars, tryCount);
        displayWinners(cars);
    }

    private Cars getCars() {
        String carNames = InputView.inputCarNames();
        return new Cars(carNames);
    }

    private TryCount getTryCount() {
        String tryCount = InputView.inputTryCount();
        return new TryCount(tryCount);
    }

    private void runRace(Cars cars, TryCount tryCount) {
        OutputView.printRaceStartMessage();

        for (int i = 0; i < tryCount.getCount(); i++) {
            cars.moveAll(powerGenerator);
            OutputView.printRoundStatus(cars.toString());
        }
    }

    private void displayWinners(Cars cars) {
        OutputView.printWinnerNames(cars.winnerNames());
    }

}
