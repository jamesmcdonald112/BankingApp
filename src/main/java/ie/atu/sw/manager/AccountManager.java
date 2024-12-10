package ie.atu.sw.manager;

import ie.atu.sw.account.BankAccount;
import ie.atu.sw.exceptions.AccountAlreadyExists;
import ie.atu.sw.exceptions.BankAccountDoesNotExist;
import ie.atu.sw.exceptions.InsufficientFundsException;
import ie.atu.sw.transaction.AccountTransaction;
import ie.atu.sw.validation.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {
    // List to store all accounts in the banking application
    private List<BankAccount> accounts;
    private AccountTransaction accountTransaction;
    private LoanManager loanManager;
    private double totalDeposits; // Tracks total deposits in the bank

    // Constructor to initialize the banking application
    public AccountManager() {
        accounts = new ArrayList<>();
        this.accountTransaction = new AccountTransaction();
        this.loanManager = new LoanManager();
        totalDeposits = 0;
    }

    // GETTERS AND SETTERS
    /**
     * Gets the total deposits available in the bank.
     * @return The total deposits.
     */
    public double getTotalDeposits() {
        return this.totalDeposits;
    }

    /**
     * Gets the balance of a specific account holder.
     * @param accountHolder The name of the account holder.
     * @return The balance if the account exists, otherwise null.
     */
    public Double getBalance(String accountHolder) {
        BankAccount account = findAccount(accountHolder);
        return account.getBalance();
    }

    /**
     * Gets the loan amount of a specific account holder.
     * @param accountHolder The name of the account holder.
     * @return The loan amount if the account exists, otherwise null.
     */
    public Double getLoan(String accountHolder) {
        BankAccount account = findAccount(accountHolder);
        return account.getLoan();
    }

    // METHODS

    /**
     * Helper method to find an account by account holder's name.
     * @param accountHolder The name of the account holder.
     * @return The Account object if found, otherwise null.
     */
     private BankAccount findAccount(String accountHolder) {
        for (BankAccount account : accounts) {
            if (account.getAccountHolder().equals(accountHolder)) {
                return account;
            }
        }
       throw new BankAccountDoesNotExist("Account not found for " + accountHolder);
    }

    /**
     * Adds a new account with an initial deposit.
     * @param accountHolder The name of the new account holder.
     * @param initialDeposit The initial deposit amount.
     */
    public void addAccount(String accountHolder, double initialDeposit) {
        // Check if an account already exists
        for (BankAccount account : accounts) {
            if (account.getAccountHolder().equals(accountHolder)) {
                throw new AccountAlreadyExists("Account already exists for " + accountHolder);
            }
        }
        InputValidator.checkPositive(initialDeposit, "Initial deposit must be positive.");
        accounts.add(new BankAccount(accountHolder, initialDeposit));
        totalDeposits += initialDeposit;
    }

    /**
     * Deposits money into an account.
     * @param accountHolder The name of the account holder.
     * @param amount The deposit amount.
     * @return True if the deposit is successful, otherwise false.
     */
    public boolean deposit(String accountHolder, double amount) {
        BankAccount account = findAccount(accountHolder);
        accountTransaction.deposit(account, amount);
        totalDeposits += amount;
        return true;
    }

    /**
     * Withdraws money from an account.
     * @param accountHolder The name of the account holder.
     * @param amount The withdrawal amount.
     * @return True if the withdrawal is successful, otherwise false.
     */
    public boolean withdraw(String accountHolder, double amount) {
        BankAccount account = findAccount(accountHolder);
        accountTransaction.withdraw(account, amount);
        totalDeposits -= amount;
        return true;
    }

    /**
     * Approves a loan for an account holder.
     * @param accountHolder The name of the account holder.
     * @param loanAmount The loan amount.
     * @return True if the loan is approved, otherwise false.
     */
    public boolean approveLoan(String accountHolder, double loanAmount) {
        BankAccount account = findAccount(accountHolder);
        if (loanAmount > this.totalDeposits) {
            throw new InsufficientFundsException("Loan amount: " + loanAmount + " exceeds total" +
                    " deposits available: " + this.totalDeposits);
        }
        loanManager.approveLoan(account, loanAmount);
        totalDeposits -= loanAmount;
        return true;
    }

    /**
     * Repays a part of the loan for an account holder.
     * @param accountHolder The bank account of the account holder.
     * @param amount The repayment amount.
     * @return True if the repayment is successful, otherwise false.
     */
    public boolean repayLoan(String accountHolder, double amount) {
        BankAccount account = findAccount(accountHolder);
        InputValidator.checkPositive(amount, "The Repayment amount must be a positive number");
        loanManager.repayLoan(account, amount);
        totalDeposits += amount;
        return true;
    }

}
