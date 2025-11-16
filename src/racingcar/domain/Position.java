package racingcar.domain;

public class Position {

    private int value;

    public Position() {
        value = 0;
    }

    public void addPosition() {
        this.value++;
    }

    public boolean isSameAs(int otherPosition) {
        return this.value == otherPosition;
    }

    public String toMarkString(String mark) {
        return mark.repeat(value);
    }

    public int getValue() {
        return value;
    }

}
