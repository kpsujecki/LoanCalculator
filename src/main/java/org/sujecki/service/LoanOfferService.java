package org.sujecki.service;

import org.sujecki.model.LoanData;
import org.sujecki.model.LoanOffer;

import java.util.List;

public interface LoanOfferService {

    List<LoanOffer> findLoanOffers(LoanData loanData);

}
