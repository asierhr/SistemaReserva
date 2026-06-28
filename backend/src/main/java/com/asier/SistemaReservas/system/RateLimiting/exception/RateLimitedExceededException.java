package com.asier.SistemaReservas.system.RateLimiting.exception;

public class RateLimitedExceededException extends RuntimeException{
    public RateLimitedExceededException(String message){
        super(message);
    }
}
