package com.luminor.paymentservice.service.validator;

import com.luminor.paymentservice.persistence.entity.Payment;
import com.luminor.paymentservice.enums.PaymentType;

public interface PaymentValidator {

    PaymentType getType();

    boolean isValid(Payment payment);

}
