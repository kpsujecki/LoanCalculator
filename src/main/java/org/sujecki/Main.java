package org.sujecki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sujecki.config.LoanConfig;
import org.sujecki.exception.EmploymentDurationException;
import org.sujecki.model.LoanData;
import org.sujecki.model.LoanOffer;
import org.sujecki.service.LoanOfferService;
import org.sujecki.service.LoanOfferServiceImpl;
import org.sujecki.service.UserInputService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            LoanConfig loanConfig = LoanConfig.readFromFile("src/main/resources/application.yml");
            Scanner scanner = new Scanner(System.in);
            logger.info("Welcome in Loan Calculator!");

            LoanData loanData = UserInputService.getLoanDataFromUser(scanner, loanConfig);

            LoanOfferService loanOfferService = new LoanOfferServiceImpl(loanConfig);

            List<LoanOffer> offers = loanOfferService.findLoanOffers(loanData);

            if (offers.isEmpty()) {
                logger.info("No credit capacity.");
            } else {
                logger.info("Found loan offers:");
                for (LoanOffer offer : offers) {
                    logger.info(offer.toString());
                }
            }
        } catch (EmploymentDurationException e) {
            logger.error("Error: {}", e.getMessage(), e);
        }
    }
}