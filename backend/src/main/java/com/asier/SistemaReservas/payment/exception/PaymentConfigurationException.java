package com.asier.SistemaReservas.payment.exception;

public class PaymentConfigurationException extends PaymentException {
    public PaymentConfigurationException(String message) {
        super(message);
    }

    public PaymentConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
