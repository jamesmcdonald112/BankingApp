package ie.atu.sw.exceptions;

public class InsufficientFundsException extends RuntimeException {

    /**
     * An InsufficientFundsException is constructed with a specified message when an attempt is
     * made to withdraw an amount greater than the available balance.
     *
     * @param message The error message
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
