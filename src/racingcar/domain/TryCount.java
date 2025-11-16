package racingcar.domain;

public class TryCount {

    private static final String ERROR_MESSAGE_NOT_A_NUMBER = "시도 횟수는 숫자여야 합니다.";
    private static final String ERROR_MESSAGE_NOT_POSITIVE = "시도 횟수는 1 이상의 양수여야 합니다.";

    private final int count;

    public TryCount(String count) {
        int parsedCount = parseToInt(count);
        validateIsPositive(parsedCount);
        this.count = parsedCount;
    }

    private int parseToInt(String count) {
        try {
            return Integer.parseInt(count);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NOT_A_NUMBER);
        }
    }

    private void validateIsPositive(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NOT_POSITIVE);
        }
    }

    public int getCount() {
        return count;
    }

}
