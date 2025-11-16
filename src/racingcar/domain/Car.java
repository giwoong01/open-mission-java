package racingcar.domain;

public class Car {

    private static final int INITIAL_POSITION = 0;
    private static final int MOVE_THRESHOLD = 4;
    private static final String NAME_POSITION_SEPARATOR = " : ";
    private static final String POSITION_MARK = "-";

    private final Name name;
    private final Position position;

    public Car(Name name) {
        this.name = name;
        this.position = new Position();
    }

    public void move(int power) {
        if (isMove(power)) {
            position.addPosition();
        }
    }

    private boolean isMove(int power) {
        return power >= MOVE_THRESHOLD;
    }

    public boolean isAt(int position) {
        return this.position.isSameAs(position);
    }

    public String getNameValue() {
        return name.name();
    }

    public int getPositionValue() {
        return position.getValue();
    }

    @Override
    public String toString() {
        return name.name() + NAME_POSITION_SEPARATOR + position.toMarkString(POSITION_MARK);
    }

}
