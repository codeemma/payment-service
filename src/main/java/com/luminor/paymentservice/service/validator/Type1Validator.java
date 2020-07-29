package com.luminor.paymentservice.service.validator;

import com.luminor.paymentservice.persistence.entity.Payment;
import com.luminor.paymentservice.enums.Currency;
import com.luminor.paymentservice.enums.PaymentType;
import org.springframework.stereotype.Component;

import static com.luminor.paymentservice.enums.PaymentType.TYPE1;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class Type1Validator implements PaymentValidator {
    @Override
    public PaymentType getType() {
        return TYPE1;
    }

    @Override
    public boolean isValid(Payment payment) {
        if (isNull(payment)) {
            return false;
        }

        return getType().equals(payment.getType())
                && Currency.EUR.equals(payment.getCurrency())
                && nonNull(payment.getDetails())
                && !payment.getDetails().trim().isEmpty();
    }

}
