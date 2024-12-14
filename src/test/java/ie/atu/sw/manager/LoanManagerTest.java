package ie.atu.sw.manager;

import ie.atu.sw.account.BankAccount;
import ie.atu.sw.exceptions.ExcessLoanRepaymentException;
import ie.atu.sw.manager.LoanManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class LoanManagerTest {
    BankAccount account;
    LoanManager loanManager;

    /**
     * Create a new loanManager instance for each test
     */
    @BeforeEach
    public void setupEach() {
        loanManager = new LoanManager();
        account = new BankAccount("James", 100.00);
    }

    /**
     * Tests the loan approval correctly increases the loan balance.
     *
     * @param startingLoan        The loan amount to start with
     * @param additionalLoan      The loan amount to increase by
     * @param expectedLoanBalance The expected loan balance at the end
     */
    @ParameterizedTest
    @CsvSource({
            "100, 50, 150",
            "10_000, 2_000, 12_000",
            "1_000_000, 2_000_000, 3_000_000"
    })
    public void testApproveLoan(double startingLoan, double additionalLoan,
                                double expectedLoanBalance) {
        // Take out an initial loan
        loanManager.approveLoan(this.account, startingLoan);

        // Increase the loan
        loanManager.approveLoan(this.account, additionalLoan);

        // Check expected result
        assertEquals(expectedLoanBalance, account.getLoan(), "Expected loan balance after taking " +
                "an initial loan of " + startingLoan + " and an additional loan of " +
                additionalLoan + " should be " + expectedLoanBalance + ", but was " + account.getLoan());
    }

    /**
     * Test to ensure negative amounts cannot be used to increase the loan amount. An
     * IllegalArgumentException is thrown if the amount is negative
     */
    @Test
    public void testApproveLoanWithNegativeNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> loanManager.approveLoan(this.account,
                        -100),
                "Increasing the loan by a negative number should throw IllegalArgumentException");
    }

    @ParameterizedTest
    @CsvSource({
            "1_000, 500, 500",
            "500_000, 100_000, 400_000",
            "1_000_000, 900_000, 100_000"
    })
    public void testRepayLoan(double startingLoan, double loanRepayment,
                              double expectedLoanBalance) {
        // Create a loan amount
        loanManager.approveLoan(this.account, startingLoan);

        // Repay part of the loan
        loanManager.repayLoan(this.account, loanRepayment);

        // Check expected results
        assertEquals(expectedLoanBalance, account.getLoan(), "Expected loan balance after taking " +
                "an initial loan of " + startingLoan + " and a repayment of " + loanRepayment +
                " should be " + expectedLoanBalance + ", but was " + account.getLoan());
    }


    /**
     * Test to ensure negative amounts cannot be used to repay the loan amount. An
     * IllegalArgumentException is thrown if the amount is negative
     */
    @Test
    public void testRepayLoanWithNegativeNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> loanManager.repayLoan(this.account,
                        -100),
                "Repaying the loan using negative number should throw IllegalArgumentException");
    }

    /**
     * Test to ensure amounts greater than the loan balance cannot be repaid to the
     * account. An ExcessLoanRepaymentException is thrown if the amount exceeds thr balance.
     */
    @Test
    public void testWithdrawalWithExceedingAmountThrowsInsufficientFundsException() {
        // Create a loan amount
        loanManager.approveLoan(this.account, 100);

        assertThrows(ExcessLoanRepaymentException.class,
                () -> loanManager.repayLoan(this.account, 10_000), "Reyaping an amount " +
                        "greater than the balance available should throw " +
                        "ExcessLoanRepaymentException");
    }

}