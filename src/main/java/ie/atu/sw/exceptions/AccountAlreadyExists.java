package ie.atu.sw.exceptions;

public class AccountAlreadyExists extends RuntimeException {

    /**
     * An AccountAlreadyExists is constructed with a specified message when an attempt is
     * made to create an account that already exists.
     *
     * @param message The error message
     */
    public AccountAlreadyExists(String message) {
        super(message);
    }
}
