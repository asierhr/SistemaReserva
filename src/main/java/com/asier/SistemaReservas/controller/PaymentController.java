package com.asier.SistemaReservas.controller;

import com.asier.SistemaReservas.servicies.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
}
