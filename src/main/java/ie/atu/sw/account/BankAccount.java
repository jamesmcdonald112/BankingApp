package ie.atu.sw.account;

import ie.atu.sw.validation.InputValidator;

public class BankAccount {
    private String accountHolder; // Name of the account holder
    private double balance;       // Current account balance
    private double loan;          // Outstanding loan amount

    // Constructor to create a new account
    public BankAccount(String accountHolder, double balance) {
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.loan = 0;
    }

    // GETTERS & SETTERS

    // Getter for the account holder's name
    public String getAccountHolder() {
        return accountHolder;
    }

    // Setter for the account holder's name
    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    // Getter for the account balance
    public double getBalance() {
        return balance;
    }

    // Increase the balance by the amount
    public void increaseBalance(double amount) {
        this.balance += amount;
    }

    // Decrease the balance by the amount
    public void decreaseBalance(double amount) {
        this.balance -= amount;
    }

    // Getter for the loan amount
    public double getLoan() {
        return loan;
    }


    // Increase the loan by the amount
    public void increaseLoan(double amount) {
        this.loan += amount;
    }

    // Decrease the loan by the amount
    public void decreaseLoan(double amount) {
        this.loan -= amount;
    }
}
