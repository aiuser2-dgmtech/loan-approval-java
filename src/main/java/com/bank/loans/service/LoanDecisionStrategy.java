package com.bank.loans.service;

import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;

public interface LoanDecisionStrategy {
    
    LoanDecisionResponse evaluate(LoanApplicationRequest request);
    
    boolean supports(String employmentStatus);
}
