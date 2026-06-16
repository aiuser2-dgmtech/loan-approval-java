package com.bank.loans.service;

import com.bank.loans.domain.LoanApplication;
import com.bank.loans.dto.EligibilitySummaryResponse;
import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.exception.ApplicantNotFoundException;
import com.bank.loans.repository.LoanApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanApprovalService {

    private static final Logger log = LoggerFactory.getLogger(LoanApprovalService.class);
    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanDecisionEngine decisionEngine;

    public LoanApprovalService(LoanApplicationRepository loanApplicationRepository, LoanDecisionEngine decisionEngine) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.decisionEngine = decisionEngine;
    }

    public LoanDecisionResponse processApplication(LoanApplicationRequest request) {
        log.info("Processing loan application for applicant: {}", request.getApplicantId());

        LoanApplication application = new LoanApplication();

        LoanDecisionResponse decision = decisionEngine.evaluate(request);
        log.info("Loan decision for applicant {}: {}", request.getApplicantId(), decision.getStatus());

        return decision;
    }

    public EligibilitySummaryResponse evaluateEligibility(String applicantId, Integer creditScore) {
        log.info("Evaluating eligibility for applicant: {}, creditScore: {}", applicantId, creditScore);


        
        final int minimumCreditScore = 600;
        boolean eligible = creditScore != null && creditScore >= minimumCreditScore;
        
        String message = eligible
                ? "Applicant meets minimum credit score requirement"
                : "Credit score below minimum threshold of " + minimumCreditScore;
        
        return new EligibilitySummaryResponse(
                eligible,
                minimumCreditScore,
                creditScore != null ? creditScore : 0,
                message
        );
    }

    public List<LoanApplication> getApplicationsByApplicant(String applicantId) {
        return loanApplicationRepository.findByApplicantId(applicantId).stream()
                .sorted((a, b) -> {
                    if (a.getCreatedAt() == null || b.getCreatedAt() == null) {
                        return 0;
                    }
                    return b.getCreatedAt().compareTo(a.getCreatedAt());
                })
                .toList();
    }
}