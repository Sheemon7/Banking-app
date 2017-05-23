package banking.app.util;

public class NonExistingAccountNumber extends Exception {

    private static final String MESSAGE = "Non existing account number! ";

    public NonExistingAccountNumber() {
    }

    public NonExistingAccountNumber(String message) {
        super(message);
    }

    public NonExistingAccountNumber(long id) {
        super(MESSAGE + id);
    }
}
