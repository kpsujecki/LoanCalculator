package org.sujecki.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sujecki.config.LoanConfig;
import org.sujecki.model.LoanData;
import org.sujecki.model.LoanOffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoanOfferServiceImpl implements LoanOfferService {
    private static final Logger logger = LoggerFactory.getLogger(LoanOfferServiceImpl.class);

    LoanConfig loanConfig;

    public LoanOfferServiceImpl(LoanConfig loanConfig) {
        this.loanConfig = loanConfig;
    }

        @Override
        public List<LoanOffer> findLoanOffers(LoanData loanData) {
            int maxCreditPeriod = Math.min(loanData.getEmploymentPeriod(), loanConfig.getMaxCreditPeriod());
            int minCreditPeriod = loanConfig.getMinCreditPeriod();
            double minCreditAmount = loanConfig.getMinCreditAmount();
            double maxCreditAmount = loanConfig.getMaxCreditAmount();

            if (maxCreditPeriod < minCreditPeriod || maxCreditAmount < minCreditAmount) {
                return Collections.emptyList();
            }

            List<LoanOffer> offers = new ArrayList<>();

            if (maxCreditPeriod >=6) {
                offers.add(generateLoanOffer(loanData, 12));
                if (maxCreditPeriod == 24) {
                    offers.add(generateLoanOffer(loanData, 24));
                }
                if (maxCreditPeriod == 100){
                    offers.add(generateLoanOffer(loanData, 24));
                    offers.add(generateLoanOffer(loanData, 36));
                    offers.add(generateLoanOffer(loanData, 100));
                }
            }

            return offers;
        }

        private LoanOffer generateLoanOffer(LoanData loanData, int creditPeriod) {

            double maxMonthlyRate = calculateMaximumMonthlyRate(loanData, creditPeriod);
            double maxCreditAmount = calculateMaximumCreditAmount(loanData, maxMonthlyRate, creditPeriod);

            return new LoanOffer(creditPeriod, maxMonthlyRate, maxCreditAmount);
        }


        private double getDtiForCreditPeriod(int creditPeriod) {
            if (creditPeriod >= 6 && creditPeriod <= 12) {
                return loanConfig.getDti().get("period6_12");
            } else if (creditPeriod >= 13 && creditPeriod <= 36) {
                return loanConfig.getDti().get("period13_36");
            } else if (creditPeriod >= 37 && creditPeriod <= 60) {
                return loanConfig.getDti().get("period37_60");
            } else if (creditPeriod >= 61 && creditPeriod <= 100) {
                return loanConfig.getDti().get("period61_100");
            } else {
                logger.error("Invalid credit period: {}", creditPeriod);
                throw new IllegalArgumentException("Invalid credit period: " + creditPeriod);
            }
        }

        private double getInterestRateForCreditPeriod(int creditPeriod) {
            if (creditPeriod >= 6 && creditPeriod <= 12) {
                return loanConfig.getInterestRates().get("period6_12");
            } else if (creditPeriod >= 13 && creditPeriod <= 36) {
                return loanConfig.getInterestRates().get("period13_36");
            } else if (creditPeriod >= 37 && creditPeriod <= 60) {
                return loanConfig.getInterestRates().get("period37_60");
            } else if (creditPeriod >= 61 && creditPeriod <= 100) {
                return loanConfig.getInterestRates().get("period61_100");
            } else {
                logger.error("Invalid credit period: {}", creditPeriod);
                throw new IllegalArgumentException("Invalid credit period: " + creditPeriod);
            }
        }

        private double calculateMaximumMonthlyRate(LoanData loanData, int creditPeriod) {
            double dti = getDtiForCreditPeriod(creditPeriod);

            double dzKuZk = loanData.getMonthlyIncome() - loanData.getMonthlyLivingExpenses() - loanData.getMonthlyCreditObligations();
            double dtiDzKuZk = dti * loanData.getMonthlyIncome() - loanData.getMonthlyCreditObligations();

            double maxMonthlyRate = Math.min(dzKuZk, dtiDzKuZk);

            logger.debug("Calculated Maximum Monthly Rate: {}", maxMonthlyRate);
            return maxMonthlyRate;
        }

        private double calculateMaximumCreditAmount(LoanData loanData, double maximumMonthlyRate, int creditPeriod) {
            double maxEngagement = loanConfig.getMaxEngagement();
            double totalOutstandingLoanBalance = loanData.getTotalOutstandingLoanBalance();
            double maxCreditAmount = loanConfig.getMaxCreditAmount();
            double mi = getInterestRateForCreditPeriod(creditPeriod) / 12;

            double option1 = maxEngagement - totalOutstandingLoanBalance;
            double option2 = maxCreditAmount;
            double option3 = maximumMonthlyRate * (1 - Math.pow((1 + mi), - creditPeriod)) / mi;

            double calculatedMaxCreditAmount = Math.min(option1, Math.min(option2, option3));

            logger.debug("Calculated Max Credit Amount: {}", calculatedMaxCreditAmount);
            return calculatedMaxCreditAmount;
        }
    }