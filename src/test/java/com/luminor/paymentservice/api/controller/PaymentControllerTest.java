package com.luminor.paymentservice.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luminor.paymentservice.api.model.ExternalPaymentCreate;
import com.luminor.paymentservice.helper.DatetimeHelper;
import com.luminor.paymentservice.persistence.entity.Payment;
import com.luminor.paymentservice.persistence.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static com.luminor.paymentservice.TestData.*;
import static com.luminor.paymentservice.enums.Currency.EUR;
import static com.luminor.paymentservice.enums.PaymentType.TYPE1;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentRepository paymentRepository;

    @SpyBean
    private DatetimeHelper datetimeHelper;

    @Test
    public void createShouldReturn422ForEmptyProperties() throws Exception {
        mockMvc.perform(post("/v1/payment")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(ExternalPaymentCreate.builder().build())))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createShouldReturnBadRequestForInvalidType1() throws Exception {
        ExternalPaymentCreate paymentCreate = externalPaymentCreateBuilder()
                .type(TYPE1)
                .details(null).build();

        mockMvc.perform(post("/v1/payment")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(paymentCreate)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createShouldReturnSuccess() throws Exception {
        mockMvc.perform(post("/v1/payment")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(externalPaymentCreate())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.creditor_iban", is("0000000000")))
                .andExpect(jsonPath("$.debtor_iban", is("1111111111")))
                .andExpect(jsonPath("$.amount", is(1500)))
                .andExpect(jsonPath("$.currency", is("EUR")))
                .andExpect(jsonPath("$.type", is("TYPE1")))
                .andExpect(jsonPath("$.details", is("test")))
                .andExpect(jsonPath("$.cancelled", is(false)))
                .andExpect(jsonPath("$.created_at", notNullValue()))
                .andExpect(jsonPath("$.updated_at", notNullValue()));
    }

    @Test
    public void cancelShouldFailWhenNotOnCreationDay() throws Exception {
        Payment payment = createPayment();
        paymentRepository.save(payment);
        when(datetimeHelper.isSameDay(payment.getCreatedAt())).thenReturn(false);

        mockMvc.perform(put("/v1/payment/{0}/cancel", payment.getId()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getShouldFailForPaymentIdNotFound() throws Exception {

        mockMvc.perform(get("/v1/payment/{0}", Integer.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getShouldReturnSuccessForValidPaymentId() throws Exception {
        Payment payment = createPayment();
        paymentRepository.save(payment);

        mockMvc.perform(get("/v1/payment/{0}", payment.getId()))
                .andExpect(status().isOk());
    }

}