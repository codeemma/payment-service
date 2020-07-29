package com.luminor.paymentservice.service.validator;

import com.luminor.paymentservice.persistence.entity.Payment;
import com.luminor.paymentservice.enums.Currency;
import com.luminor.paymentservice.enums.PaymentType;
import org.springframework.stereotype.Component;

import static com.luminor.paymentservice.enums.PaymentType.TYPE2;
import static java.util.Objects.isNull;

@Component
public class Type2Validator implements PaymentValidator {
    @Override
    public PaymentType getType() {
        return TYPE2;
    }

    @Override
    public boolean isValid(Payment payment) {
        if (isNull(payment)) {
            return false;
        }

        return getType().equals(payment.getType())
                && Currency.USD.equals(payment.getCurrency());
    }

}
