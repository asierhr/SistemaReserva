package com.asier.SistemaReservas.loyalty.components;

import com.asier.SistemaReservas.loyalty.domain.entity.LoyaltyTierEntity;
import com.asier.SistemaReservas.loyalty.repository.LoyaltyTierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final LoyaltyTierRepository loyaltyTierRepository;

    @Override
    public void run(String... args){
        if(loyaltyTierRepository.count() == 0){
            LoyaltyTierEntity bronze = LoyaltyTierEntity.builder()
                    .name("Bronze")
                    .minPoints(0)
                    .maxPoints(999)
                    .benefits(List.of())
                    .build();

            LoyaltyTierEntity silver = LoyaltyTierEntity.builder()
                    .name("Silver")
                    .minPoints(1000)
                    .maxPoints(4999)
                    .benefits(List.of("10% discount", "Flexible cancellation policy", "Booking priority"))
                    .build();

            LoyaltyTierEntity gold = LoyaltyTierEntity.builder()
                    .name("Gold")
                    .minPoints(5000)
                    .maxPoints(9999)
                    .benefits(List.of("15% discount", "Access to exclusive offers","Priority in Check-in/Check-out"))
                    .build();

            LoyaltyTierEntity platinum = LoyaltyTierEntity.builder()
                    .name("Platinum")
                    .minPoints(10000)
                    .maxPoints(20000)
                    .benefits(List.of("20% discount", "Free cancellation", "Exclusive experiences"))
                    .build();

            loyaltyTierRepository.saveAll(List.of(bronze,silver,gold,platinum));
        }
    }
}
