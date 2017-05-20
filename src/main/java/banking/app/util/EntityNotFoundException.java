package banking.app.util;

public class EntityNotFoundException extends Exception {
    private static final String MESSAGE = "Entity not found in database! Id:  ";

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(long id) {
        super(MESSAGE + id);
    }
}
