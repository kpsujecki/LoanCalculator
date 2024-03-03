package org.sujecki.service;

import org.junit.jupiter.api.Test;
import org.sujecki.config.LoanConfig;
import org.sujecki.exception.EmploymentDurationException;
import org.sujecki.model.LoanData;
import org.sujecki.model.LoanOffer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
class LoanOfferServiceTest {

    private static final String FILE_CONFIG_PATH = "src/test/resources/application.yml";

    @Test
    void whenCreditPeriodIs12ShouldBeReturnTwoOffers() {
        // given
        LoanConfig loanConfig = LoanConfig.readFromFile(FILE_CONFIG_PATH);
        LoanOfferService loanOfferService = new LoanOfferServiceImpl(loanConfig);

        int employmentPeriod = 12;
        double monthlyIncome = 15000;
        double monthlyLivingExpenses = 4000;
        double monthlyCreditObligations = 3000;
        double totalOutstandingLoanBalance = 0;

        LoanData loanData = new LoanData(employmentPeriod, monthlyIncome, monthlyLivingExpenses,
                monthlyCreditObligations, totalOutstandingLoanBalance);

        // when
        List<LoanOffer> offers = loanOfferService.findLoanOffers(loanData);

        // then
        assertEquals(1, offers.size());

        LoanOffer firstOffer = offers.get(0);
        assertEquals(12, firstOffer.getMaximumCreditDuration());
        assertEquals(6000, firstOffer.getMaximumMonthlyRate(), 0.01);
        assertEquals(71226.02, firstOffer.getMaximumCreditAmount(), 0.01);
    }


    @Test
    void whenCreditPeriodIs24ShouldBeReturnTwoOffers() {
        // given
        LoanConfig loanConfig = LoanConfig.readFromFile(FILE_CONFIG_PATH);
        LoanOfferService loanOfferService = new LoanOfferServiceImpl(loanConfig);

        int employmentPeriod = 24;
        double monthlyIncome = 5000;
        double monthlyLivingExpenses = 2000;
        double monthlyCreditObligations = 1000;
        double totalOutstandingLoanBalance = 0;

        LoanData loanData = new LoanData(employmentPeriod, monthlyIncome, monthlyLivingExpenses,
                monthlyCreditObligations, totalOutstandingLoanBalance);

        // when
        List<LoanOffer> offers = loanOfferService.findLoanOffers(loanData);

        // then
        assertEquals(2, offers.size());

        LoanOffer firstOffer = offers.get(0);
        assertEquals(12, firstOffer.getMaximumCreditDuration());
        assertEquals(2000, firstOffer.getMaximumMonthlyRate(), 0.01);
        assertEquals(23742.00, firstOffer.getMaximumCreditAmount(), 0.01);

        LoanOffer secondOffer = offers.get(1);
        assertEquals(24, secondOffer.getMaximumCreditDuration());
        assertEquals(2000, secondOffer.getMaximumMonthlyRate(), 0.01);
        assertEquals(46531.95, secondOffer.getMaximumCreditAmount(), 0.01);
    }

    @Test
    void whenCreditPeriodIs25ShouldBeReturnFourOffers() {
        // given
        LoanConfig loanConfig = LoanConfig.readFromFile(FILE_CONFIG_PATH);
        LoanOfferService loanOfferService = new LoanOfferServiceImpl(loanConfig);

        int employmentPeriod = 100;
        double monthlyIncome = 5000;
        double monthlyLivingExpenses = 2000;
        double monthlyCreditObligations = 1000;
        double totalOutstandingLoanBalance = 0;

        LoanData loanData = new LoanData(employmentPeriod, monthlyIncome, monthlyLivingExpenses,
                monthlyCreditObligations, totalOutstandingLoanBalance);

        // when
        List<LoanOffer> offers = loanOfferService.findLoanOffers(loanData);

        // then
        assertEquals(4, offers.size());

        LoanOffer firstOffer = offers.get(0);
        assertEquals(12, firstOffer.getMaximumCreditDuration());
        assertEquals(2000, firstOffer.getMaximumMonthlyRate(), 0.01);
        assertEquals(23742.00, firstOffer.getMaximumCreditAmount(), 0.01);

        LoanOffer secondOffer = offers.get(1);
        assertEquals(24, secondOffer.getMaximumCreditDuration());
        assertEquals(2000, secondOffer.getMaximumMonthlyRate(), 0.01);
        assertEquals(46531.95, secondOffer.getMaximumCreditAmount(), 0.01);

        LoanOffer thirdOffer = offers.get(2);
        assertEquals(36, thirdOffer.getMaximumCreditDuration());
        assertEquals(2000, thirdOffer.getMaximumMonthlyRate(), 0.01);
        assertEquals(68772.93, thirdOffer.getMaximumCreditAmount(), 0.01);

        LoanOffer fourthOffer = offers.get(3);
        assertEquals(100, fourthOffer.getMaximumCreditDuration());
        assertEquals(1750, fourthOffer.getMaximumMonthlyRate(), 0.01);
        assertEquals(150000, fourthOffer.getMaximumCreditAmount(), 0.01);

    }

    @Test
    public void testFindLoanOffersWithInvalidData() {
        // Mock LoanConfig
        LoanConfig loanConfig = mock(LoanConfig.class);
        when(loanConfig.getMinCreditPeriod()).thenReturn(6);
        when(loanConfig.getMaxCreditPeriod()).thenReturn(100);
        when(loanConfig.getMinCreditAmount()).thenReturn(1000.0);
        when(loanConfig.getMaxCreditAmount()).thenReturn(5000.0);
        when(loanConfig.getMaxEngagement()).thenReturn(10000.0);

        // Mock LoanData with invalid employment period
        LoanData loanData = mock(LoanData.class);
        when(loanData.getEmploymentPeriod()).thenReturn(5); // Invalid employment period

        // Create LoanOfferServiceImpl
        LoanOfferServiceImpl loanOfferService = new LoanOfferServiceImpl(loanConfig);

        // Test the method and expect an exception
        assertThrows(EmploymentDurationException.class, () -> loanOfferService.findLoanOffers(loanData));
    }

    @Test
    public void testFindLoanOffersWithInsufficientData() {
        // Mock LoanConfig with insufficient credit amount
        LoanConfig loanConfig = mock(LoanConfig.class);
        when(loanConfig.getMinCreditPeriod()).thenReturn(6);
        when(loanConfig.getMaxCreditPeriod()).thenReturn(100);
        when(loanConfig.getMinCreditAmount()).thenReturn(10000.0); // Invalid minimum credit amount
        when(loanConfig.getMaxCreditAmount()).thenReturn(5000.0);
        when(loanConfig.getMaxEngagement()).thenReturn(10000.0);

        // Mock LoanData
        LoanData loanData = mock(LoanData.class);
        when(loanData.getEmploymentPeriod()).thenReturn(12);
        when(loanData.getMonthlyIncome()).thenReturn(3000.0);
        when(loanData.getMonthlyLivingExpenses()).thenReturn(1000.0);
        when(loanData.getMonthlyCreditObligations()).thenReturn(500.0);
        when(loanData.getTotalOutstandingLoanBalance()).thenReturn(2000.0);

        // Create LoanOfferServiceImpl
        LoanOfferServiceImpl loanOfferService = new LoanOfferServiceImpl(loanConfig);

        // Test the method and expect an empty list due to insufficient credit amount
        List<LoanOffer> offers = loanOfferService.findLoanOffers(loanData);
        assertTrue(offers.isEmpty());
    }

}
