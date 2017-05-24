package banking.app.util;

public class CardNotAcceptedException extends Exception {
    private static final String MESSAGE = "Card not accepted!";

    public CardNotAcceptedException() {
    }

    public CardNotAcceptedException(String message) {
        super(message);
    }

    public CardNotAcceptedException(long id) {
        super(MESSAGE + id);
    }
}
