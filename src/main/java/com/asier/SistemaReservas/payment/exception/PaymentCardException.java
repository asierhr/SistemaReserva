package com.asier.SistemaReservas.payment.exception;


public class PaymentCardException extends PaymentException {
    public PaymentCardException(String message) {
        super(message);
    }

    public PaymentCardException(String message, Throwable cause) {
        super(message, cause);
    }
}
