package com.bank.loans.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ApplicantProfileRequest {
    @NotBlank(message = "Applicant ID is required")
    private String applicantId;
    
    @NotBlank
    @Size(min = 2, max = 100)
    private String fullName;
    
    @NotBlank
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
    private String panNumber;
    
    @NotBlank
    @Email(message = "Invalid email address")
    private String emailAddress;
    
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian mobile number")
    private String mobileNumber;
    
    @DecimalMin(value = "0", inclusive = true)
    private BigDecimal netWorth;
    
    @Min(value = 0, message = "Dependents cannot be negative")
    private Integer dependents;

    public String getApplicantId() { return applicantId; }
    public void setApplicantId(String a) { this.applicantId = a; }
    public String getFullName() { return fullName; }
    public void setFullName(String f) { this.fullName = f; }
    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String p) { this.panNumber = p; }
    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String e) { this.emailAddress = e; }
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String m) { this.mobileNumber = m; }
    public BigDecimal getNetWorth() { return netWorth; }
    public void setNetWorth(BigDecimal n) { this.netWorth = n; }
    public Integer getDependents() { return dependents; }
    public void setDependents(Integer d) { this.dependents = d; }
}