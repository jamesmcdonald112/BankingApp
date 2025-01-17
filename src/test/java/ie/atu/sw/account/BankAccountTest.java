package ie.atu.sw.account;

import ie.atu.sw.validation.InputValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    // INSTANCE VARIABLES
    private BankAccount account;

    /**
     * Creates a new BankAccount instance for each test.
     */
    @BeforeEach
    public void setupEach() {
        account = new BankAccount("James", 100.00);
    }


    /**
     * Tests to ensure the correct name is returned.
     */
    @Test
    public void testGetAccountHolderName() {
        assertEquals("James", account.getAccountHolder(), "The expected account holder name is " +
                "James, but the actual account holder name is " + account.getAccountHolder());
    }

    /**
     * Tests the account holder name is set correctly
     */
    @Test
    public void testSetAccountHolderName() {
        account.setAccountHolder("Alice");
        assertEquals("Alice", account.getAccountHolder(),
                "The expected account holder name is Alice, but the actual account holder name " +
                        "is" + account.getAccountHolder());
    }

    /**
     * Tests that the that different input names are handled correctly
     *
     * @param name the account holder name
     */
    @ParameterizedTest
    @CsvSource({
            "James",
            "Alice",
            "Bob"
    })
    public void testAccountHolderNameSetAndGetParameterizedTest(String name) {
        account.setAccountHolder(name);
        assertEquals(name, account.getAccountHolder(),
                "The expected account holder name is " + name + ", but the actual name is " + account.getAccountHolder());
    }

    /**
     * Tests the account balance is returned correctly.
     */
    @Test
    public void testGetAccountBalance() {
        assertEquals(100.00, account.getBalance(), "The expected balance is 100.00, but the " +
                "actual balance is " + account.getBalance());
    }

    /**
     * Tests that the account balance is increased correctly.
     *
     * @param amountToAdd     The amount to increase the balance by
     * @param expectedBalance The expected balance after increase.
     */
    @ParameterizedTest
    @CsvSource({
            "50.00, 150.00",
            "1000.00, 1100.00",
            "40_000.00, 40_100.00"
    })
    public void testIncreaseBalance(double amountToAdd, double expectedBalance) {
        // Get starting balance
        double startingBalance = account.getBalance();

        // Increase the balance
        account.increaseBalance(amountToAdd);

        // Check the final balance
        assertEquals(expectedBalance, account.getBalance(),
                "The account balance should be " + expectedBalance + " after increasing the " +
                        "starting balance of " + startingBalance +
                        " it by " + amountToAdd);
    }

    /**
     * Tests that the account balance is decreased correctly.
     *
     * @param amountToSubtract The amount to decrease the balance by
     * @param expectedBalance  The expected balance after increase.
     */
    @ParameterizedTest
    @CsvSource({
            "50.00, 50.00",
            "100.00, 0.00",
            "1_000.00, -900.00"
    })
    public void testDecreaseBalance(double amountToSubtract,
                                    double expectedBalance) {
        // Get the starting balance
        double startingBalance = account.getBalance();

        // Increase the balance
        account.decreaseBalance(amountToSubtract);

        // Check the final balance
        assertEquals(expectedBalance, account.getBalance(),
                "The account balance should be " + expectedBalance + " after decreasing the " +
                        "starting balance of " + startingBalance +
                        " it by " + amountToSubtract);
    }

    /**
     * Tests that the loan amount is returned correctly
     */
    @Test
    public void testGetLoan() {
        assertEquals(0, account.getLoan());
    }

    /**
     * Tests that the loan is increased correctly.
     */
    @ParameterizedTest
    @CsvSource({
            "100.00, 100.00",
            "10_000, 10_000"
    })
    public void testIncreaseLoan(double amountToIncrease,
                                 double expectedLoan) {
        // Get the starting amount
        double startingAmount = account.getBalance();

        // Increase the loan
        account.increaseLoan(amountToIncrease);

        // Check the loan amount
        assertEquals(expectedLoan, account.getLoan(), "The loan should be " + expectedLoan + " " +
                "after increasing the starting loan of " + startingAmount + "  by " + amountToIncrease);
    }


    /**
     * Tests that the loan is decreased correctly.
     */
    @ParameterizedTest
    @CsvSource({
            "100.00, 50.00, 50.00",
            "10_000, 1_000, 9_000"
    })
    public void testDecreaseLoan(double amountToIncrease, double amountToDecrease, double expectedLoan) {

        // Increase the loan and return the balance as an example of a loan in progress
        account.increaseLoan(amountToIncrease);
        double startingBalance = account.getBalance();

        // Decrease the loan
        account.decreaseLoan(amountToDecrease);

        // Check the loan amount
        assertEquals(expectedLoan, account.getLoan(), "The loan should be " + expectedLoan + " " +
                "after decreasing the starting loan of " + startingBalance + "  by " + amountToDecrease);
    }


}