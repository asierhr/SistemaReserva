package com.asier.SistemaReservas.loyalty.controller;

import com.asier.SistemaReservas.loyalty.service.LoyaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoyaltyController {
    private final LoyaltyService loyaltyService;
}
