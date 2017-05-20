package banking.app.util;

public class IncorrectAccountPasswordException extends Exception {

    private static final String MESSAGE = "Incorrect password for account ";

    public IncorrectAccountPasswordException() {
    }

    public IncorrectAccountPasswordException(String message) {
        super(message);
    }

    public IncorrectAccountPasswordException(long id) {
        super(MESSAGE + id);
    }
}
