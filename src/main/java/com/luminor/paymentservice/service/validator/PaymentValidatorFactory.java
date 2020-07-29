package com.luminor.paymentservice.service.validator;

import com.luminor.paymentservice.enums.PaymentType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PaymentValidatorFactory {
    private final Map<PaymentType, PaymentValidator> paymentValidatorMap;

    public PaymentValidatorFactory(Set<PaymentValidator> paymentValidators) {
        paymentValidatorMap = paymentValidators.stream()
                .collect(Collectors.toMap(PaymentValidator::getType, validator -> validator));
    }

    public PaymentValidator getValidator(PaymentType type) {
        return Optional.ofNullable(paymentValidatorMap.get(type))
                .orElseThrow(() -> new NoSuchElementException("no validator for type " + type));
    }
}
