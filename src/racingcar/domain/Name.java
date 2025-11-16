package racingcar.domain;

public record Name(
        String name
) {

    private static final int NAME_MAX_LENGTH = 5;
    private static final String ERROR_MESSAGE_NAME_BLANK = "자동차의 이름은 비어있거나 공백일 수 없습니다.";
    private static final String ERROR_MESSAGE_NAME_LENGTH = "자동차의 이름은 5자를 초과할 수 없습니다.";

    public Name {
        validateNameBlank(name);
        validateNameLength(name);
    }

    private void validateNameBlank(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NAME_BLANK);
        }
    }

    private void validateNameLength(String name) {
        if (name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(ERROR_MESSAGE_NAME_LENGTH);
        }
    }

}
