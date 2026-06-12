package com.bank.loans.service;

import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.service.strategies.DefaultLoanStrategy;
import com.bank.loans.service.strategies.SalariedLoanStrategy;
import com.bank.loans.service.strategies.SelfEmployedLoanStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class LoanDecisionStrategyTest {

    private SalariedLoanStrategy salariedStrategy;
    private SelfEmployedLoanStrategy selfEmployedStrategy;
    private DefaultLoanStrategy defaultStrategy;

    @BeforeEach
    public void setUp() {
        salariedStrategy = new SalariedLoanStrategy();
        selfEmployedStrategy = new SelfEmployedLoanStrategy();
        defaultStrategy = new DefaultLoanStrategy();
    }

    // SalariedLoanStrategy Tests
    @Test
    public void testSalariedStrategy_ApprovalAt750PlusScore() {
        LoanApplicationRequest request = createRequest(750, "SALARIED");
        LoanDecisionResponse response = salariedStrategy.evaluate(request);

        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("7.5"), response.getInterestRate());
        assertEquals("Excellent credit profile", response.getMessage());
    }

    @Test
    public void testSalariedStrategy_ApprovalAt600To749Score() {
        LoanApplicationRequest request = createRequest(650, "SALARIED");
        LoanDecisionResponse response = salariedStrategy.evaluate(request);

        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("12.0"), response.getInterestRate());
        assertEquals("Standard credit profile", response.getMessage());
    }

    @Test
    public void testSalariedStrategy_RejectionBelow600() {
        LoanApplicationRequest request = createRequest(550, "SALARIED");
        LoanDecisionResponse response = salariedStrategy.evaluate(request);

        assertEquals("REJECTED", response.getStatus());
        assertNull(response.getInterestRate());
        assertEquals("Credit score below minimum threshold", response.getMessage());
    }

    @Test
    public void testSalariedStrategy_RejectionAtBoundary599() {
        LoanApplicationRequest request = createRequest(599, "SALARIED");
        LoanDecisionResponse response = salariedStrategy.evaluate(request);

        assertEquals("REJECTED", response.getStatus());
        assertNull(response.getInterestRate());
    }

    @Test
    public void testSalariedStrategy_ApprovalAtBoundary600() {
        LoanApplicationRequest request = createRequest(600, "SALARIED");
        LoanDecisionResponse response = salariedStrategy.evaluate(request);

        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("12.0"), response.getInterestRate());
    }

    @Test
    public void testSalariedStrategy_RejectionAtBoundary749() {
        LoanApplicationRequest request = createRequest(749, "SALARIED");
        LoanDecisionResponse response = salariedStrategy.evaluate(request);

        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("12.0"), response.getInterestRate());
    }

    @Test
    public void testSalariedStrategy_ApprovalAtBoundary750() {
        LoanApplicationRequest request = createRequest(750, "SALARIED");
        LoanDecisionResponse response = salariedStrategy.evaluate(request);

        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("7.5"), response.getInterestRate());
    }

    @Test
    public void testSalariedStrategy_RejectionOnNullScore() {
        LoanApplicationRequest request = createRequest(null, "SALARIED");
        LoanDecisionResponse response = salariedStrategy.evaluate(request);

        assertEquals("REJECTED", response.getStatus());
        assertNull(response.getInterestRate());
        assertEquals("Credit score too low or not provided", response.getMessage());
    }

    @Test
    public void testSalariedStrategy_Supports() {
        assertTrue(salariedStrategy.supports("SALARIED"));
        assertFalse(salariedStrategy.supports("SELF_EMPLOYED"));
        assertFalse(salariedStrategy.supports(null));
    }

    // SelfEmployedLoanStrategy Tests
    @Test
    public void testSelfEmployedStrategy_ApprovalAt750PlusScore() {
        LoanApplicationRequest request = createRequest(800, "SELF_EMPLOYED");
        LoanDecisionResponse response = selfEmployedStrategy.evaluate(request);

        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("7.5"), response.getInterestRate());
    }

    @Test
    public void testSelfEmployedStrategy_ApprovalAt600To749Score() {
        LoanApplicationRequest request = createRequest(680, "SELF_EMPLOYED");
        LoanDecisionResponse response = selfEmployedStrategy.evaluate(request);

        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("12.0"), response.getInterestRate());
    }

    @Test
    public void testSelfEmployedStrategy_RejectionBelow600() {
        LoanApplicationRequest request = createRequest(400, "SELF_EMPLOYED");
        LoanDecisionResponse response = selfEmployedStrategy.evaluate(request);

        assertEquals("REJECTED", response.getStatus());
        assertNull(response.getInterestRate());
    }

    @Test
    public void testSelfEmployedStrategy_Supports() {
        assertTrue(selfEmployedStrategy.supports("SELF_EMPLOYED"));
        assertFalse(selfEmployedStrategy.supports("SALARIED"));
        assertFalse(selfEmployedStrategy.supports(null));
    }

    // DefaultLoanStrategy Tests
    @Test
    public void testDefaultStrategy_ApprovalAt750PlusScore() {
        LoanApplicationRequest request = createRequest(800, "UNEMPLOYED");
        LoanDecisionResponse response = defaultStrategy.evaluate(request);

        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("7.5"), response.getInterestRate());
    }

    @Test
    public void testDefaultStrategy_ApprovalAt600To749Score() {
        LoanApplicationRequest request = createRequest(700, "RETIRED");
        LoanDecisionResponse response = defaultStrategy.evaluate(request);

        assertEquals("APPROVED", response.getStatus());
        assertEquals(new BigDecimal("12.0"), response.getInterestRate());
    }

    @Test
    public void testDefaultStrategy_RejectionBelow600() {
        LoanApplicationRequest request = createRequest(500, "UNEMPLOYED");
        LoanDecisionResponse response = defaultStrategy.evaluate(request);

        assertEquals("REJECTED", response.getStatus());
        assertNull(response.getInterestRate());
    }

    @Test
    public void testDefaultStrategy_SupportsAnyStatus() {
        assertTrue(defaultStrategy.supports("UNEMPLOYED"));
        assertTrue(defaultStrategy.supports("RETIRED"));
        assertTrue(defaultStrategy.supports("UNKNOWN"));
        assertTrue(defaultStrategy.supports(null));
    }

    // Helper method
    private LoanApplicationRequest createRequest(Integer creditScore, String employmentStatus) {
        LoanApplicationRequest request = new LoanApplicationRequest();
        request.setCreditScore(creditScore);
        request.setEmploymentStatus(employmentStatus);
        request.setApplicantId("TEST123");
        return request;
    }
}
