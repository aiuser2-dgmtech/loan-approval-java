package com.bank.loans.service.strategies;

import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.service.LoanDecisionStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SalariedLoanStrategy implements LoanDecisionStrategy {

    @Override
    public LoanDecisionResponse evaluate(LoanApplicationRequest request) {
        Integer creditScore = request.getCreditScore();

        if (creditScore == null || creditScore < 300) {
            return new LoanDecisionResponse(null, "REJECTED", null, "Credit score too low or not provided");
        } else if (creditScore >= 800) {
            return new LoanDecisionResponse(null, "APPROVED", new BigDecimal("7.5"), "Excellent credit profile");
        } else if (creditScore >= 600) {
            return new LoanDecisionResponse(null, "APPROVED", new BigDecimal("12.0"), "Standard credit profile");
        } else {
            return new LoanDecisionResponse(null, "REJECTED", null, "Credit score below minimum threshold");
        }
    }

    @Override
    public boolean supports(String employmentStatus) {
        return "SALARIED".equals(employmentStatus);
    }
}
