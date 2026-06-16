package com.bank.loans.dto;

public record EligibilitySummaryResponse(
        boolean eligible,
        int minimumCreditScore,
        int applicantScore,
        String message
) {}
