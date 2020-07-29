package com.luminor.paymentservice.config;

import com.luminor.paymentservice.enums.PaymentType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "cancellation-coefficient")
public class CancellationProperties {

    private Map<PaymentType, Double> values = new HashMap<>();

    public Map<PaymentType, Double> getValues() {
        return values;
    }

    public void setValues(Map<PaymentType, Double> values) {
        this.values = values;
    }
}
