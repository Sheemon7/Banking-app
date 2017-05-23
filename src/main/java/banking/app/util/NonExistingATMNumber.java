package banking.app.util;

public class NonExistingATMNumber extends Exception {

    private static final String MESSAGE = "Non existing account number! ";

    public NonExistingATMNumber() {
    }

    public NonExistingATMNumber(String message) {
        super(message);
    }

    public NonExistingATMNumber(long id) {
        super(MESSAGE + id);
    }
}
