package racingcar.domain.power;

import annotations.Component;
import racingcar.util.Randoms;

@Component
public class RandomPowerGenerator implements PowerGenerator {

    private static final int RANDOM_RANGE_START_INCLUSIVE = 0;
    private static final int RANDOM_RANGE_END_INCLUSIVE = 9;

    @Override
    public int generate() {
        return Randoms.pickNumberInRange(RANDOM_RANGE_START_INCLUSIVE, RANDOM_RANGE_END_INCLUSIVE);
    }

}
