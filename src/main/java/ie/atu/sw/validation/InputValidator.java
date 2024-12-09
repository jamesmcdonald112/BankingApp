package ie.atu.sw.validation;

import ie.atu.sw.exceptions.ExcessLoanRepaymentException;
import ie.atu.sw.exceptions.InsufficientFundsException;

public class InputValidator {

    /**
     * Validates the amount provided is a positive number
     *
     * @param amount the number to validate
     * @param message the error message
     * @throws IllegalArgumentException if the amount is a negative number
     */
    public static void checkPositive(double amount, String message) {
        if (amount <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Validates there is sufficient funds in the account
     *
     * @param amount The amount for the transaction
     * @param balance The balance to be checked
     * @param message The error message
     * @throws InsufficientFundsException if the amount exceeds the balance
     */
    public static void checkSufficientFunds(double amount, double balance, String message) {
        if (amount > balance) {
            throw new InsufficientFundsException(message);
        }
    }

    /**
     * Validates if the amount does not exceed the remaining loan debt.
     *
     * @param amount The amount for the transaction
     * @param loanBalance The balance of the loan remaining
     * @param message The error message
     * @throws ExcessLoanRepaymentException if the amount exceeds the loan balance
     */
    public static void checkSufficientLoanRepayment(double amount, double loanBalance,
                                                    String message) {
        if (amount > loanBalance) {
            throw new ExcessLoanRepaymentException(message);
        }
    }

}
