package org.sujecki.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanConfigTest {

    @Test
    void readFromFile_ValidFile_ReturnsLoanConfig() {
        LoanConfig loanConfig = LoanConfig.readFromFile("src/test/resources/application.yml");

        assertNotNull(loanConfig);
        assertEquals(100, loanConfig.getMaxCreditPeriod());
        assertEquals(6, loanConfig.getMinCreditPeriod());
        assertEquals(5000.0, loanConfig.getMinCreditAmount());
        assertEquals(150000.0, loanConfig.getMaxCreditAmount());
        assertEquals(200000.0, loanConfig.getMaxEngagement());
        assertNotNull(loanConfig.getInterestRates());
        assertNotNull(loanConfig.getDti());
    }

    @Test
    void readFromFile_InvalidFile_ReturnsNull() {
        LoanConfig loanConfig = LoanConfig.readFromFile("src/test/resources/application-new.yml");

        assertNull(loanConfig);
    }
}