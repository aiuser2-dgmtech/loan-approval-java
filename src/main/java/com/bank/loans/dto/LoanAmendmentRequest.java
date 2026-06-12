package com.bank.loans.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class LoanAmendmentRequest {
    @NotNull(message = "Application ID is required")
    private Long applicationId;
    
    @NotNull
    @DecimalMin(value = "10000", message = "Minimum loan is Rs.10,000")
    @DecimalMax(value = "5000000", message = "Maximum loan is Rs.50,00,000")
    private BigDecimal newRequestedAmount;
    
    @NotBlank
    @Size(min = 2, max = 100)
    private String reason;

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long a) { this.applicationId = a; }
    public BigDecimal getNewRequestedAmount() { return newRequestedAmount; }
    public void setNewRequestedAmount(BigDecimal n) { this.newRequestedAmount = n; }
    public String getReason() { return reason; }
    public void setReason(String r) { this.reason = r; }
}