package com.bank.loans.controller;

import com.bank.loans.dto.LoanApplicationRequest;
import com.bank.loans.dto.LoanDecisionResponse;
import com.bank.loans.service.LoanApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanApprovalService loanApprovalService;

    @PostMapping("/apply")
    public ResponseEntity<LoanDecisionResponse> applyForLoan(
            @RequestBody LoanApplicationRequest request) {
        LoanDecisionResponse response = loanApprovalService.processApplication(request);
        return ResponseEntity.ok(response);
    }
}