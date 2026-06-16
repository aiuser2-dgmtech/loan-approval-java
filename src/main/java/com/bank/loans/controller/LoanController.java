package com.bank.loans.controller;

import com.bank.loans.dto.EligibilitySummaryResponse;
import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.service.LoanApprovalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@Validated
public class LoanController {

    private final LoanApprovalService loanApprovalService;

    public LoanController(LoanApprovalService loanApprovalService) {
        this.loanApprovalService = loanApprovalService;
    }

    @PostMapping("/apply")
    public ResponseEntity<LoanDecisionResponse> applyForLoan(
            @Valid @RequestBody LoanApplicationRequest request) {
        LoanDecisionResponse response = loanApprovalService.processApplication(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{applicantId}/eligibility-summary")
    public ResponseEntity<EligibilitySummaryResponse> getEligibilitySummary(
            @PathVariable String applicantId,
            @RequestParam Integer creditScore) {
        if (creditScore == null) {
            return ResponseEntity.badRequest().build();
        }
        EligibilitySummaryResponse response = loanApprovalService.evaluateEligibility(applicantId, creditScore);
        return ResponseEntity.ok(response);
    }
}