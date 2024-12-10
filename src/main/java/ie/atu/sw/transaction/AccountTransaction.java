package ie.atu.sw.transaction;

import ie.atu.sw.account.BankAccount;
import ie.atu.sw.validation.InputValidator;

public class AccountTransaction {


    // Method to deposit money into the account
    public void deposit(BankAccount account, double amount) {
        // Checks the amount entered is a positive number
        InputValidator.checkPositive(amount, "The deposit must be positive");

        // Increases the balance by the amount.
        account.increaseBalance(amount);
        ;
    }

    // Method to withdraw money from the account (only if balance is sufficient)
    public boolean withdraw(BankAccount account, double amount) {
        // Checks the amount entered is a positive number.
        InputValidator.checkPositive(amount, "The withdrawal amount must be a positive number");

        // Checks if there is enough funds for the withdrawal
        InputValidator.checkSufficientFunds(amount, account.getBalance(), "Insufficient funds: " +
                "withdrawal amount: " + amount + ", Account Balance: " + account.getBalance());

        // Decrease the balance by the withdrawal amount
        account.decreaseBalance(amount);
        return true;
    }


}
