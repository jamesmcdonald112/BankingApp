package ie.atu.sw.validation;

import ie.atu.sw.exceptions.ExcessLoanRepaymentException;
import ie.atu.sw.exceptions.InsufficientFundsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    /**
     * Test makes sure an exception is not thrown when it is a positive number.
     */
    @Test
    public void testCheckPositiveValidAmount() {
        assertDoesNotThrow(() -> InputValidator.checkPositive(100.00, "Amount must be positive"));
    }

    /**
     * Test ensures an IllegalArgumentException is thrown when a negative amount is entered
     */
    @Test
    public void testCheckPositiveThrowsExceptionForNegativeAmount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                InputValidator.checkPositive(-50.00, "Amount must be positive"));
        assertEquals("Amount must be positive", exception.getMessage());
    }

    /**
     * Test ensures that no exception is thrown when there is sufficient funds available for the
     * transaction.
     */
    @Test
    public void testCheckSufficientFundsValidTransaction() {
        assertDoesNotThrow(() -> InputValidator.checkSufficientFunds(50.00, 100.00, "Insufficient funds"));
    }

    /**
     * Test ensures that InsufficientFundsException is thrown when there is not enough funds
     * available.
     */
    @Test
    public void testCheckSufficientFundsThrowsExceptionForInsufficientFunds() {
        Exception exception = assertThrows(InsufficientFundsException.class, () ->
                InputValidator.checkSufficientFunds(150.00, 100.00, "Insufficient funds"));
        assertEquals("Insufficient funds", exception.getMessage());
    }

    /**
     * Test ensures that no exception is thrown when a loan valid loan repayment is made.
     */
    @Test
    public void testCheckSufficientLoanRepaymentValidRepayment() {
        assertDoesNotThrow(() -> InputValidator.checkSufficientLoanRepayment(50.00, 100.00, "Excess loan repayment"));
    }

    /**
     * Test ensures that ExcessLoanRepaymentException is thrown when a larger amount is paid than
     * what is left on the balance.
     */
    @Test
    public void testCheckSufficientLoanRepaymentThrowsExceptionForExcessRepayment() {
        Exception exception = assertThrows(ExcessLoanRepaymentException.class, () ->
                InputValidator.checkSufficientLoanRepayment(150.00, 100.00, "Excess loan repayment"));
        assertEquals("Excess loan repayment", exception.getMessage());
    }


}