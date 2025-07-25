package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Payment;

import java.time.Instant;

public record PaymentDTO(Long id, Instant moment) {
    public static PaymentDTO fromPayment(Payment payment){
        return new PaymentDTO(payment.getId(),payment.getMoment());    }
}
