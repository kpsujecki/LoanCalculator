package org.sujecki.service;

import org.sujecki.config.LoanConfig;
import org.sujecki.exception.EmploymentDurationException;
import org.sujecki.model.LoanData;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInputService {

    public static LoanData getLoanDataFromUser(Scanner scanner, LoanConfig loanConfig) throws EmploymentDurationException {
        try {
            System.out.print("Enter employment period (in months): ");
            int employmentPeriod = scanner.nextInt();
            if (employmentPeriod < loanConfig.getMinCreditPeriod()) {
                throw new EmploymentDurationException("Error: Employment period must be equal to or greater than 6 months.");
            }

            System.out.print("Enter monthly income (in PLN): ");
            double monthlyIncome = scanner.nextDouble();

            System.out.print("Enter monthly living expenses (in PLN): ");
            double monthlyLivingExpenses = scanner.nextDouble();

            System.out.print("Enter monthly credit obligations (in PLN): ");
            double monthlyCreditObligations = scanner.nextDouble();

            System.out.print("Enter total outstanding loan balance (in PLN): ");
            double totalOutstandingLoanBalance = scanner.nextDouble();

            return new LoanData(employmentPeriod, monthlyIncome, monthlyLivingExpenses,
                    monthlyCreditObligations, totalOutstandingLoanBalance);
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException("Invalid input. Please enter numeric values.");
        }
    }
}