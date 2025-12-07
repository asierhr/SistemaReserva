package com.asier.SistemaReservas.loyalty.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loyaltyEntity")
public class LoyaltyTierEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int minPoints;
    private int maxPoints;

    @ElementCollection
    @CollectionTable(name = "loyalty_tier_benefits", joinColumns = @JoinColumn(name = "tier_id"))
    @Column(name = "benefit")
    private List<String> benefits;
}
