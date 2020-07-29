package com.luminor.paymentservice.api.controller;

import com.luminor.paymentservice.api.model.ExternalPayment;
import com.luminor.paymentservice.api.model.ExternalPaymentCreate;
import com.luminor.paymentservice.exception.NotFoundException;
import com.luminor.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@Slf4j
@RestController
@RequestMapping(path = "v1/payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ExternalPayment create(@RequestBody @Valid ExternalPaymentCreate paymentCreate) {
        log.debug("create() received, paymentCreate = {}", paymentCreate);

        return ExternalPayment.valueOf(
                paymentService.save(paymentCreate.toPayment())
        );
    }

    @GetMapping("/{paymentId}")
    public ExternalPayment get(@PathVariable @Min(1) long paymentId) {
        log.debug("get() received, paymentId = {}", paymentId);

        return paymentService.find(paymentId).map(ExternalPayment::valueOf)
                .orElseThrow(() -> new NotFoundException("payment not found, paymentId "+ paymentId));
    }

    @PutMapping("/{paymentId}/cancel")
    public ExternalPayment cancel(@PathVariable @Min(1) long paymentId) {
        log.debug("cancel() received, paymentId = {}", paymentId);

        return ExternalPayment.valueOf(
                paymentService.cancel(paymentId)
        );
    }
}
