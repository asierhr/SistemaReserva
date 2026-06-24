package com.asier.SistemaReservas.payment.exception;

public class PaymentNotFoundException extends PaymentException {
    public PaymentNotFoundException(Long paymentId) {
        super("Payment not found with ID: " + paymentId);
    }

    public PaymentNotFoundException(String paymentIntentId) {
        super("Payment not found with PaymentIntent ID: " + paymentIntentId);
    }
}
