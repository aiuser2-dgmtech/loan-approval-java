package com.bank.loans.exception;

public class ApplicantNotFoundException extends RuntimeException {
    public ApplicantNotFoundException(String applicantId) {
        super("Applicant not found: " + applicantId);
    }
}
