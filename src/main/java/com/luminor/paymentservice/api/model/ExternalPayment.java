package com.luminor.paymentservice.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.luminor.paymentservice.enums.Currency;
import com.luminor.paymentservice.enums.PaymentType;
import com.luminor.paymentservice.persistence.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExternalPayment {

    private Long id;
    private BigDecimal amount;
    private Currency currency;
    private String debtorIban;
    private String creditorIban;
    private PaymentType type;
    private String details;
    private String creditorBic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean cancelled;
    private BigDecimal cancellationFee;
    private LocalDateTime cancelledAt;

    public static ExternalPayment valueOf(Payment payment) {
        return ExternalPayment.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .debtorIban(payment.getDebtorIban())
                .creditorIban(payment.getCreditorIban())
                .type(payment.getType())
                .details(payment.getDetails())
                .creditorBic(payment.getCreditorBic())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .cancelled(payment.isCancelled())
                .cancellationFee(payment.getCancellationFee())
                .cancelledAt(payment.getCancelledAt())
                .build();
    }
}
