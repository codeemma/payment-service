package com.luminor.paymentservice.service.validator;

import com.luminor.paymentservice.persistence.entity.Payment;
import com.luminor.paymentservice.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.luminor.paymentservice.TestData.paymentBuilder;
import static com.luminor.paymentservice.enums.PaymentType.TYPE2;
import static org.junit.jupiter.api.Assertions.*;

class Type2ValidatorTest {

    private Type2Validator type2Validator;

    @BeforeEach
    void setUp() {
        type2Validator = new Type2Validator();
    }

    @Test
    void testGetType() {
        assertEquals(TYPE2, type2Validator.getType());
    }

    @Test
    void isValid_ShouldReturnFalseForPaymentNull() {

        assertFalse(type2Validator.isValid(null));
    }

    @Test
    void isValid_ShouldReturnFalseForEmptyProperties() {
        Payment payment = Payment.builder().build();

        assertFalse(type2Validator.isValid(payment));
    }

    @Test
    void isValid_ShouldReturnFalseForCurrencyEUR() {
        Payment payment = paymentBuilder()
                .type(TYPE2)
                .currency(Currency.EUR)
                .build();

        assertFalse(type2Validator.isValid(payment));
    }

    @Test
    void isValid_ShouldReturnTrueForCurrencyUSD_DetailsNull() {
        Payment payment = paymentBuilder()
                .type(TYPE2)
                .currency(Currency.USD)
                .details(null)
                .build();

        assertTrue(type2Validator.isValid(payment));
    }

    @Test
    void isValid_ShouldReturnTrueForCurrencyUSD_DetailsEmpty() {
        Payment payment = paymentBuilder()
                .type(TYPE2)
                .currency(Currency.USD)
                .details("")
                .build();

        assertTrue(type2Validator.isValid(payment));
    }

    @Test
    void isValid_ShouldReturnTrueForCurrencyUSD_DetailsNotEmpty() {
        Payment payment = paymentBuilder()
                .type(TYPE2)
                .currency(Currency.USD)
                .details("test")
                .build();

        assertTrue(type2Validator.isValid(payment));
    }

}