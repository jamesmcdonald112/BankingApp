package ie.atu.sw.exceptions;

public class BankAccountDoesNotExist extends RuntimeException {

    /**
     * An BankAccountDoesNotExist is constructed with a specified message when an attempt is
     * made to find an account that returns null.
     *
     * @param message The error message
     */
    public BankAccountDoesNotExist(String message) {
        super(message);
    }
}
