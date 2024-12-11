package store.exception;

public enum ErrorMessage {
    INVALID_FILE_CONTENT("잘못된 파일 형식입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
