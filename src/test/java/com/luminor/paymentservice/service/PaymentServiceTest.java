package com.luminor.paymentservice.service;

import com.luminor.paymentservice.exception.CancellationException;
import com.luminor.paymentservice.exception.NotFoundException;
import com.luminor.paymentservice.helper.DatetimeHelper;
import com.luminor.paymentservice.persistence.entity.Payment;
import com.luminor.paymentservice.exception.PaymentValidationException;
import com.luminor.paymentservice.persistence.repository.PaymentRepository;
import com.luminor.paymentservice.service.validator.PaymentValidatorFactory;
import com.luminor.paymentservice.service.validator.Type1Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.luminor.paymentservice.TestData.createPayment;
import static com.luminor.paymentservice.TestData.paymentBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentServiceTest {
    @MockBean
    private PaymentRepository paymentRepository;
    @MockBean
    private PaymentValidatorFactory paymentValidatorFactory;
    @MockBean
    private Type1Validator type1Validator;
    @MockBean
    private DatetimeHelper datetimeHelper;

    @Autowired
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        when(paymentValidatorFactory.getValidator(any())).thenReturn(type1Validator);
        when(type1Validator.isValid(any())).thenReturn(true);
    }

    @Test
    void testSavePaymentShouldSave() {
        Payment payment = createPayment();
        when(paymentRepository.save(payment)).thenReturn(payment);

        paymentService.save(payment);

        verify(paymentRepository).save(payment);
        verify(type1Validator).isValid(payment);

    }

    @Test
    void testSavePaymentShouldFailWhenValidationFail() {
        Payment payment = createPayment();
        when(type1Validator.isValid(payment)).thenReturn(false);

        Throwable exception = assertThrows(PaymentValidationException.class, () -> paymentService.save(payment));

        verifyNoInteractions(paymentRepository);
        verify(type1Validator).isValid(payment);
        assertEquals("invalid payment of type TYPE1", exception.getMessage());
    }

    @Test
    void testCancelShouldThrowNotFoundWhenIdNotFound() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(NotFoundException.class, () -> paymentService.cancel(1L));

        verify(paymentRepository).findById(1L);
        assertEquals("payment not found, paymentId 1", exception.getMessage());
    }

    @Test
    void testCancelShouldFailWhenNotSameDayOfCreation() {
        Payment payment = paymentBuilder()
                .createdAt(LocalDateTime.parse("2020-07-08T20:00:00.000"))
                .build();
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
        when(datetimeHelper.isSameDay(LocalDateTime.parse("2020-07-08T20:00:00.000"))).thenReturn(false);

        Throwable exception = assertThrows(CancellationException.class, () -> paymentService.cancel(1L));

        verify(paymentRepository).findById(1L);
        assertEquals("can only cancel payment on same day", exception.getMessage());
    }

    @Test
    void testCancelWhenSameDayOfCreation() {
        Payment payment = paymentBuilder()
                .createdAt(LocalDateTime.parse("2020-07-08T20:00:00.000"))
                .build();
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(any())).thenAnswer(arg -> arg.getArgument(0));
        when(datetimeHelper.isSameDay(LocalDateTime.parse("2020-07-08T20:00:00.000"))).thenReturn(true);
        when(datetimeHelper.completeHourDifference(LocalDateTime.parse("2020-07-08T20:00:00.000"))).thenReturn(2L);

        Payment result = paymentService.cancel(1L);

        verify(paymentRepository).findById(1L);
        assertTrue(result.isCancelled());
        assertEquals(BigDecimal.valueOf(0.10), result.getCancellationFee());
        assertNotNull(result.getCancelledAt());
    }

}