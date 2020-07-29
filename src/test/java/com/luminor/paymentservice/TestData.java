package com.luminor.paymentservice;

import com.luminor.paymentservice.api.model.ExternalPaymentCreate;
import com.luminor.paymentservice.persistence.entity.Payment;
import com.luminor.paymentservice.enums.Currency;
import com.luminor.paymentservice.enums.PaymentType;

import java.math.BigDecimal;

public final class TestData {

    private TestData() {
    }

    public static Payment createPayment() {
        return paymentBuilder().build();
    }

    public static Payment.PaymentBuilder paymentBuilder() {
        return Payment.builder()
                .type(PaymentType.TYPE1)
                .amount(BigDecimal.valueOf(1500))
                .currency(Currency.EUR)
                .creditorIban("0000000000")
                .debtorIban("1111111111")
                .details("test");
    }

    public static ExternalPaymentCreate externalPaymentCreate() {
        return externalPaymentCreateBuilder().build();
    }

    public static ExternalPaymentCreate.ExternalPaymentCreateBuilder externalPaymentCreateBuilder() {
        return ExternalPaymentCreate.builder()
                .type(PaymentType.TYPE1)
                .amount(BigDecimal.valueOf(1500))
                .currency(Currency.EUR)
                .creditorIban("0000000000")
                .debtorIban("1111111111")
                .details("test");
    }


}
