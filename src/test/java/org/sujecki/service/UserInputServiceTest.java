package org.sujecki.service;

import org.junit.jupiter.api.Test;
import org.sujecki.config.LoanConfig;
import org.sujecki.exception.EmploymentDurationException;
import org.sujecki.model.LoanData;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UserInputServiceTest {

    @Test
    void getLoanDataFromUserValidInputShouldReturnLoanData() {
        String input = "12\n5000\n2000\n1000\n500\n2000\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        LoanConfig loanConfig = new LoanConfig();
        loanConfig.setMinCreditPeriod(6);

        try {
            LoanData loanData = UserInputService.getLoanDataFromUser(scanner, loanConfig);

            assertEquals(12, loanData.getEmploymentPeriod());
            assertEquals(5000.0, loanData.getMonthlyIncome());
            assertEquals(2000.0, loanData.getMonthlyLivingExpenses());
            assertEquals(1000.0, loanData.getMonthlyCreditObligations());
            assertEquals(500.0, loanData.getTotalOutstandingLoanBalance());
        } catch (EmploymentDurationException e) {
            fail("Unexpected EmploymentDurationException: " + e.getMessage());
        }
    }

    @Test
    void getLoanDataFromUserInvalidInputShouldThrowException() {
        String input = "invalid\n5000\n2000\n1000\n500\n2000\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        LoanConfig loanConfig = new LoanConfig();
        loanConfig.setMinCreditPeriod(6);

        assertThrows(IllegalArgumentException.class,
                () -> UserInputService.getLoanDataFromUser(scanner, loanConfig),
                "Expected IllegalArgumentException for invalid input");
    }

    @Test
    public void testGetLoanDataFromUserThrowsException() {
        // Prepare test input
        String input = "5\n10000.0\n2000.0\n500.0\n0.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Create a LoanConfig with minCreditPeriod set to 6
        LoanConfig loanConfig = new LoanConfig();
        loanConfig.setMinCreditPeriod(6);

        // Use a try-with-resources block to ensure the Scanner is closed
        try (Scanner scanner = new Scanner(System.in)) {
            // Assert that the method throws EmploymentDurationException
            assertThrows(EmploymentDurationException.class,
                    () -> UserInputService.getLoanDataFromUser(scanner, loanConfig),
                    "Expected getLoanDataFromUser to throw EmploymentDurationException");
        }

        // Reset System.in
        System.setIn(System.in);
    }
}