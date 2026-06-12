package com.bank.loans.dto;

import jakarta.validation.constraints.*;

public class CreditBureauRequest {
    @NotBlank
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
    private String panNumber;
    
    @NotBlank
    @Pattern(regexp = "CIBIL|EXPERIAN|CRIF", message = "Bureau must be CIBIL, EXPERIAN, or CRIF")
    private String bureauName;
    
    @NotBlank(message = "Consent token is mandatory for bureau queries")
    private String consentToken;

    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String p) { this.panNumber = p; }
    public String getBureauName() { return bureauName; }
    public void setBureauName(String b) { this.bureauName = b; }
    public String getConsentToken() { return consentToken; }
    public void setConsentToken(String c) { this.consentToken = c; }
}