package com.luminor.paymentservice.api.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.luminor.paymentservice.enums.Currency;
import com.luminor.paymentservice.enums.PaymentType;
import com.luminor.paymentservice.persistence.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExternalPaymentCreate {

    @NotNull
    private BigDecimal amount;
    @NotNull
    private Currency currency;
    @NotEmpty
    private String debtorIban;
    @NotEmpty
    private String creditorIban;
    @NotNull
    private PaymentType type;
    private String details;
    private String creditorBic;

    public Payment toPayment() {
        return Payment.builder()
                .amount(amount)
                .currency(currency)
                .debtorIban(debtorIban)
                .creditorIban(creditorIban)
                .type(type)
                .details(details)
                .creditorBic(creditorBic)
                .build();
    }

    @Override
    public String toString() {
        return "{\"ExternalPaymentCreate\":{"
                + "\"amount\":\"" + amount + "\""
                + ", \"currency\":\"" + currency + "\""
                + ", \"debtorIban\":\"" + debtorIban + "\""
                + ", \"creditorIban\":\"" + creditorIban + "\""
                + ", \"type\":\"" + type + "\""
                + ", \"details\":\"" + details + "\""
                + ", \"creditorBic\":\"" + creditorBic + "\""
                + "}}";
    }
}
