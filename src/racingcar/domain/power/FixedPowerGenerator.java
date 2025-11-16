package racingcar.domain.power;

public class FixedPowerGenerator implements PowerGenerator {

    private final int fixedPower;

    public FixedPowerGenerator(int fixedPower) {
        this.fixedPower = fixedPower;
    }

    @Override
    public int generate() {
        return fixedPower;
    }

}
