package com.bank.loans.service;

import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanDecisionEngine {

    private static final Logger log = LoggerFactory.getLogger(LoanDecisionEngine.class);
    private final List<LoanDecisionStrategy> strategies;

    public LoanDecisionEngine(List<LoanDecisionStrategy> strategies) {
        this.strategies = strategies;
    }

    public LoanDecisionResponse evaluate(LoanApplicationRequest request) {
        String employmentStatus = request.getEmploymentStatus();
        
        LoanDecisionStrategy selectedStrategy = strategies.stream()
                .filter(strategy -> strategy.supports(employmentStatus))
                .findFirst()
                .orElse(strategies.stream()
                        .filter(strategy -> strategy.supports(null))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("No suitable strategy found for employment status: " + employmentStatus)));
        
        log.debug("Selected strategy for employment status '{}': {}", employmentStatus, selectedStrategy.getClass().getSimpleName());
        return selectedStrategy.evaluate(request);
    }
}
