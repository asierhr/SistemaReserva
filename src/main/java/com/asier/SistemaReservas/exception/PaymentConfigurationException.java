package com.asier.SistemaReservas.exception;

public class PaymentConfigurationException extends PaymentException {
    public PaymentConfigurationException(String message) {
        super(message);
    }

    public PaymentConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
