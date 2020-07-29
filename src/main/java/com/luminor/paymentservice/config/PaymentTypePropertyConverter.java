package com.luminor.paymentservice.config;

import com.luminor.paymentservice.enums.PaymentType;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class PaymentTypePropertyConverter implements Converter<String, PaymentType> {

    @Override
    public PaymentType convert(String source) {
        return PaymentType.valueOf(source);
    }
}
