package com.bank.loans.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class LoanApplicationRequest {
    @NotBlank(message = "Applicant ID is required")
    private String applicantId;
    
    @NotNull
    @DecimalMin(value = "10000", message = "Minimum loan is Rs.10,000")
    @DecimalMax(value = "5000000", message = "Maximum loan is Rs.50,00,000")
    private BigDecimal requestedAmount;
    
    @NotNull
    @Min(300)
    @Max(900)
    private Integer creditScore;
    
    private BigDecimal annualIncome;
    private String employmentStatus;

    public String getApplicantId() { return applicantId; }
    public void setApplicantId(String applicantId) { this.applicantId = applicantId; }
    public BigDecimal getRequestedAmount() { return requestedAmount; }
    public void setRequestedAmount(BigDecimal requestedAmount) { this.requestedAmount = requestedAmount; }
    public Integer getCreditScore() { return creditScore; }
    public void setCreditScore(Integer creditScore) { this.creditScore = creditScore; }
    public BigDecimal getAnnualIncome() { return annualIncome; }
    public void setAnnualIncome(BigDecimal annualIncome) { this.annualIncome = annualIncome; }
    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }
}