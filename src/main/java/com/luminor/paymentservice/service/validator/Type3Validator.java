package com.luminor.paymentservice.service.validator;

import com.luminor.paymentservice.persistence.entity.Payment;
import com.luminor.paymentservice.enums.PaymentType;
import org.springframework.stereotype.Component;

import static com.luminor.paymentservice.enums.PaymentType.TYPE3;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class Type3Validator implements PaymentValidator {
    @Override
    public PaymentType getType() {
        return TYPE3;
    }

    @Override
    public boolean isValid(Payment payment) {
        if (isNull(payment)) {
            return false;
        }

        return getType().equals(payment.getType())
                && nonNull(payment.getCurrency())
                && nonNull(payment.getCreditorBic())
                && !payment.getCreditorBic().isEmpty();
    }

}
