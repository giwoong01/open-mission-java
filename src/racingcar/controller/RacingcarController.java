package racingcar.controller;

import annotations.Autowired;
import annotations.Component;
import racingcar.domain.Cars;
import racingcar.domain.TryCount;
import racingcar.domain.power.PowerGenerator;
import racingcar.view.InputView;
import racingcar.view.OutputView;

@Component
public class RacingcarController {

    @Autowired
    private PowerGenerator powerGenerator;

    @Autowired
    private InputView inputView;

    @Autowired
    private OutputView outputView;

    public RacingcarController() {
    }

    public void run() {
        Cars cars = getCars();
        TryCount tryCount = getTryCount();

        runRace(cars, tryCount);
        displayWinners(cars);
    }

    private Cars getCars() {
        String carNames = inputView.inputCarNames();
        return new Cars(carNames);
    }

    private TryCount getTryCount() {
        String tryCount = inputView.inputTryCount();
        return new TryCount(tryCount);
    }

    private void runRace(Cars cars, TryCount tryCount) {
        outputView.printRaceStartMessage();

        for (int i = 0; i < tryCount.getCount(); i++) {
            cars.moveAll(powerGenerator);
            outputView.printRoundStatus(cars.toString());
        }
    }

    private void displayWinners(Cars cars) {
        outputView.printWinnerNames(cars.winnerNames());
    }

}
