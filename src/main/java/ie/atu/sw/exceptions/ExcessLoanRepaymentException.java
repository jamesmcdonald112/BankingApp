package ie.atu.sw.exceptions;

public class ExcessLoanRepaymentException extends RuntimeException {

    /**
     * An ExcessLoanRepaymentException is constructed with a specified message when an attempt is
     * made to repay an amount greater than the loan balance.
     *
     * @param message The error message
     */
    public ExcessLoanRepaymentException(String message) {
        super(message);
    }
}
