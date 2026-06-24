package com.asier.SistemaReservas.payment.exception;

public class PaymentProcessingException extends PaymentException {
    public PaymentProcessingException(String message) {
        super(message);
    }

    public PaymentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
