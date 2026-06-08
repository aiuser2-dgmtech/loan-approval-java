package com.bank.loans.service;

import com.bank.loans.domain.LoanApplication;
import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class LoanApprovalService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    public LoanDecisionResponse processApplication(LoanApplicationRequest request) {
        System.out.println("Processing loan for: " + request.getApplicantId());

        LoanApplication application = new LoanApplication();

        String status;
        BigDecimal rate;
        String message;

        if (request.getCreditScore() == null || request.getCreditScore() < 300) {
            status = "REJECTED";
            rate = null;
            message = "Credit score too low or not provided";
        } else if (request.getCreditScore() >= 750) {
            status = "APPROVED";
            rate = new BigDecimal("7.5");
            message = "Excellent credit profile";
        } else if (request.getCreditScore() >= 600) {
            status = "APPROVED";
            rate = new BigDecimal("12.0");
            message = "Standard credit profile";
        } else {
            status = "REJECTED";
            rate = null;
            message = "Credit score below minimum threshold";
        }

        return new LoanDecisionResponse(null, status, rate, message);
    }
}