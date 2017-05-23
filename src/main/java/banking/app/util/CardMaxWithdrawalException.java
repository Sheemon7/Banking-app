package banking.app.util;

public class CardMaxWithdrawalException extends Exception {

    private static final String MESSAGE = "Exceeded card max withdrawal!";

    public CardMaxWithdrawalException() {
    }

    public CardMaxWithdrawalException(String message) {
        super(message);
    }

    public CardMaxWithdrawalException(long id) {
        super(MESSAGE + id);
    }
}
