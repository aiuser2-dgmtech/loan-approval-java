package com.bank.loans.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String applicantId;
    private BigDecimal requestedAmount;
    private Integer creditScore;
    private BigDecimal annualIncome;
    private String employmentStatus;
    private String status;
    private BigDecimal interestRate;
    private LocalDateTime createdAt;
    private String rejectionReason;
}