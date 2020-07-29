package com.luminor.paymentservice.persistence.entity;

import com.luminor.paymentservice.enums.Currency;
import com.luminor.paymentservice.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(nullable = false)
    private String debtorIban;

    @Column(nullable = false)
    private String creditorIban;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    private String details;

    private String creditorBic;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private boolean cancelled;

    private BigDecimal cancellationFee;

    private LocalDateTime cancelledAt;

    public PaymentBuilder toBuilder() {
        return builder()
                .id(id)
                .amount(amount)
                .currency(currency)
                .type(type)
                .debtorIban(debtorIban)
                .creditorIban(creditorIban)
                .details(details)
                .creditorBic(creditorBic)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .cancelled(cancelled)
                .cancelledAt(cancelledAt);
                
    }
}
