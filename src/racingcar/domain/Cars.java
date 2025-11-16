package racingcar.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import racingcar.domain.power.PowerGenerator;

public class Cars {

    private static final int DEFAULT_POSITION = 0;
    private static final String LINE_SEPARATOR = "\n";
    private static final String INPUT_DELIMITER = ",";
    private static final String WINNER_NAME_DELIMITER = ", ";
    private static final String ERROR_MESSAGE_DUPLICATE_NAME = "자동차의 이름은 중복될 수 없습니다.";

    private final List<Car> cars;

    public Cars(String carNames) {
        List<Name> names = parseCarNames(carNames);
        validateDuplicateNames(names);

        this.cars = names.stream()
                .map(Car::new)
                .toList();
    }

    private List<Name> parseCarNames(String carNames) {
        return Arrays.stream(carNames.split(INPUT_DELIMITER))
                .map(String::trim)
                .map(Name::new)
                .toList();
    }

    private void validateDuplicateNames(List<Name> names) {
        if (names.size() != Set.copyOf(names).size()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_DUPLICATE_NAME);
        }
    }

    public void moveAll(PowerGenerator powerGenerator) {
        for (Car car : cars) {
            car.move(powerGenerator.generate());
        }
    }

    public String winnerNames() {
        int maxPosition = findMaxPosition();

        return cars.stream()
                .filter(car -> car.isAt(maxPosition))
                .map(Car::getNameValue)
                .collect(Collectors.joining(WINNER_NAME_DELIMITER));
    }

    private int findMaxPosition() {
        return cars.stream()
                .mapToInt(Car::getPositionValue)
                .max()
                .orElse(DEFAULT_POSITION);
    }

    @Override
    public String toString() {
        return cars.stream()
                .map(Car::toString)
                .collect(Collectors.joining(LINE_SEPARATOR));
    }

}
