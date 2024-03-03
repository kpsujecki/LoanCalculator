package org.sujecki.model;

import lombok.Getter;

@Getter
public class LoanOffer {
    private int maximumCreditDuration;
    private double maximumMonthlyRate;
    private double maximumCreditAmount;

    public LoanOffer(int maximumCreditDuration, double maximumMonthlyRate,
                     double maximumCreditAmount) {
        this.maximumCreditDuration = maximumCreditDuration;
        this.maximumMonthlyRate = maximumMonthlyRate;
        this.maximumCreditAmount = maximumCreditAmount;
    }


    @Override
    public String toString() {
        return "Credit Offer:\n"
                + "Maximum Credit Duration: " + maximumCreditDuration + " months\n"
                + "Maximum Monthly Rate: " + String.format("%.2f", maximumMonthlyRate) + " PLN\n"
                + "Maximum Credit Amount: " + String.format("%.2f", maximumCreditAmount) + " PLN\n";
    }

}
