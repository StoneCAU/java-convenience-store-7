package store.exception;

public class StoreException extends IllegalArgumentException{
    private static final String PREFIX = "[ERROR] ";

    public StoreException(ErrorMessage errorMessage) {
        super(PREFIX + errorMessage.getMessage());
    }
}
