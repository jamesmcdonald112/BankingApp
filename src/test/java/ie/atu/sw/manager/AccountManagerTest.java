package ie.atu.sw.manager;

import ie.atu.sw.exceptions.AccountAlreadyExists;
import ie.atu.sw.exceptions.BankAccountDoesNotExist;
import ie.atu.sw.exceptions.InsufficientFundsException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagerTest {
    private static AccountManager accountManager;
    private static String testName;
    private static double initialDeposit;

    /**
     * Creates an static instance of AccountManager for the test class
     */
    @BeforeAll
    public static void beforeAllSetup() {
        accountManager = new AccountManager();
        testName = "James";
        initialDeposit = 1_000;
    }


    /**
     * Adds a new instance of an account before each test.
     */
    @BeforeEach
    public void setUpBeforeEach() {
        accountManager.addAccount(testName, initialDeposit);
    }

    /**
     * Resets the accountManager for the following tests.
     */
    @AfterEach
    public void tearDownAfterEach() {
        accountManager = new AccountManager();
    }

    /**
     * Test ensures the correct amount is returned.
     */
    @Test
    public void testGetTotalDeposits() {
        accountManager.addAccount("John", 1_000);
        // Includes account added
        assertEquals(2_000, accountManager.getTotalDeposits(),
                "testName account adds " + initialDeposit + ", John adds " + 1_000 + ", the " +
                        " expected deposits should be 2_000, deposits: " + accountManager.getTotalDeposits());
    }

    /**
     * Test to ensure the correct balance is returned from an account
     */
    @Test
    public void testGetBalance() {
        assertEquals(initialDeposit, accountManager.getBalance(testName),
                "Expected balance " + initialDeposit + ", actual balance: " + accountManager.getBalance(testName));

    }

    /**
     * Test ensures that accounts that searching for accounts that do not exist will throw a
     * BankAccountDoesNotExist exception.
     */
    @Test
    public void testFindAccountThrowsExceptionForNonexistentAccount() {
        assertThrows(BankAccountDoesNotExist.class, () -> {
            accountManager.getBalance("NonExistentAccount");
        });
    }

    /**
     * Test ensures the correct loan amount is returned for a specified account holder.
     */
    @Test
    public void testGetLoan() {
        // Create a loan
        accountManager.approveLoan(testName, 1_000);
        assertEquals(1_000, accountManager.getLoan(testName));
    }

    /**
     * Test to ensure that accounts are added correctly by fetching their balance in the account
     * and making sure it matches the initial deposit.
     *
     * @param accountHolder   The name of the account holder.
     * @param initialDeposit  The amount for the initial deposit.
     * @param expectedBalance The expected balance in the account.
     */
    @ParameterizedTest
    @CsvSource({
            "Conor, 200_000, 200_000",
            "Mary, 10, 10",
            "Deirdre, 1_000_000, 1_000_000"
    })
    public void testAddAccount(String accountHolder, double initialDeposit,
                               double expectedBalance) {
        accountManager.addAccount(accountHolder, initialDeposit);
        assertEquals(expectedBalance, accountManager.getBalance(accountHolder));
    }

    /**
     * Test to ensure that duplicates of accounts cannot be added. An exception is thrown when a
     * duplicate account is made.
     */
    @Test
    public void testAddDuplicateAccountThrowsException() {
        assertThrows(AccountAlreadyExists.class, () -> {
            accountManager.addAccount(testName, 1_000);
        });
    }


    /**
     * Test to ensure that deposits are added correctly by increasing the bank balance by a
     * chosen amount and checking the final balance.
     *
     * @param depositAmount   The amount to be deposited
     * @param expectedBalance The final balance expected
     */
    @ParameterizedTest
    @CsvSource({
            "2_000, 3_000",
            "10, 1_010",
            "1_000_000, 1_001_000"
    })
    public void testDeposit(double depositAmount, double expectedBalance) {
        accountManager.deposit(testName, depositAmount);
        assertEquals(expectedBalance, accountManager.getBalance(testName));
    }

    /**
     * Test ensures that only positive amounts can be deposited or an IllegalArgumentException is
     * thrown
     */
    @Test
    public void testDepositNegativeAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountManager.deposit(testName, -100);
        });
    }


    /**
     * Test to ensure that amounts are withdrawn correctly by decreasing the bank balance by a
     * chosen amount and checking the final balance.
     */
    @Test
    public void testWithdraw() {
        accountManager.withdraw(testName, 500);
        assertEquals(500, accountManager.getBalance(testName));
    }

    /**
     * Test ensures that only positive amounts can be withdrawn or an IllegalArgumentException is
     * thrown
     */
    @Test
    public void testWithdrawNegativeAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountManager.withdraw(testName, -500);
        });
    }

    /**
     * Test to ensure that withdrawals cannot exceed the amount in the account. An
     * InsufficientFundsException is thrown if it does.
     */
    @Test
    public void testWithdrawExceedsBalanceThrowsException() {
        assertThrows(InsufficientFundsException.class, () -> {
            accountManager.withdraw(testName, initialDeposit + 1_000);
        });
    }

    /**
     * Test ot ensure that loan amounts are correctly added to accounts. The loan amount is added
     * and the account balance is checked.
     */
    @Test
    public void testApproveLoan() {
        accountManager.approveLoan(testName, 500);
        assertEquals(500, accountManager.getLoan(testName));
    }

    /**
     * Test ensures that only positive amounts can be approved or an IllegalArgumentException is
     * thrown
     */
    @Test
    public void testApproveLoanWithNegativeAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            accountManager.approveLoan(testName, -200);
        });
    }

    /**
     * Test ensures that InsufficientFundsException will be thrown for loan amounts greater than
     * total funds
     * available.
     */
    @Test
    public void testApproveLoanExceedsAvailableFundsThrowsException() {
        // Total deposits = 1_000 due to default account
        assertThrows(InsufficientFundsException.class, () -> {
            accountManager.approveLoan(testName, 1_000_000);
        });
    }


    /**
     * Test ensures that loan repayments are correctly taken from the loan balance. The loan
     * payment is subtracted from the loan balance and the loan balance is checked.
     */
    @Test
    public void testRepayLoan() {
        // Create a loan first
        accountManager.approveLoan(testName, 500);

        // Make a payment
        accountManager.repayLoan(testName, 200);
        assertEquals(300, accountManager.getLoan(testName));

    }

    /**
     * Test ensures that the deposit completes within the sect timeframe.
     */
    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    public void testDepositCompletesWithinTimeout() {
        boolean depositSuccessful = accountManager.deposit(testName, 1_000);
        assertEquals(true, depositSuccessful, "The deposit operation should" +
                "complete within the timeout");
    }


    @AfterAll
    public static void afterAllSetup() {
        System.out.println("AccountManager tests complete");
        accountManager = null;
    }
}