package org.sujecki.model;

import lombok.Getter;

@Getter
public class LoanData {
    private int employmentPeriod;
    private double monthlyIncome;
    private double monthlyLivingExpenses;
    private double monthlyCreditObligations;
    private double totalOutstandingLoanBalance;

    public LoanData(int employmentPeriod, double monthlyIncome, double monthlyLivingExpenses,
                    double monthlyCreditObligations, double totalOutstandingLoanBalance) {
        this.employmentPeriod = employmentPeriod;
        this.monthlyIncome = monthlyIncome;
        this.monthlyLivingExpenses = monthlyLivingExpenses;
        this.monthlyCreditObligations = monthlyCreditObligations;
        this.totalOutstandingLoanBalance = totalOutstandingLoanBalance;
    }


    public int getEmploymentPeriod() {
        return employmentPeriod;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public double getMonthlyLivingExpenses() {
        return monthlyLivingExpenses;
    }

    public double getMonthlyCreditObligations() {
        return monthlyCreditObligations;
    }

    public double getTotalOutstandingLoanBalance() {
        return totalOutstandingLoanBalance;
    }

}