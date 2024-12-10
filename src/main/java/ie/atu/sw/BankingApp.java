package ie.atu.sw;

import ie.atu.sw.manager.AccountManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This program simulates a simple banking application. It allows:
 * - Adding new accounts with an initial deposit.
 * - Depositing and withdrawing money from accounts.
 * - Approving and repaying loans for account holders.
 * - Tracking the total deposits available in the bank.
 *
 * The program uses a list of Account objects to manage account data.
 */
public class BankingApp {

    public static void main(String[] args) {
        // Create a new banking application instance
        AccountManager accountManager = new AccountManager();

        // Add accounts
        accountManager.addAccount("Alice", 1000);
        accountManager.addAccount("Bob", 500);

        // Test deposits
        System.out.println("Depositing 200 to Alice: " + accountManager.deposit("Alice", 200)); // Should return true
        System.out.println("Alice's balance: " + accountManager.getBalance("Alice")); // Should be 1200

        // Test withdrawals
        System.out.println("Withdrawing 300 from Bob: " + accountManager.withdraw("Bob", 300)); // Should return true
        System.out.println("Bob's balance: " + accountManager.getBalance("Bob")); // Should be 200

        // Test loan approval
        System.out.println("Approving a loan of 400 for Alice: " + accountManager.approveLoan("Alice", 400)); // Should return true
        System.out.println("Alice's loan: " + accountManager.getLoan("Alice")); // Should be 400

        // Test loan repayment
        System.out.println("Repaying 200 of Alice's loan: " + accountManager.repayLoan("Alice", 200)); // Should return true
        System.out.println("Alice's remaining loan: " + accountManager.getLoan("Alice")); // Should be 200

        // Check total deposits in the bank
        System.out.println("Total deposits in the bank: " + accountManager.getTotalDeposits());
    }
}

