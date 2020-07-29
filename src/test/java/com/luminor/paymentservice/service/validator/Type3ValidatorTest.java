package com.luminor.paymentservice.service.validator;

import com.luminor.paymentservice.persistence.entity.Payment;
import com.luminor.paymentservice.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.luminor.paymentservice.TestData.paymentBuilder;
import static com.luminor.paymentservice.enums.PaymentType.TYPE3;
import static org.junit.jupiter.api.Assertions.*;

class Type3ValidatorTest {

    private Type3Validator type3Validator;

    @BeforeEach
    void setUp() {
        type3Validator = new Type3Validator();
    }

    @Test
    void testGetType() {
        assertEquals(TYPE3, type3Validator.getType());
    }

    @Test
    void isValid_ShouldReturnFalseForPaymentNull() {

        assertFalse(type3Validator.isValid(null));
    }

    @Test
    void isValid_ShouldReturnFalseForEmptyProperties() {
        Payment payment = Payment.builder().build();

        assertFalse(type3Validator.isValid(payment));
    }

    @Test
    void isValid_ShouldReturnFalseForCurrencyNull() {
        Payment payment = paymentBuilder()
                .type(TYPE3)
                .currency(null)
                .build();

        assertFalse(type3Validator.isValid(payment));
    }

    @Test
    void isValid_ShouldReturnFalseForCreditorBicNull() {
        Payment payment = paymentBuilder()
                .type(TYPE3)
                .currency(Currency.USD)
                .creditorBic(null)
                .build();

        assertFalse(type3Validator.isValid(payment));
    }

    @Test
    void isValid_ShouldReturnFalseForCreditorBicEmpty() {
        Payment payment = paymentBuilder()
                .type(TYPE3)
                .currency(Currency.EUR)
                .creditorBic("")
                .build();

        assertFalse(type3Validator.isValid(payment));
    }

    @Test
    void isValid_ShouldReturnTrueForCurrencyNotNull_CreditorBicNotEmpty() {
        Payment payment = paymentBuilder()
                .type(TYPE3)
                .currency(Currency.USD)
                .creditorBic("test")
                .build();

        assertTrue(type3Validator.isValid(payment));
    }

}