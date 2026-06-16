package com.bank.loans.service;

import com.bank.loans.domain.LoanApplication;
import com.bank.loans.dto.EligibilitySummaryResponse;
import com.bank.loans.exception.ApplicantNotFoundException;
import com.bank.loans.repository.LoanApplicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanApprovalServiceTest {

    private LoanApprovalService loanApprovalService;

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private LoanDecisionEngine decisionEngine;

    @BeforeEach
    public void setUp() {
        loanApprovalService = new LoanApprovalService(loanApplicationRepository, decisionEngine);
    }

    @Test
    public void testEvaluateEligibility_EligibleAt600() {
        String applicantId = "APP001";
        Integer creditScore = 600;
        when(loanApplicationRepository.findByApplicantId(applicantId))
                .thenReturn(List.of(new LoanApplication()));

        EligibilitySummaryResponse response = loanApprovalService.evaluateEligibility(applicantId, creditScore);

        assertTrue(response.eligible());
        assertEquals(600, response.minimumCreditScore());
        assertEquals(600, response.applicantScore());
        assertEquals("Applicant meets minimum credit score requirement", response.message());
    }

    @Test
    public void testEvaluateEligibility_EligibleAbove600() {
        String applicantId = "APP002";
        Integer creditScore = 750;
        when(loanApplicationRepository.findByApplicantId(applicantId))
                .thenReturn(List.of(new LoanApplication()));

        EligibilitySummaryResponse response = loanApprovalService.evaluateEligibility(applicantId, creditScore);

        assertTrue(response.eligible());
        assertEquals(600, response.minimumCreditScore());
        assertEquals(750, response.applicantScore());
        assertEquals("Applicant meets minimum credit score requirement", response.message());
    }

    @Test
    public void testEvaluateEligibility_NotEligibleBelow600() {
        String applicantId = "APP003";
        Integer creditScore = 550;
        when(loanApplicationRepository.findByApplicantId(applicantId))
                .thenReturn(List.of(new LoanApplication()));

        EligibilitySummaryResponse response = loanApprovalService.evaluateEligibility(applicantId, creditScore);

        assertFalse(response.eligible());
        assertEquals(600, response.minimumCreditScore());
        assertEquals(550, response.applicantScore());
        assertTrue(response.message().contains("below minimum threshold"));
    }

    @Test
    public void testEvaluateEligibility_BoundaryAt599() {
        String applicantId = "APP004";
        Integer creditScore = 599;
        when(loanApplicationRepository.findByApplicantId(applicantId))
                .thenReturn(List.of(new LoanApplication()));

        EligibilitySummaryResponse response = loanApprovalService.evaluateEligibility(applicantId, creditScore);

        assertFalse(response.eligible());
        assertEquals(599, response.applicantScore());
    }

    @Test
    public void testEvaluateEligibility_NullCreditScore() {
        String applicantId = "APP005";
        when(loanApplicationRepository.findByApplicantId(applicantId))
                .thenReturn(List.of(new LoanApplication()));

        EligibilitySummaryResponse response = loanApprovalService.evaluateEligibility(applicantId, null);

        assertFalse(response.eligible());
        assertEquals(0, response.applicantScore());
        assertTrue(response.message().contains("below minimum threshold"));
    }

    @Test
    public void testEvaluateEligibility_LowScore() {
        String applicantId = "APP006";
        Integer creditScore = 300;
        when(loanApplicationRepository.findByApplicantId(applicantId))
                .thenReturn(List.of(new LoanApplication()));

        EligibilitySummaryResponse response = loanApprovalService.evaluateEligibility(applicantId, creditScore);

        assertFalse(response.eligible());
        assertEquals(300, response.applicantScore());
    }

    @Test
    public void testEvaluateEligibility_ApplicantNotFound() {
        String applicantId = "NONEXISTENT";
        when(loanApplicationRepository.findByApplicantId(applicantId))
                .thenReturn(List.of());

        assertThrows(ApplicantNotFoundException.class,
                () -> loanApprovalService.evaluateEligibility(applicantId, 600));
    }
}
