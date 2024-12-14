package ie.atu.sw.transaction;

import ie.atu.sw.account.BankAccount;
import ie.atu.sw.exceptions.InsufficientFundsException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class AccountTransactionTest {
    BankAccount account;
    AccountTransaction accountTransaction;

    /**
     * Creates a new account transaction instance for each test
     */
    @BeforeEach
    public void setupEach() {
        accountTransaction = new AccountTransaction();
    }


    /**
     * Tests that deposits are added to the account correctly.
     *
     * @param startingBalance The starting balance of the account
     * @param depositAmount   The deposit to be added to the account
     * @param expectedBalance The expected balance after adding the deposit
     */
    @ParameterizedTest
    @CsvSource({
            "100.00, 50.00, 150.00",
            "1_000.00, 1_000, 2_000",
            "300_000.00, 10_000, 310_000"
    })
    public void testDeposit(double startingBalance, double depositAmount, double expectedBalance) {
        // Initialise a bank account with startingBalance
        account = new BankAccount("James", startingBalance);

        // Deposit amount
        accountTransaction.deposit(this.account, depositAmount);

        // Check the final balance
        assertEquals(expectedBalance, account.getBalance(),
                "Starting balance: " + startingBalance + " + deposit of: " + depositAmount + " " +
                        "should equal: " + expectedBalance + ". The actual balance is: " + account.getBalance());
    }


    /**
     * Test to ensure negative amounts cannot be used to deposit amounts to the account balance. An
     * IllegalArgumentException is thrown if the amount is negative
     */
    @Test
    public void testDepositWithNegativeNumberThrowsException() {
        account = new BankAccount("James", 100.00);
        assertThrows(IllegalArgumentException.class,
                () -> accountTransaction.deposit(this.account, -100), "Depositing a negative " +
                        "amount should throw IllegalArgumentException");
    }

    /**
     * Tests that withdrawals from the account is handled correctly
     *
     * @param startingBalance  The starting balance of the account
     * @param withdrawalAmount The withdrawal to be added to the account
     * @param expectedBalance  The expected balance after adding the deposit
     */
    @ParameterizedTest
    @CsvSource({
            "100.00, 50.00, 50.00",
            "10_000.00, 1_000, 9_000",
            "100_000.00, 40_000, 60_000"
    })
    public void testWithdraw(double startingBalance, double withdrawalAmount,
                             double expectedBalance) {
        // Initialise a bank account with startingBalance
        account = new BankAccount("James", startingBalance);

        // Account withdrawal
        accountTransaction.withdraw(this.account, withdrawalAmount);

        // Check the final balance
        assertEquals(expectedBalance, account.getBalance(),
                "Starting balance: " + startingBalance + " + withdrawal of: " + withdrawalAmount +
                        "should equal: " + expectedBalance + ". The actual balance is: " + account.getBalance());
    }


    /**
     * Test to ensure negative amounts cannot be used to withdraw amounts from the account
     * balance. An IllegalArgumentException is thrown if the amount is negative
     */
    @Test
    public void testWithdrawWithNegativeNumberThrowsException() {
        // Initialise a bank account with startingBalance
        account = new BankAccount("James", 100);

        assertThrows(IllegalArgumentException.class,
                () -> accountTransaction.withdraw(this.account, -100), "Depositing a negative " +
                        "amount should throw IllegalArgumentException");
    }

    /**
     * Test to ensure amounts greater than the account balance cannot be withdrawn from the
     * account. An InsufficientFundsException is thrown if the amount is negative
     */
    @Test
    public void testWithdrawalWithExceedingAmountThrowsInsufficientFundsException() {
        // Initialise a bank account with startingBalance
        account = new BankAccount("James", 100);

        assertThrows(InsufficientFundsException.class,
                () -> accountTransaction.withdraw(this.account, 10_000), "Withdrawing an amount " +
                        "greater than the balance available should throw " +
                        "InsufficientFundsException");
    }


}