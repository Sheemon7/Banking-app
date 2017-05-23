package banking.app.util;

public class NotEnoughMoneyException extends Exception {

    private static final String MESSAGE = "Not enough money on the account! ";

    public NotEnoughMoneyException() {
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }

    public NotEnoughMoneyException(long id) {
        super(MESSAGE + id);
    }
}
