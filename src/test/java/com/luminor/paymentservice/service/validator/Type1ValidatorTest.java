package com.luminor.paymentservice.service.validator;

import com.luminor.paymentservice.persistence.entity.Payment;
import com.luminor.paymentservice.enums.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.luminor.paymentservice.TestData.paymentBuilder;
import static com.luminor.paymentservice.enums.PaymentType.TYPE1;
import static org.junit.jupiter.api.Assertions.*;

class Type1ValidatorTest {

    private Type1Validator type1Validator;

    @BeforeEach
    void setUp() {
        type1Validator = new Type1Validator();
    }

    @Test
    void testGetType() {
        assertEquals(TYPE1, type1Validator.getType());
    }

    @Test
    void isValid_ShouldReturnFalseForPaymentNull() {

        assertFalse(type1Validator.isValid(null));
    }

    @Test
    void isValid_ShouldReturnFalseForEmptyProperties() {
        Payment payment = Payment.builder().build();

        assertFalse(type1Validator.isValid(payment));
    }

    @Test
    void isValid_ShouldReturnFalseForCurrencyUSD() {
        Payment payment = paymentBuilder()
                .type(TYPE1)
                .currency(Currency.USD).build();

        assertFalse(type1Validator.isValid(payment));
    }

    @Test
    void isValid_ShouldReturnFalseForDetailsNull() {
        Payment payment = paymentBuilder().details(null).build();

        assertFalse(type1Validator.isValid(payment));
    }

    @Test
    void isValid_ShouldReturnFalseForDetailsEmpty() {
        Payment payment = paymentBuilder()
                .type(TYPE1)
                .details("").build();

        assertFalse(type1Validator.isValid(payment));
    }

    @Test
    void isValid_ShouldReturnTrueForEURAndDetailsNotNull() {
        Payment payment = Payment.builder()
                .type(TYPE1)
                .currency(Currency.EUR)
                .details("test details")
                .build();

        assertTrue(type1Validator.isValid(payment));
    }
}