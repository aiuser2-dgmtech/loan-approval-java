package com.bank.loans.dto;

import java.math.BigDecimal;

public class LoanDecisionResponse {
    private Long applicationId;
    private String status;
    private BigDecimal interestRate;
    private String message;

    public LoanDecisionResponse(Long applicationId, String status,
                                BigDecimal interestRate, String message) {
        this.applicationId = applicationId;
        this.status = status;
        this.interestRate = interestRate;
        this.message = message;
    }

    public Long getApplicationId() { return applicationId; }
    public String getStatus() { return status; }
    public BigDecimal getInterestRate() { return interestRate; }
    public String getMessage() { return message; }
}