package com.luminor.paymentservice.service;

import com.luminor.paymentservice.config.CancellationProperties;
import com.luminor.paymentservice.enums.PaymentType;
import com.luminor.paymentservice.exception.CancellationException;
import com.luminor.paymentservice.exception.NotFoundException;
import com.luminor.paymentservice.exception.PaymentValidationException;
import com.luminor.paymentservice.helper.DatetimeHelper;
import com.luminor.paymentservice.persistence.entity.Payment;
import com.luminor.paymentservice.persistence.repository.PaymentRepository;
import com.luminor.paymentservice.service.validator.PaymentValidatorFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentValidatorFactory paymentValidatorFactory;
    private final DatetimeHelper datetimeHelper;
    private final CancellationProperties cancellationProperties;

    public PaymentService(PaymentRepository paymentRepository, PaymentValidatorFactory paymentValidatorFactory,
                          DatetimeHelper datetimeHelper, CancellationProperties cancellationProperties) {
        this.paymentRepository = paymentRepository;
        this.paymentValidatorFactory = paymentValidatorFactory;
        this.datetimeHelper = datetimeHelper;
        this.cancellationProperties = cancellationProperties;
    }

    public Payment save(Payment payment) {
        validate(payment);
        return paymentRepository.save(payment);
    }

    private void validate(Payment payment) {
        if (!paymentValidatorFactory.getValidator(payment.getType()).isValid(payment)) {
            throw new PaymentValidationException("invalid payment of type " + payment.getType());
        }
    }

    public Payment cancel(long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("payment not found, paymentId " + paymentId));

        if (!datetimeHelper.isSameDay(payment.getCreatedAt())) {
            throw new CancellationException();
        }

        BigDecimal cancellationFee = calculateCancellationFee(payment.getType(), payment.getCreatedAt());
        payment = payment.toBuilder()
                .cancelled(true)
                .cancellationFee(cancellationFee)
                .cancelledAt(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }

    private BigDecimal calculateCancellationFee(PaymentType type, LocalDateTime dateTime) {
        return BigDecimal.valueOf(
                cancellationProperties.getValues().get(type) * datetimeHelper.completeHourDifference(dateTime)
        );
    }

    public Optional<Payment> find(long paymentId) {
        return paymentRepository.findById(paymentId);
    }
}
