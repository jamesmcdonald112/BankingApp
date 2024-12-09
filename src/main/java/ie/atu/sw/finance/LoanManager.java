package ie.atu.sw.finance;

import ie.atu.sw.account.BankAccount;
import ie.atu.sw.validation.InputValidator;

public class LoanManager {

    // Method to approve a loan for the account
    public void approveLoan(BankAccount account, double amount) {
        InputValidator.checkPositive(amount, "The loan amount must be a positive number");
        account.increaseLoan(amount);
    }

    // Method to repay a part of the loan (only if amount <= loan)
    public boolean repayLoan(BankAccount account, double amount) {
        InputValidator.checkPositive(amount, "The Repayment amount must be a positive number");
        // Repayment exceeds loan
        InputValidator.checkSufficientLoanRepayment(amount, account.getLoan(), "The repayment " +
                "cannot exceed the loan balance");
        account.decreaseLoan(amount);
        return true;
    }
}
